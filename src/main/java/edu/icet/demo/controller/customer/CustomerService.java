package edu.icet.demo.controller.customer;

import edu.icet.demo.model.Customer;
import javafx.collections.ObservableList;

public interface CustomerService {
    //facade design pattern

    ObservableList<Customer> loadCustomers();

    Customer searchCustomer(String Cid);

    boolean addCustomer(Customer customer);
}
