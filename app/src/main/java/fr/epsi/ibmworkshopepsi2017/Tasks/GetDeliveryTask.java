package fr.epsi.ibmworkshopepsi2017.Tasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import fr.epsi.ibmworkshopepsi2017.Models.Delivery;
import fr.epsi.ibmworkshopepsi2017.ViewModel.MainViewModel;
import android.os.AsyncTask;
/**
 * Created by HBxAa on 16/10/2017.
 * Get JSON data about deliveries from custom API
 */

public abstract class GetDeliveryTask extends AsyncTask<Object, Object, Void> {


    public GetDeliveryTask(){


    }


    public void getDelivery() throws JSONException {
        String response = ""; //API URL TO QUERY

        //DELIVERY CREATION
        Delivery delivery  = new Delivery();
        //JSON object
        JSONObject jsonReponse = new JSONObject(response);

        //Getting data and assigning them to our custom delivery object
        delivery.setDeliveryID(jsonReponse.getInt("deliveryID"));
        delivery.setAdress(jsonReponse.getString("adress"));
        delivery.setPrice(jsonReponse.getDouble("price"));
        delivery.setQuantity(jsonReponse.getInt("quantity"));
        delivery.setClientID(jsonReponse.getInt("clientId"));

        //Setting custom delivery to our singleton to retreive it easely in our activity
        MainViewModel.getInstance().SetDelivery(delivery);

    }

    public void getAllDelivery() throws JSONException {

        String response = "";
        JSONArray jsonArray = new JSONArray(response);
    }
}
