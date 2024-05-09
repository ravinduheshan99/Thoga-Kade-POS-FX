package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private String itmCode;
    private String itmDesc;
    private String packSize;
    private Double unitPrice;
    private Integer qty;
}
