package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.dao.custom.CustomerDao;
import edu.icet.demo.dto.Customer;
import edu.icet.demo.entity.CustomerEntity;
import edu.icet.demo.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public ObservableList<Customer> loadCustomers() {
        /*
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
        */

        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        Session session = null;
        Transaction transaction = null;

        try {

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            List<CustomerEntity> customerEntities = session.createQuery("FROM CustomerEntity", CustomerEntity.class).list();
            for (CustomerEntity entity : customerEntities) {
                Customer customer = new ModelMapper().map(entity, Customer.class);
                customerList.add(customer);
            }

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // or use a logger to log the exception
            throw new RuntimeException("Failed to load customers", e);

        } finally {

            if (session != null) {
                session.close();
            }

        }

        return customerList;
    }

    @Override
    public boolean addCustomer(CustomerEntity entity) {
        /*
        try {
            String sql = "INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?)";
            return CrudUtil.execute(sql,entity.getId(),entity.getTitle(),entity.getCname(),entity.getDob(),entity.getSal(),entity.getAdrs(),entity.getCity(),entity.getProvince(),entity.getPostalCode());
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        */

        Session session = null;
        Transaction transaction = null;

        try {

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();  // or use a logger to log the exception
            return false;

        } finally {

            if (session != null) {
                session.close();
            }

        }

    }

    @Override
    public boolean updateCustomer(CustomerEntity entity) {
        /*
        try {
            return CrudUtil.execute("UPDATE customer SET CustTitle = '" + entity.getTitle() + "',CustName='" + entity.getCname() + "',DOB='" + entity.getDob() + "',salary='" + entity.getSal() + "',CustAddress='" + entity.getAdrs() + "',City='" + entity.getCity() + "',Province='" + entity.getProvince() + "',PostalCode='" + entity.getPostalCode() + "' WHERE CustID='" + entity.getId() + "'");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

         */

        Session session = null;
        Transaction transaction = null;

        try {

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            session.merge(entity); // merge does not require entity.getId(), just the entity
            transaction.commit();
            return true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // or use a logger to log the exception
            return false;

        } finally {

            if (session != null) {
                session.close();
            }

        }

    }

    @Override
    public Customer searchCustomer(String cid) {
        /*
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

         */

        Session session = null;
        Transaction transaction = null;

        try {

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            CustomerEntity customerEntity = session.get(CustomerEntity.class, cid);
            if (customerEntity == null) {
                return null;  // No customer found with the given ID
            }

            Customer customer = new ModelMapper().map(customerEntity, Customer.class);
            transaction.commit();
            return customer;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();  // or use a logger to log the exception
            return null;

        } finally {

            if (session != null) {
                session.close();
            }

        }

    }

    @Override
    public boolean deleteCustomer(String cid) {
        /*
        try {
            return CrudUtil.execute("DELETE FROM customer WHERE CusTID = '" + cid + "'");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

         */

        Session session = null;
        Transaction transaction = null;

        try {

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();

            CustomerEntity customerEntity = session.get(CustomerEntity.class, cid);
            if (customerEntity == null) {
                return false;  // No customer found with the given ID
            }

            session.delete(customerEntity);

            transaction.commit();
            return true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // or use a logger to log the exception
            return false;

        } finally {

            if (session != null) {
                session.close();
            }

        }

    }

}
