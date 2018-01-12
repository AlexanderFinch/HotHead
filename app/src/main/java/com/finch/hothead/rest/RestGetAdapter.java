package com.finch.hothead.rest;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.finch.hothead.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by finchrat on 3/13/2017.
 */
abstract public class RestGetAdapter extends AsyncTask<String, Void, String> {
    public static final String REST_IP = "http://66.75.47.155:12557";
//    public static final String REST_IP = "http:// 192.168.1.123:12557";
    private static final String API_KEY = "123456";
    Dialog progress;
    Context context;
    String title = "Loading...";
    String message = "Please wait";
    private String endpoint;

    public RestGetAdapter(Context context, String endpoint){
        this.context = context;
        this.endpoint = endpoint;
    }

    protected void onPreExecute(){
        progress = ProgressDialog.show(context, title, message);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls) {
        String params = urls.length > 0 ? urls[0] : "";

        try {
            URL url = new URL(REST_IP + endpoint + params);// + "/apiKey=" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(Integer.parseInt(context.getResources().getString(R.string.connectionTimeOut)));
            urlConnection.setReadTimeout(Integer.parseInt(context.getResources().getString(R.string.connectionTimeOut)));
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append("{\"response\":").append(line).append("}");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response){
        super.onPostExecute(response);
        progress.dismiss();
        if(response == null) {
            error();
        } else {
            try {
                success(new JSONObject(response));
            } catch (JSONException e) {
                // todo something
            }
        }
    }

    abstract protected void success(JSONObject response);
    abstract protected void error();
}
