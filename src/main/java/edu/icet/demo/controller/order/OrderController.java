package edu.icet.demo.controller.order;

import edu.icet.demo.controller.item.ItemController;
import edu.icet.demo.db.DBConnection;
import edu.icet.demo.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    //Singleton
    private OrderController() {
    }

    private static OrderController instance;

    public static OrderController getInstance() {
        if (instance == null) {
            return instance = new OrderController();
        }
        return instance;
    }


    public Boolean placeOrder(Order order) throws SQLException, ClassNotFoundException {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO orders VALUES(?,?,?)");
            pstm.setString(1, order.getOrderId());
            pstm.setObject(2, order.getOrderDate());
            pstm.setString(3, order.getCusId());
            boolean isOrderExecute = pstm.execute();

            //we use ! symbol below, beause this pstm.execute() returns false even if it execute correctly
            if (!isOrderExecute) {
                boolean isOrderDetailAdd = OrderDetailController.getInstance().addOrderDetail(order.getOrderDetailList());
                if (isOrderDetailAdd) {
                    boolean isStockUpdate = ItemController.getInstance().updateStock(order.getOrderDetailList());
                    if (isStockUpdate) {
                        connection.setAutoCommit(true);
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }finally {
            //because we disable auto commit for the whole database so we need to active it again
            Connection con =  DBConnection.getInstance().getConnection();
            con.setAutoCommit(true);
        }
    }
}
