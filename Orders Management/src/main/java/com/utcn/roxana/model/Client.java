package com.utcn.roxana.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Client {

    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String address;

    public Client() {

    }
    public Client(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Client(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Client" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'';
    }
}
