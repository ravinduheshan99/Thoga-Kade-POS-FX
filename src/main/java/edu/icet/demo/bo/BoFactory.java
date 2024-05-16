package edu.icet.demo.bo;

import edu.icet.demo.bo.custom.impl.CustomerBoImpl;
import edu.icet.demo.util.BoType;

//Factory Design Pattern
public class BoFactory {
    //Singleton Design Pattern
    private BoFactory(){}

    private static BoFactory instance;

    public static BoFactory getInstance(){
        return instance!=null? instance:(instance = new BoFactory());
    }

    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case CUSTOMER:
                return  (T) new CustomerBoImpl();
        }
        return null;
    }

}
