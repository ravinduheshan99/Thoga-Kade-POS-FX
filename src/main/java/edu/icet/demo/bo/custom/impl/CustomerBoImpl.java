package edu.icet.demo.bo.custom.impl;

import edu.icet.demo.bo.custom.CustomerBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.CustomerDao;
import edu.icet.demo.dao.custom.impl.CustomerDaoImpl;
import edu.icet.demo.dto.Customer;
import edu.icet.demo.entity.CustomerEntity;
import edu.icet.demo.util.DaoType;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

public class CustomerBoImpl implements CustomerBo {

    private CustomerDao customerDaoImpl = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);

    @Override
    public ObservableList<Customer> loadCustomers() {
        return customerDaoImpl.loadCustomers();
    }

    @Override
    public boolean addCustomer(Customer dto) {
        return customerDaoImpl.addCustomer(new ModelMapper().map(dto, CustomerEntity.class));
    }

    @Override
    public boolean updateCustomer(Customer dto) {
        return customerDaoImpl.updateCustomer(new ModelMapper().map(dto, CustomerEntity.class));
    }

    @Override
    public Customer searchCustomer(String cid) {
        return customerDaoImpl.searchCustomer(cid);
    }

    @Override
    public boolean deleteCustomer(String cid) {
        return customerDaoImpl.deleteCustomer(cid);
    }
}
