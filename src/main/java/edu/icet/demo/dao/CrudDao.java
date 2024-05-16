package edu.icet.demo.dao;

import edu.icet.demo.dto.Customer;
import javafx.collections.ObservableList;

public interface CrudDao <T> extends SuperDao{

    public ObservableList<Customer> loadCustomers();
    public boolean addCustomer(T entity);
    public boolean updateCustomer(T entity);
    public Customer searchCustomer(String cid);
    public boolean deleteCustomer(String cid);

}
