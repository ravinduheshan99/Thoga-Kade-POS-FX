package edu.icet.demo.controller.customer;

import edu.icet.demo.crudUtil.CrudUtil;
import edu.icet.demo.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerController implements CustomerService {
    //Singleton
    private CustomerController(){}

    private static CustomerController instance;

    public static CustomerController getInstance(){
        if (instance==null){
            return instance=new CustomerController();
        }
        return instance;
    }


    public ObservableList<Customer> loadCustomers() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer");
            ObservableList<Customer> customerList = FXCollections.observableArrayList();
            while (resultSet.next()){
                customerList.add( new Customer(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                ));
            }
            return customerList;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addCustomer(Customer customer){
        try {
            String sql = "INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?)";
            return CrudUtil.execute(sql,customer.getCid(),customer.getTitle(),customer.getCname(),customer.getDob(),customer.getSal(),customer.getAdrs(),customer.getCity(),customer.getProvince(),customer.getPostalCode());
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateCustomer(Customer customer){
        try {
            return CrudUtil.execute("UPDATE customer SET CustTitle = '" + customer.getTitle() + "',CustName='" + customer.getCname() + "',DOB='" + customer.getDob() + "',salary='" + customer.getSal() + "',CustAddress='" + customer.getAdrs() + "',City='" + customer.getCity() + "',Province='" + customer.getProvince() + "',PostalCode='" + customer.getPostalCode() + "' WHERE CustID='" + customer.getCid() + "'");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer searchCustomer(String Cid){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer WHERE CustID ='"+Cid+"'" );
            while (resultSet.next()){
                return new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9));
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean deleteCustomer(String Cid){
        try {
             return CrudUtil.execute("DELETE FROM customer WHERE CusTID = '" + Cid + "'");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
