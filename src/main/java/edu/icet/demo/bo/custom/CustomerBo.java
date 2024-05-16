package edu.icet.demo.bo.custom;

import edu.icet.demo.bo.SuperBo;
import edu.icet.demo.dto.Customer;
import javafx.collections.ObservableList;

//Strategy Design Pattern
public interface CustomerBo extends SuperBo {

    public ObservableList<Customer> loadCustomers();

    public boolean addCustomer(Customer dto);
    public boolean updateCustomer(Customer dto);
    public Customer searchCustomer(String cid);
    public boolean deleteCustomer(String cid);

}
