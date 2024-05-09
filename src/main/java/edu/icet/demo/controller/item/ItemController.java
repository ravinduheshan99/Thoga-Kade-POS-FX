package edu.icet.demo.controller.item;

import edu.icet.demo.crudUtil.CrudUtil;
import edu.icet.demo.model.Item;
import edu.icet.demo.model.OrderDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemController {
    //Singleton
    private static ItemController instance;

    private ItemController() {
    }

    public static ItemController getInstance() {
        if (instance == null) {
            return instance = new ItemController();
        }
        return instance;
    }


    public ObservableList<Item> loadItems() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM item");
            ObservableList<Item> itemList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                itemList.add(
                        new Item(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDouble(4),
                                resultSet.getInt(5)
                        )
                );
            }
            return itemList;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Item searchItem(String ItmCode) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM item WHERE ItemCode ='" + ItmCode + "'");
            while (resultSet.next()) {
                return new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5));
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean updateStock(List<OrderDetail> orderDetailList) {
        boolean isUpdate = false;
        for (OrderDetail orderDetail : orderDetailList) {
            isUpdate = updateStock(orderDetail);
        }
        if(isUpdate){
            return true;
        }
        return false;
    }

    public boolean updateStock(OrderDetail orderDetail) {
        try {
            Object isUpdate = CrudUtil.execute("UPDATE item SET QtyOnHand=QtyOnHand-? WHERE ItemCode=?", orderDetail.getQty(), orderDetail.getItemCode());
            return (Boolean) isUpdate;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
