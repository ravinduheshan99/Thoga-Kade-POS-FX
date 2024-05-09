package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String orderId;
    private Date orderDate;
    private String cusId;
    private List<OrderDetail> orderDetailList;
}
