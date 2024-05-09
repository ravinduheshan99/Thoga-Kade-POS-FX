package edu.icet.demo.controller.order;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.demo.controller.customer.CustomerController;
import edu.icet.demo.controller.item.ItemController;
import edu.icet.demo.db.DBConnection;
import edu.icet.demo.model.*;
import edu.icet.demo.model.tm.TableModelCart;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceOrderFormController implements Initializable {

    public Label lblDate;
    public Label lblTime;
    public ComboBox cmbCid;
    public JFXTextField txtCname;
    public JFXTextField txtAdrs;
    public JFXTextField txtSal;
    public JFXTextField txtCity;
    public JFXTextField txtItmDesc;
    public ComboBox cmbItmId;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public Label lblOid;
    public TableView tblCart;
    public TableColumn colItmCode;
    public TableColumn colItmDesc;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTot;
    public JFXTextField txtQtyNeed;
    public Label lblNetTot;
    public JFXButton btnBackNav;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItmCode.setCellValueFactory(new PropertyValueFactory<>("itmCode"));
        colItmDesc.setCellValueFactory(new PropertyValueFactory<>("itmDesc"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTot.setCellValueFactory(new PropertyValueFactory<>("tot"));
        loadDateAndTime();
        loadCustomerIDs();
        loadItemIDs();
        generateOrderId();

        cmbCid.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCustomerData((String) newValue);
        });

        cmbItmId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setItemData((String) newValue);
        });
    }

    private void setItemData(String ItmCode) {
        Item item = ItemController.getInstance().searchItem(ItmCode);
        txtItmDesc.setText(item.getItmDesc());
        txtPackSize.setText(item.getPackSize());
        txtUnitPrice.setText(item.getUnitPrice() + "0");
        txtQty.setText(item.getQty() + "");
    }

    private void setCustomerData(String Cid) {
        Customer customer = CustomerController.getInstance().searchCustomer(Cid);
        txtCname.setText(customer.getCname());
        txtAdrs.setText(customer.getAdrs());
        txtSal.setText(customer.getSal() + "0");
        txtCity.setText(customer.getCity());
    }

    private void loadItemIDs() {
        ObservableList<Item> allItems = ItemController.getInstance().loadItems();
        ObservableList<String> itmIds = FXCollections.observableArrayList();
        allItems.forEach(item -> {
            itmIds.add(item.getItmCode());
        });
        cmbItmId.setItems(itmIds);
    }

    private void loadCustomerIDs() {
        ObservableList<Customer> allCustomers = CustomerController.getInstance().loadCustomers();
        ObservableList<String> cusIDs = FXCollections.observableArrayList();
        allCustomers.forEach(customer -> {
            cusIDs.add(customer.getCid());
        });
        cmbCid.setItems(cusIDs);
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(fmt.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime time = LocalTime.now();
            lblTime.setText(time.getHour() + " : " + time.getMinute() + " : " + time.getSecond());
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void generateOrderId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM orders");
            Integer count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
                System.out.println(count + "  count ");
            }
            if (count == 0) {
                lblOid.setText("D001");
            }
            String lastOrderId = "";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT OrderID\n" +
                    "FROM orders\n" +
                    "ORDER BY OrderID DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()) {
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    lblOid.setText(String.format("D%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING, "hello").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    ObservableList<TableModelCart> cartList = FXCollections.observableArrayList();

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String itmCode = cmbItmId.getValue().toString();
        String itmDesc = txtItmDesc.getText();
        Integer qty = Integer.parseInt(txtQtyNeed.getText());
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Double tot = qty * unitPrice;

        if (qty > Integer.parseInt(txtQty.getText())) {
            new Alert(Alert.AlertType.WARNING, "Quantity Exceeded!").show();
            txtQtyNeed.setText(null);
        } else {
            TableModelCart cart = new TableModelCart(itmCode, itmDesc, qty, unitPrice, tot, 0.0);
            cartList.add(cart);
            tblCart.setItems(cartList);
            calcNetToatal();
            clearItemDetails();
        }

    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        try {
            String oid = lblOid.getText();

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date orderDate = format.parse(lblDate.getText());

            String cid = cmbCid.getValue().toString();

            List<OrderDetail> orderDetailList = new ArrayList<>();

            for (TableModelCart itm : cartList) {
                orderDetailList.add(new OrderDetail(oid, itm.getItmCode(), itm.getQty(), itm.getDiscount()));
            }
            Order order = new Order(oid, orderDate, cid, orderDetailList);
            Boolean isOrderPlaced = OrderController.getInstance().placeOrder(order);
            if(isOrderPlaced){
                generateOrderId();
                new Alert(Alert.AlertType.INFORMATION,"Order Placed Successfully").show();
                clearForm();
            }

        } catch (ParseException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnBackNavOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) btnBackNav.getScene().getWindow();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Add Customer");
            primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/addCustomerForm.fxml"))));
            primaryStage.show();
            stage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearForm();
    }

    private void calcNetToatal() {
        Double netTot = 0.0;
        for (TableModelCart cartObj : cartList) {
            netTot += cartObj.getTot();
        }
        lblNetTot.setText(netTot + "0 LKR");
    }

    private void clearForm() {
        cmbCid.setValue(null);
        txtCname.setText(null);
        txtAdrs.setText(null);
        txtSal.setText(null);
        txtCity.setText(null);
        cmbItmId.setValue(null);
        txtItmDesc.setText(null);
        txtPackSize.setText(null);
        txtUnitPrice.setText(null);
        txtQty.setText(null);
        txtQtyNeed.setText(null);
        lblNetTot.setText(null);
        tblCart.setItems(null);
    }

    private void clearItemDetails(){
        cmbItmId.setValue(null);
        txtItmDesc.setText(null);
        txtPackSize.setText(null);
        txtUnitPrice.setText(null);
        txtQty.setText(null);
        txtQtyNeed.setText(null);
    }

    public void btnAPICallOnAction(ActionEvent actionEvent) {
        try {
            //create url
            URL url = new URL("https://jsonplaceholder.typicode.com/todos/1");

            //create connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //set request method
            connection.setRequestMethod("GET");

            //get response code
            int reponseCode = connection.getResponseCode();

            //read response body
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            while((inputLine=bufferedReader.readLine()) !=null){
                response.append(inputLine);
            }
            bufferedReader.close();

            Gson gson = new Gson();
            Sample sample = gson.fromJson(response.toString(),Sample.class);
            System.out.println(sample);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
