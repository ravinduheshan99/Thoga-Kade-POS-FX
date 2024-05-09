package edu.icet.demo.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TableModelCart {
    private String itmCode;
    private String itmDesc;
    private Integer qty;
    private Double unitPrice;
    private Double tot;
    private Double discount;
}
