package fr.epsi.ibmworkshopepsi2017.Tasks;

import org.json.JSONException;
import org.json.JSONObject;

import fr.epsi.ibmworkshopepsi2017.Models.Delivery;
import fr.epsi.ibmworkshopepsi2017.ViewModel.MainViewModel;

/**
 * Created by HBxAa on 16/10/2017.
 * Va récupérer les livraison au format JSON depuis l'API afin de les stocker dans le viewModel
 */

public class GetDeliveryTask {


    public GetDeliveryTask(){


    }
    public void getDelivery() throws JSONException {
        String response = ""; //URL de l'API retournant le JSON

        //Instanciation du delivery
        Delivery delivery  = new Delivery();
        //JSON object
        JSONObject jsonReponse = new JSONObject(response);

        //Récupération de l'ID et assignation à notre Delivery
        delivery.setDeliveryID(jsonReponse.getInt("deliveryID"));
        delivery.setAdress(jsonReponse.getString("adress"));
        delivery.setPrice(jsonReponse.getDouble("price"));
        delivery.setQuantity(jsonReponse.getInt("quantity"));
        delivery.setClientID(jsonReponse.getInt("clientId"));

        //On assigne la delivery au viewModel. Il sera récupérable de partout
        MainViewModel.getInstance().SetDelivery(delivery);

    }
}
