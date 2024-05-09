package edu.icet.demo.controller.customer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.demo.model.Customer;
import edu.icet.demo.model.tm.TableModel01;
import edu.icet.demo.model.tm.TableModel02;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {
    public JFXTextField txtCid;
    public ChoiceBox cmbTitle;
    public JFXTextField txtCname;
    public JFXTextField txtSal;
    public DatePicker dateDob;
    public JFXTextField txtAdrs;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;
    public TableView tblCus01;
    public TableColumn colCid01;
    public TableColumn colTitle;
    public TableColumn colCname;
    public TableColumn colDob;
    public TableColumn colSal;
    public TableView tblCus02;
    public TableColumn colCid02;
    public TableColumn colAdrs;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalcode;
    public JFXButton btnPlaceOrderNav;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCid01.setCellValueFactory(new PropertyValueFactory<>("Cid"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colCname.setCellValueFactory(new PropertyValueFactory<>("Cname"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("Dob"));
        colSal.setCellValueFactory(new PropertyValueFactory<>("Sal"));
        colCid02.setCellValueFactory(new PropertyValueFactory<>("Cid"));
        colAdrs.setCellValueFactory(new PropertyValueFactory<>("Adrs"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("City"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("Province"));
        colPostalcode.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
        loadTitleMenu();
        loadTable01();
        loadTable02();
    }

    private void loadTitleMenu() {
        ObservableList<Object> titles = FXCollections.observableArrayList();
        titles.add("Ms");
        titles.add("Miss");
        titles.add("Mr");
        titles.add("Mrs");
        cmbTitle.setItems(titles);
    }

    private void loadTable01() {
        ObservableList<TableModel01> tbl01Data = FXCollections.observableArrayList();
        ObservableList<Customer> allCustomers = CustomerController.getInstance().loadCustomers();
        allCustomers.forEach(customer -> {
            TableModel01 table01Record = new TableModel01(
                    customer.getCid(),
                    customer.getTitle(),
                    customer.getCname(),
                    customer.getDob(),
                    customer.getSal());
            tbl01Data.add(table01Record);
        });

        tblCus01.setItems(tbl01Data);
    }

    private void loadTable02() {
        ObservableList<TableModel02> tbl02Data = FXCollections.observableArrayList();
        ObservableList<Customer> allCustomers = CustomerController.getInstance().loadCustomers();
        allCustomers.forEach(customer -> {
            TableModel02 table02Record = new TableModel02(
                    customer.getCid(),
                    customer.getAdrs(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode());
            tbl02Data.add(table02Record);
        });

        tblCus02.setItems(tbl02Data);
    }

    private void clearText() {
        txtCid.setText(null);
        cmbTitle.setValue(null);
        txtCname.setText(null);
        dateDob.setValue(null);
        txtSal.setText(null);
        txtAdrs.setText(null);
        txtCity.setText(null);
        txtProvince.setText(null);
        txtPostalCode.setText(null);
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws ParseException {
        Customer customer = new Customer(txtCid.getText(), cmbTitle.getValue().toString(), txtCname.getText(), dateDob.getValue(), Double.parseDouble(txtSal.getText()), txtAdrs.getText(), txtCity.getText(), txtProvince.getText(), txtPostalCode.getText());
        boolean b = CustomerController.getInstance().addCustomer(customer);
        if (b) {
            loadTable01();
            loadTable02();
            clearText();
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Added Successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Added!").show();
            loadTable01();
            loadTable02();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Customer customer = new Customer(txtCid.getText(), cmbTitle.getValue().toString(), txtCname.getText(), dateDob.getValue(), Double.parseDouble(txtSal.getText()), txtAdrs.getText(), txtCity.getText(), txtProvince.getText(), txtPostalCode.getText());
        boolean b = CustomerController.getInstance().updateCustomer(customer);
        if (b) {
            loadTable01();
            loadTable02();
            clearText();
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated Successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Updated!").show();
            loadTable01();
            loadTable02();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        Customer customer = CustomerController.getInstance().searchCustomer(txtCid.getText());
        if(customer==null){
            new Alert(Alert.AlertType.WARNING,"Customer Not Found!").show();
            txtCid.setText(null);
        }else {
            cmbTitle.setValue(customer.getTitle());
            txtCname.setText(customer.getCname());
            dateDob.setValue(customer.getDob());
            txtSal.setText(customer.getSal() + "0");
            txtAdrs.setText(customer.getAdrs());
            txtCity.setText(customer.getCity());
            txtProvince.setText(customer.getProvince());
            txtPostalCode.setText(customer.getPostalCode());
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
            boolean b = CustomerController.getInstance().deleteCustomer(txtCid.getText());
            if (b) {
                loadTable01();
                loadTable02();
                clearText();
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Deleted!").show();
                loadTable01();
                loadTable02();
            }
    }

    public void btnClearAllOnAction(ActionEvent actionEvent) {
        clearText();
    }

    public void btnPlaceOrderNavOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) btnPlaceOrderNav.getScene().getWindow();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Place Order");
            primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/placeOrderForm.fxml"))));
            primaryStage.show();
            stage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnExitOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

}
