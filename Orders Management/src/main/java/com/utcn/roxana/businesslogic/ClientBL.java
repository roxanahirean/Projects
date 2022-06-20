package com.utcn.roxana.businesslogic;

import com.utcn.roxana.dataAccess.ClientDA;
import com.utcn.roxana.model.Client;

import java.util.List;
import java.util.NoSuchElementException;

public class ClientBL {
    private final ClientDA client;

    public ClientBL() {
        client = new ClientDA();
    }



    public List<Client> findAll() {
        List<Client> c = client.findAll();
        if(c == null) {
            throw new NoSuchElementException("there are no clients in the database\n");
        }
        return c;
    }
    public void insert(Client cl) {
        Client c = client.insert(cl);
        if(c == null) {
            throw new NoSuchElementException("insertion cannot be done\n");
        }
    }
    public void update(Client cl) {
        Client c = client.update(cl);
        if(c == null) {
            throw new NoSuchElementException("update cannot be done\n");
        }
    }
    public void delete(int id) {
        int i = client.delete(id);
        if(i == 0) {
            throw new NoSuchElementException("delete cannot be done");
        }

    }
}
