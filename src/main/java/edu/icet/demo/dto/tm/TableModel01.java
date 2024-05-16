package edu.icet.demo.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

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
