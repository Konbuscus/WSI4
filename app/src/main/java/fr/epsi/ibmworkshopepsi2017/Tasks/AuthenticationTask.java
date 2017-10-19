package fr.epsi.ibmworkshopepsi2017.Tasks;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import fr.epsi.ibmworkshopepsi2017.Models.Delivery;

/**
 * Created by HBxAa on 19/10/2017.
 */

public class AuthenticationTask extends AsyncTask<String, Void, String> {



    public interface AutenticationListener {
        void onFinished(String result);
    }

    private final AutenticationListener taskListener;
    private CharSequence login;
    private CharSequence pass;



    public AuthenticationTask(AutenticationListener listener, CharSequence Login, CharSequence Pass){

        this.taskListener = listener;
        this.login =  Login;
        this.pass =  Pass;
    }
    @Override
    protected String doInBackground(String... params) {

        //Post sur l'URL avec login et pass
        BufferedReader reader=null;
        String response;

        // Send data
        try
        {

            String data = URLEncoder.encode("login", "UTF-8")
                    + "=" + URLEncoder.encode(login.toString(), "UTF-8");
            data+= "&" + URLEncoder.encode("pass", "UTF-8") + "="
                    + URLEncoder.encode(pass.toString(), "UTF-8");
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
