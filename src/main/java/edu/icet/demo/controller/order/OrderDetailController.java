package edu.icet.demo.controller.order;

import edu.icet.demo.util.CrudUtil;
import edu.icet.demo.dto.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {
    //Singleton
    private OrderDetailController(){}

    private static OrderDetailController instance;

    public static OrderDetailController getInstance(){
        if (instance==null){
            return instance=new OrderDetailController();
        }
        return instance;
    }

    public Boolean addOrderDetail(List<OrderDetail> orderDetailList){
        boolean isAdd=false;
        for(OrderDetail orderDetail:orderDetailList){
             isAdd = addOrderDetail(orderDetail);
        }
        if (isAdd){
            return true;
        }
        return false;
    }

    public Boolean addOrderDetail(OrderDetail orderDetail){
        try {
            Object isAdd = CrudUtil.execute("INSERT INTO orderdetail VALUES(?,?,?,?)", orderDetail.getOrderId(), orderDetail.getItemCode(), orderDetail.getQty(), orderDetail.getDiscount());
            return (Boolean) isAdd;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
