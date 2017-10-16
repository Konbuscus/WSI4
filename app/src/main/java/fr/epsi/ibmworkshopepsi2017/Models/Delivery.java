package fr.epsi.ibmworkshopepsi2017.Models;

import java.util.Date;

/**
 * Created by HBxAa on 16/10/2017.
 */

public class Delivery {


    //Private variable
    private int deliveryID;
    private Date dateDelivery;
    private int clientID;
    private int quantity;
    private String adress;
    private double price;


    //Constructor
    public Delivery(){

        this.deliveryID = getDeliveryID();
        this.clientID = getClientID();
        this.dateDelivery = getDateDelivery();
        this.quantity = getQuantity();
        this.adress = getAdress();
        this.price = getPrice();

    }

    //Getter And Setter
    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    public Date getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(Date dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
