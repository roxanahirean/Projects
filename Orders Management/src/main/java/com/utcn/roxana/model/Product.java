package com.utcn.roxana.model;

import lombok.Getter;
import lombok.Setter;

public class Product {
    @Getter
    @Setter
    private int ID;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int price;
    @Getter
    @Setter
    private int amount;

    public Product() {
    }

    public Product(int ID, String name, int amount, int price) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Product" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount;
    }
}
