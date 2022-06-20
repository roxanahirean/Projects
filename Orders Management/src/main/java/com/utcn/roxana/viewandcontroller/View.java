package com.utcn.roxana.viewandcontroller;


import com.utcn.roxana.dataAccess.ClientDA;
import com.utcn.roxana.dataAccess.ProductDA;
import com.utcn.roxana.model.Client;
import com.utcn.roxana.model.Product;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class View extends JFrame {
    @Getter
    @Setter
    private final JTextField IDc = new JTextField(33);
    @Getter
    @Setter
    private final JTextField nume = new JTextField(33);
    @Getter
    @Setter
    private final JTextField adresa = new JTextField(33);
    @Getter
    @Setter
    private final JTextField IDp = new JTextField(30);
    @Getter
    @Setter
    private final JTextField denumire = new JTextField(30);
    @Getter
    @Setter
    private final JTextField cantitate = new JTextField(30);
    @Getter
    @Setter
    private final JTextField pret = new JTextField(30);
    @Getter
    @Setter
    private final JComboBox<Integer> client = new JComboBox<>();
    @Getter
    @Setter
    private final JComboBox<Integer> produs = new JComboBox<>();
    @Getter
    @Setter
    private final JTextField cantitateComanda = new JTextField(10);
    private final JButton adC = new JButton("Adaugare");
    private final JButton adP = new JButton("Adaugare");
    private final JButton editC = new JButton("Editare");
    private final JButton editP = new JButton("Editare");
    private final JButton delC = new JButton("Stergere");
    private final JButton delP = new JButton("Stergere");
    private final JButton vizC = new JButton("Vizualizare");
    private final JButton vizP = new JButton("Vizualizare");
    private final JButton comanda = new JButton("Plasare");
    JTextArea textArea1 = new JTextArea();
    JTextArea textArea2 = new JTextArea();

    public View() {
        JScrollPane scrollPane1 = new JScrollPane(textArea1);
        scrollPane1.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setPreferredSize(new Dimension(400, 200));

        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        scrollPane2.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane2.setPreferredSize(new Dimension(400, 200));

        JTabbedPane tabbedPane = new JTabbedPane();
        ClientDA c = new ClientDA();
        ProductDA prod = new ProductDA();
        JPanel card1 = new JPanel();
        JPanel label = new JPanel();
        JPanel tf = new JPanel();
        JPanel button = new JPanel();
        JPanel p = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        card1.setLayout(new GridLayout(0,1));
        card1.setBorder(BorderFactory.createEmptyBorder(50,15,0,0));
        label.setLayout(new GridLayout(0,1, 9, 9));
        tf.setLayout(new GridLayout(0,1, 7, 7));

        label.add(new JLabel("ID:"));
        tf.add(IDc);
        label.add(new JLabel("NUME:"));
        tf.add(nume);
        label.add(new JLabel("ADRESA:"));
        tf.add(adresa);
        p.add(label);
        p.add(tf);

        button.add(adC);
        button.add(editC);
        button.add(delC);
        button.add(vizC);

        card1.add(p);
        card1.add(button);
        panel1.add(card1);
        panel1.add(scrollPane1);

        JPanel card2 = new JPanel();
        JPanel label1 = new JPanel();
        JPanel tf1 = new JPanel();
        JPanel button1 = new JPanel();
        JPanel p1 = new JPanel();

        card2.setLayout(new GridLayout(0,1));
        card2.setBorder(BorderFactory.createEmptyBorder(20,11,0,15));
        label1.setLayout(new GridLayout(0,1, 4, 4));
        tf1.setLayout(new GridLayout(0,1, 3, 3));

        label1.add(new JLabel("ID PRODUS:"));
        tf1.add(IDp);
        label1.add(new JLabel("DENUMIRE:"));
        tf1.add(denumire);
        label1.add(new JLabel("CANTITATE:"));
        tf1.add(cantitate);
        label1.add(new JLabel("PRET:"));
        tf1.add(pret);
        p1.add(label1);
        p1.add(tf1);

        button1.add(adP);
        button1.add(editP);
        button1.add(delP);
        button1.add(vizP);

        card2.add(p1);
        card2.add(button1);
        panel2.add(card2);
        panel2.add(scrollPane2);

        JPanel card3 = new JPanel();
        JPanel label2 = new JPanel();
        JPanel tf2 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel button2 = new JPanel();

        card3.setLayout(new GridLayout(0,1));
        card3.setBorder(BorderFactory.createEmptyBorder(40,20,0,20));
        label2.setLayout(new GridLayout(0,1, 17, 17));
        tf2.setLayout(new GridLayout(0,1, 10, 10));

        List<Client> clienti = c.findAll();
        List<Product> produse = prod.findAll();
        label2.add(new JLabel("ID Client: "));
        if(produse.size() != 0){
            for(Product pr: produse)
                produs.addItem(pr.getID());}
        if(clienti.size() != 0)
            for(Client cl:clienti)
                client.addItem(cl.getId());

        tf2.add(client);
        label2.add(new JLabel("Produs: "));
        tf2.add(produs);
        label2.add(new JLabel("Cantitate: "));
        tf2.add(cantitateComanda);
        button2.add(comanda);

        p2.add(label2);
        p2.add(tf2);

        card3.add(p2);
        card3.add(button2);

        tabbedPane.addTab("CLIENTI", panel1);
        tabbedPane.addTab("PRODUSE", panel2);
        tabbedPane.addTab("COMENZI", card3);

        this.setContentPane(tabbedPane);
        this.pack();
        this.setTitle("DB");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public int getIDc() {
        return Integer.parseInt(IDc.getText());
    }

    public int getIDp() {
        return Integer.parseInt(IDp.getText());
    }

    public String getNume() {
        return nume.getText();
    }

    public String getDenumire() {
        return denumire.getText();
    }

    public String getAdresa() {
        return adresa.getText();
    }

    public int getPret() {
        return Integer.parseInt(pret.getText());
    }

    public int getCantitate() {
        return Integer.parseInt(cantitate.getText());
    }

    public void setText1(String text){
        textArea1.setText(text);
    }
    public void setText2(String text){
        textArea2.setText(text);
    }

    public void adCListener(ActionListener act) {
        adC.addActionListener(act);
    }

    public void adPListener(ActionListener act) {
        adP.addActionListener(act);
    }

    public void editCListener(ActionListener act) {
        editC.addActionListener(act);
    }

    public void editPListener(ActionListener act) {
        editP.addActionListener(act);
    }

    public void delCListener(ActionListener act) {
        delC.addActionListener(act);
    }

    public void delPListener(ActionListener act) {
        delP.addActionListener(act);
    }

    public void vizCListener(ActionListener act) {
        vizC.addActionListener(act);
    }

    public void vizPListener(ActionListener act) {
        vizP.addActionListener(act);
    }

    public void comandListener(ActionListener act) {
        comanda.addActionListener(act);
    }


}
