package fr.epsi.ibmworkshopepsi2017.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class CallAPI extends AsyncTask<String, String, String> {
    private Context c;
    private String ret;
    private MyCallBack callback;

    public CallAPI(Context pC, MyCallBack pCallback){
        //set context variables if required
        c = pC;
        callback = pCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0]; // URL to call
        String data = params[1]; //data to post
        ret = "";

        Log.d("test",urlString);

        try {
            ret = performPostCall(urlString, data);
            Log.d("testret", "bdzf zddfs dsd fsd fsd f::::");
            Log.d("testret", "res : " + ret);
            Log.d("test","yeah !!!!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Log.d("test","nope !!!!");
        }

        return urlString;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        callback.callback(ret);
    }

    public static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public String  performPostCall(String requestURL,
                                   String postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(postDataParams);

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response=String.valueOf(responseCode);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}

