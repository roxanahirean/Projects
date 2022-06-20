package com.utcn.roxana.businesslogic;

import com.utcn.roxana.dataAccess.ProductDA;
import com.utcn.roxana.model.Order;
import com.utcn.roxana.model.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductBL {
    private ProductDA product;

    public ProductBL() {
        product = new ProductDA();
    }

    public List<Product> findAll() {
        List<Product> p = product.findAll();
        if(p == null) {
            throw new NoSuchElementException("there are no clients in the database\n");
        }
        return p;
    }

    public void setAmount1(int id, int amount) {
        Product p = product.findById(id);
        if(p == null || p.getAmount() < amount) {
            throw new NoSuchElementException("error\n");
        } else {
            product.setAmount(id, p.getAmount() - amount);
        }
    }

    public void insert(Product pr) {
        Product p = product.insert(pr);
        if(p == null) {
            throw new NoSuchElementException("insertion cannot be done\n");
        }
    }
    public void update(Product pr) {
        Product p = product.update(pr);
        if(p == null) {
            throw new NoSuchElementException("update cannot be done\n");
        }
    }
    public void delete(int id) {
        int i = product.delete(id);
        if(i == 0) {
            throw new NoSuchElementException("delete cannot be done\n");
        }
    }
}
