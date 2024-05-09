package edu.icet.demo.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TableModel02 {

    private String Cid;
    private String Adrs;
    private String City;
    private String Province;
    private String PostalCode;
}
