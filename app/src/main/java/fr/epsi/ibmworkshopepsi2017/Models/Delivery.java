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
    private String deliveryID;
    private Date dateDelivery;
    private String clientID;
    private int quantity;
    private String adress;
    private double price;
    private int typeDelivery;
    private String packageId;


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
    public String getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(String deliveryID) {
        this.deliveryID = deliveryID;
    }

    public Date getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(Date dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
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

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
}
