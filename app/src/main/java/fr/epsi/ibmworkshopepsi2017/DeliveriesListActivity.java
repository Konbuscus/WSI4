package fr.epsi.ibmworkshopepsi2017;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

public class DeliveriesListActivity extends AppCompatActivity {


    private NfcAdapter nfcAdapter;
    TextView textViewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveries_list);


        textViewInfo = (TextView) findViewById(R.id.testNFC);
        //On obtient le NFC pour le RFID
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null)
        {
            Toast.makeText(this, "NFC pas supporté par ton appareil merdique, va acheter un téléphone récent gros PD",Toast.LENGTH_LONG).show();
            finish();
        }
        else if (!nfcAdapter.isEnabled()) {

            Toast.makeText(this, "ACTIVE TON NFC BORDEL", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    @Override
    protected  void onResume(){
        super.onResume();
        Intent intent = getIntent();
        String action  = intent.getAction();

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

    }
}
