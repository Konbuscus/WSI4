package fr.epsi.ibmworkshopepsi2017.Tasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.epsi.ibmworkshopepsi2017.MainActivity;
import fr.epsi.ibmworkshopepsi2017.Models.Delivery;
import fr.epsi.ibmworkshopepsi2017.Utilities;
import fr.epsi.ibmworkshopepsi2017.ViewModel.MainViewModel;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by HBxAa on 16/10/2017.
 * Get JSON data about deliveries from custom API
 */

    public class GetDeliveryTask extends AsyncTask<Void, Void, Void> {


    private Context mContext;

    public GetDeliveryTask(Context context){

        this.mContext = context;

    }
    ArrayList<Delivery> deliveryList;
    ProgressDialog progressDialog;


    @Override
    protected Void doInBackground(Void... Params) {
        try {
            getDelivery();

        } catch (JSONException e) {

            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        deliveryList = new ArrayList<Delivery>();
        /*//Passing Context to progress dialog | Displaying infos
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Searching Deliveries ...");
        //progressDialog.getWindow().setGravity(Gravity.CENTER);
        progressDialog.show();*/
    }


    @Override
    protected void onPostExecute(Void result)
    {
        super.onPostExecute(result);
        storeData(deliveryList);
    }



    public void getDelivery() throws JSONException {
        String response = "http://yoanmercier.fr/test.json"; //API URL TO QUERY

        Utilities utilities = new Utilities();
        String jsonResponse = utilities.GetJson(response);

        ArrayList<Delivery> deliveryList = new ArrayList<>();
        //JSON object
        JSONObject jsonReponse = new JSONObject(jsonResponse);

        for(int i = 0; i < jsonReponse.getJSONArray("Deliveries").length(); i++)
        {
            //DELIVERY CREATION
            JSONObject obj = jsonReponse.getJSONArray("Deliveries").getJSONObject(i);

            Delivery delivery  = new Delivery();
            //Getting data and assigning them to our custom delivery object
            delivery.setDeliveryID(obj.getInt("deliveryID"));
            delivery.setAdress(obj.getString("address"));
            delivery.setPrice(obj.getDouble("price"));
            delivery.setQuantity(obj.getInt("quantity"));
            delivery.setClientID(obj.getInt("clientId"));
            //Adding delivery to the final list to display
            deliveryList.add(delivery);
        }




    }


    public void storeData(ArrayList<Delivery> deliveryList)
    {
        MainViewModel.getInstance().setDeliveryList(deliveryList);
    }
}
