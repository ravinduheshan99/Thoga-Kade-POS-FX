package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.dao.custom.CustomerDao;
import edu.icet.demo.dto.Customer;
import edu.icet.demo.entity.CustomerEntity;
import edu.icet.demo.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImpl implements CustomerDao {

    @Override
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

    @Override
    public boolean addCustomer(CustomerEntity entity) {
        try {
            String sql = "INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?)";
            return CrudUtil.execute(sql,entity.getCid(),entity.getTitle(),entity.getCname(),entity.getDob(),entity.getSal(),entity.getAdrs(),entity.getCity(),entity.getProvince(),entity.getPostalCode());
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCustomer(CustomerEntity entity) {
        try {
            return CrudUtil.execute("UPDATE customer SET CustTitle = '" + entity.getTitle() + "',CustName='" + entity.getCname() + "',DOB='" + entity.getDob() + "',salary='" + entity.getSal() + "',CustAddress='" + entity.getAdrs() + "',City='" + entity.getCity() + "',Province='" + entity.getProvince() + "',PostalCode='" + entity.getPostalCode() + "' WHERE CustID='" + entity.getCid() + "'");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer searchCustomer(String cid) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer WHERE CustID ='"+cid+"'" );
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

    @Override
    public boolean deleteCustomer(String cid) {
        try {
            return CrudUtil.execute("DELETE FROM customer WHERE CusTID = '" + cid + "'");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
