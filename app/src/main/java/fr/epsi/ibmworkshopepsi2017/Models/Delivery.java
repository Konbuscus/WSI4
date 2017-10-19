package fr.epsi.ibmworkshopepsi2017.Models;

import android.os.Parcelable;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by HBxAa on 16/10/2017.
 */

public class Delivery implements Serializable{


    //Private variable
    private int deliveryID;
    private Date dateDelivery;
    private int clientID;
    private int quantity;
    private String adress;
    private double price;
    private int typeDelivery;
    private int packageId;


    //Constructor
    public Delivery(){

        this.deliveryID = getDeliveryID();
        this.clientID = getClientID();
        this.dateDelivery = getDateDelivery();
        this.quantity = getQuantity();
        this.adress = getAdress();
        this.price = getPrice();
        this.typeDelivery  = getTypeDelivery();
        this.packageId = getPackageId();
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

    public int getTypeDelivery() {
        return typeDelivery;
    }

    public void setTypeDelivery(int typeDelivery) {
        this.typeDelivery = typeDelivery;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }
}
