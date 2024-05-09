package edu.icet.demo.model.tm;

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
public class TableModel01 {

    private String Cid;
    private String Title;
    private String Cname;
    private LocalDate Dob;
    private Double Sal;
}
