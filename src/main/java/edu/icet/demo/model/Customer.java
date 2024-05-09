package edu.icet.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String Cid;
    private String Title;
    private String Cname;
    private LocalDate Dob;
    private Double Sal;
    private String Adrs;
    private String City;
    private String Province;
    private String PostalCode;

}
