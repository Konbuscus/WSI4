package fr.epsi.ibmworkshopepsi2017;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentManagerNonConfig;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.epsi.ibmworkshopepsi2017.Models.Delivery;
import fr.epsi.ibmworkshopepsi2017.Tasks.CallAPI;
import fr.epsi.ibmworkshopepsi2017.Tasks.GetDeliveryTask;
import fr.epsi.ibmworkshopepsi2017.Tasks.MyCallBack;
import fr.epsi.ibmworkshopepsi2017.ViewModel.MainViewModel;

public class DeliveriesListActivity extends AppCompatActivity implements  WaitingForConfirmation.OnFragmentInteractionListener, DialogInterface.OnDismissListener {


    private NfcAdapter nfcAdapter;
    TextView textViewInfo;
    private DeliveryAdapter deliveryAdapter;
    private Activity self;
    private String centerSotrageID;
    private String deliveryManId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveries_list);
        self = this;

        CallAPI callAPI = new CallAPI(getApplicationContext(), new MyCallBack() {
            @Override
            public void callback(String pText) {

                try {
                    JSONObject response = new JSONObject(pText);
                    ArrayList<Delivery> deliveryList = new ArrayList<>();
                    boolean connect = response.getBoolean("success");
                    if (connect) {

                        //Getting infos
                        for(int i = 0; i < response.getJSONArray("Deliveries").length(); i++){

                            JSONObject obj = response.getJSONArray("Deliveries").getJSONObject(i);

                            Delivery delivery = new Delivery();
                            delivery.setDeliveryID(obj.getString("deliveryID"));
                            delivery.setAdress(obj.getString("address"));
                            delivery.setPrice(obj.getDouble("price"));
                            delivery.setQuantity(obj.getInt("quantity"));
                            delivery.setClientID(obj.getString("clientID"));
                            delivery.setTypeDelivery(obj.getInt("typeDelivery"));
                            delivery.setPackageId(obj.getString("packageID"));
                            //Adding delivery to the final list to display
                            deliveryList.add(delivery);

                        }

                        final ListView listView = (ListView) findViewById(R.id.deliveriesList);
                        deliveryAdapter = new DeliveryAdapter(self, deliveryList);
                        listView.setAdapter(deliveryAdapter);


                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Delivery deliveryClicked = deliveryAdapter.getItem(position);
                                Bundle args = new Bundle();
                                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                                //Case where deliveryType is from a Company to a Company
                                if (deliveryClicked.getTypeDelivery() == 0) {
                                    //Fragment with delivery as parameters
                                    //In function of Type, setting RFID or QRCode
                                    args.putBoolean("isFromCompanyToCompany", true);
                                }
                                //Case where deliveryType is from a Company to a customer
                                else {

                                    //Fragment with delivery as parameters
                                    //In function of Type, setting RFID or QRCode
                                    args.putBoolean("isFromCompanyToCompany", false);
                                }

                                args.putSerializable("currentDelivery", deliveryClicked);
                                WaitingForConfirmation waitingForConfirmation = new WaitingForConfirmation();
                                waitingForConfirmation.setArguments(args);
                                waitingForConfirmation.show(fm, "WaitingForConfirmation");

                            }
                        });


                    } else {

                        Log.d("n", "fuck u");
                        //v.invalidate();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        HashMap<String, String> fuckMum =  (HashMap) getIntent().getSerializableExtra("EXTRA_HASH");
        String stringifiedParams = "";
        try {
            stringifiedParams = CallAPI.getPostDataString2(fuckMum);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        callAPI.execute("http://148.100.4.216:3000/GetDeliveries", stringifiedParams);


        /*GetDeliveryTask task = new GetDeliveryTask(new GetDeliveryTask.TaskListener() {
            @Override
            public void onFinished(ArrayList<Delivery> result) {
                // Do Something after the task has finished
                MainViewModel.getInstance().setDeliveryList(result);
                ArrayList<Delivery> deliveryList =  MainViewModel.getInstance().getDeliveryList();
                final ListView listView = (ListView) findViewById(R.id.deliveriesList);
                deliveryAdapter = new DeliveryAdapter(self, deliveryList);
                listView.setAdapter(deliveryAdapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Delivery deliveryClicked = deliveryAdapter.getItem(position);
                        Bundle args = new Bundle();
                        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                        //Case where deliveryType is from a Company to a Company
                        if(deliveryClicked.getTypeDelivery() == 0)
                        {
                            //Fragment with delivery as parameters
                            //In function of Type, setting RFID or QRCode
                            args.putBoolean("isFromCompanyToCompany", true);
                        }
                        //Case where deliveryType is from a Company to a customer
                        else{

                            //Fragment with delivery as parameters
                            //In function of Type, setting RFID or QRCode
                            args.putBoolean("isFromCompanyToCompany", false);
                        }

                        args.putSerializable("currentDelivery", deliveryClicked);
                        WaitingForConfirmation waitingForConfirmation = new WaitingForConfirmation();
                        waitingForConfirmation.setArguments(args);
                        waitingForConfirmation.show(fm, "WaitingForConfirmation");

                    }
                });

            }
        });

        task.execute();*/



      //  textViewInfo = (TextView) findViewById(R.id.testNFC);
        //On obtient le NFC pour le RFID
        /*nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null)
        {
            Toast.makeText(this, "NFC pas supporté par ton appareil merdique, va acheter un téléphone récent gros PD",Toast.LENGTH_LONG).show();
            finish();
        }
        else if (!nfcAdapter.isEnabled()) {

            Toast.makeText(this, "ACTIVE TON NFC BORDEL", Toast.LENGTH_LONG).show();
            finish();
        }*/


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDismiss(DialogInterface dialog) {

        //Fragment has been dismissed
        //Refreshing listview and recalling asynchtask
        View v = (View) getWindow().getDecorView().findViewById(android.R.id.content);
        v.invalidate();


    }



    //NFC BULLSHITO
    /*@Override
    protected  void onResume(){
        super.onResume();
        Intent intent = getIntent();
        String action = intent.getAction();
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)){

            Toast.makeText(this, "TAG DECOUVERT", Toast.LENGTH_SHORT).show();

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
           if(tag == null)
            {
                textViewInfo.setText("tag == null");
            }else{
                String tagInfo = tag.toString() + "\n";

                tagInfo += "\nTag Id: \n";
                byte[] tagId = tag.getId();
                tagInfo += "length = " + tagId.length +"\n";
                for(int i=0; i<tagId.length; i++){
                    tagInfo += Integer.toHexString(tagId[i] & 0xFF) + " ";
                }
                tagInfo += "\n";

                String[] techList = tag.getTechList();
                tagInfo += "\nTech List\n";
                tagInfo += "length = " + techList.length +"\n";
                for(int i=0; i<techList.length; i++){
                    tagInfo += techList[i] + "\n ";
                }

                textViewInfo.setText(tagInfo);
            }
        }else{
            Toast.makeText(this,
                    "onResume() : " + action,
                    Toast.LENGTH_SHORT).show();
        }

    }*/


}

class DeliveryAdapter extends ArrayAdapter<Delivery> {

    private Activity activity;
    private ArrayList<Delivery> deliveryList;
    private static LayoutInflater inflater = null;

    public DeliveryAdapter(Activity activity, ArrayList<Delivery> deliveriesList){
        super (activity, 0, deliveriesList);
        try{
            this.activity = activity;
            this.deliveryList = deliveriesList;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch(Exception e){
            String failure = e.getMessage();

        }

    }

    public int getCount(){
        return deliveryList.size();
    }

    public Delivery getItem(Delivery position)
    {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {

        View v = convertView;
        if(convertView == null)
        {
            v = LayoutInflater.from(getContext()).inflate(R.layout.item_deliveries, parent, false);
        }

        TextView castedView = (TextView) v.findViewById(R.id.item_delivery);
        Delivery delivery = getItem(position);
        castedView.setText(delivery.getAdress());

        return v;
    }

}



