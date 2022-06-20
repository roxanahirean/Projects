package com.utcn.roxana.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

public class Order {
    @Getter
    @Setter
    private int ID;
    @Getter
    @Setter
    private int IDProduct;
    @Getter
    @Setter
    private int IDClient;
    @Getter
    @Setter
    private int price;
    @Getter
    @Setter
    private int amount;

    public Order(int ID, int IDProduct, int IDClient, int price, int amount) {
        this.ID = ID;
        this.IDProduct = IDProduct;
        this.IDClient = IDClient;
        this.price = price;
        this.amount = amount;
    }

    public Order() {
    }


    @Override
    public String toString() {

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        return "Order" +
                "ID=" + ID +
                ", IDProduct=" + IDProduct +
                ", IDClient=" + IDClient +
                ", price=" + price +
                ", amount=" + amount;
    }
}
