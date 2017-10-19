package fr.epsi.ibmworkshopepsi2017.Tasks;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by HBxAa on 19/10/2017.
 */

public class AuthenticationTask extends AsyncTask<String, Void, String> {



    public interface AuthenticationListener {
        void onFinished(String result);
    }

    private final AuthenticationListener taskListener;
    private String storagecenter;
    private String deliveryman;



    public AuthenticationTask(AuthenticationListener listener, String storageCenterID, String DeliveryManID){

        this.taskListener = listener;
        this.storagecenter =  storageCenterID;
        this.deliveryman =  DeliveryManID;
    }
    @Override
    protected String doInBackground(String... params) {

        //Posting on URL
        BufferedReader reader=null;
        String response;

        // Send data
        try
        {

            String data = URLEncoder.encode("storageCenterID", "UTF-8")
                    + "=" + (storagecenter);
            data+= "&" + URLEncoder.encode("deliveryManID", "UTF-8") + "="
                    + (deliveryman);
            // Defined URL  where to send data
            URL url = new URL("http://yoanmercier.fr/tests/connection.php");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line);
            }


            response = sb.toString();
            return response;
        }
        catch(Exception ex)
        {

        }
        return null;
    }


    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);
        if(this.taskListener != null){
            this.taskListener.onFinished(result);
        }
    }



}
