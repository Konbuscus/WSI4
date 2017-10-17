package fr.epsi.ibmworkshopepsi2017;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import fr.epsi.ibmworkshopepsi2017.Models.Delivery;
import fr.epsi.ibmworkshopepsi2017.Tasks.GetDeliveryTask;
import fr.epsi.ibmworkshopepsi2017.ViewModel.MainViewModel;

public class DeliveriesListActivity extends AppCompatActivity {


    private NfcAdapter nfcAdapter;
    TextView textViewInfo;
    private DeliveryAdapter deliveryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveries_list);
        GetDeliveryTask getDeliveryTask = new GetDeliveryTask(this);
        getDeliveryTask.execute();

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
        ListView listView = (ListView) findViewById(R.id.deliveriesList);
        ArrayList<Delivery> deliveryList = MainViewModel.getInstance().getDeliveryList();
        deliveryAdapter = new DeliveryAdapter(this, 0, deliveryList);
        listView.setAdapter(deliveryAdapter);


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

    public DeliveryAdapter(Activity activity, int textViewResourceId, ArrayList<Delivery> deliveriesList){
        super (activity, textViewResourceId, deliveriesList);
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
            v = inflater.inflate(R.layout.item_deliveries, parent);
        }

        return v;
    }

}



