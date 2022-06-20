package com.utcn.roxana.businesslogic;

import com.utcn.roxana.dataAccess.OrderDA;
import com.utcn.roxana.model.Order;

import java.util.List;
import java.util.NoSuchElementException;

public class OrderBL {
    private OrderDA order = new OrderDA();

    public List<Order> findAll() {
        List<Order> or = order.findAll();
        return or;
    }

    public void insert(Order or) {
        Order o = order.insert(or);
        if(o == null) {
            throw new NoSuchElementException("insertion cannot be done\n");
        }
    }

    public void placeOrder(Order or) {
        ProductBL product = new ProductBL();
        product.setAmount1(or.getIDProduct(), or.getAmount());
        Order o = order.insert(or);
        if(o == null) {
            throw new NoSuchElementException("order cannot be placed\n");
        }
    }
}
