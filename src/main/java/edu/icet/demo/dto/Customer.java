package edu.icet.demo.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private String id;

    private String Title;
    private String Cname;
    private LocalDate Dob;
    private Double Sal;
    private String Adrs;
    private String City;
    private String Province;
    private String PostalCode;

}
