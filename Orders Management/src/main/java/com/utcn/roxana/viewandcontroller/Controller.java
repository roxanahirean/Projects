package com.utcn.roxana.viewandcontroller;


import com.utcn.roxana.businesslogic.ClientBL;
import com.utcn.roxana.businesslogic.OrderBL;
import com.utcn.roxana.businesslogic.ProductBL;
import com.utcn.roxana.dataAccess.ProductDA;
import com.utcn.roxana.model.Client;
import com.utcn.roxana.model.Order;
import com.utcn.roxana.model.Product;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Controller {
    private ClientBL c;
    private ProductBL p;
    private OrderBL com;
    private View v;

    Controller() {
        c = new ClientBL();
        p = new ProductBL();
        com = new OrderBL();
        v = new View();
        v.adCListener(new acListener());
        v.adPListener(new apListener());
        v.editCListener(new ecListener());
        v.editPListener(new epListener());
        v.delCListener(new dcListener());
        v.delPListener(new dpListener());
        v.vizCListener(new vcListener());
        v.vizPListener(new vpListener());
        v.comandListener(new cListener());
    }

    class acListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Client cl = new Client(v.getIDc(), v.getNume(), v.getAdresa());
            c.insert(cl);
        }
    }

    class apListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Product prod = new Product(v.getIDp(), v.getDenumire(), v.getCantitate(), v.getPret());
            p.insert(prod);
        }
    }

    class dcListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            c.delete(v.getIDc());
        }
    }


    class dpListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            p.delete(v.getIDp());
        }
    }

    class ecListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Client cl = new Client(v.getIDc(), v.getNume(), v.getAdresa());
            c.update(cl);
        }
    }
    class epListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Product prod = new Product(v.getIDp(), v.getDenumire(), v.getCantitate(), v.getPret());
            p.update(prod);
        }
    }

    class vpListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            List<Product> produse = p.findAll();
            String s = "";
            for(Product prod: produse)
                s = s + prod.toString() + "\n";
            v.setText2(s);
        }
    }

    class vcListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            List<Client> clienti = c.findAll();
            String s = "";
            for(Client prod:clienti)
                s = s + prod.toString() + "\n";
            v.setText1(s);
        }
    }


    class cListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            List<Order> comenzi = com.findAll();
            ProductDA p = new ProductDA();
            int id = 0;
            if(comenzi.size() != 0)
                id = comenzi.get(comenzi.size() - 1).getID() + 1;
            System.out.println(id);
            String x = String.valueOf(v.getProdus().getSelectedItem());
            String y = String.valueOf(v.getClient().getSelectedItem());
            Product product = p.findById(Integer.parseInt(x));
            int pret = product.getPrice() * Integer.parseInt(v.getCantitateComanda().getText());
            Order order = new Order(id, Integer.parseInt(x), Integer.parseInt(y), pret, Integer.parseInt(v.getCantitateComanda().getText()));
            com.placeOrder(order);
            String s="";
            try {
                FileWriter fileWriter = new FileWriter("D:\\TP\\PT2022_30224_Hirean_Roxana_Assignment_3\\save.txt");
                for(Order i:comenzi){
                    s=s+i.toString();
                }
                s += "\n";
                fileWriter.write(s+order+"\n");
                fileWriter.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        Controller c = new Controller();
    }
}
