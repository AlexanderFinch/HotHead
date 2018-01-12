package com.finch.hothead.rest;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.finch.hothead.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by finchrat on 3/13/2017.
 */
abstract public class RestPostAdapter extends AsyncTask<String, Void, String> {
    private Dialog progress;
    private Context context;
    String title = "Loading...";
    String message = "Please wait";
    private String endpoint;
    private JSONObject data;

    public RestPostAdapter(Context context, String endpoint, JSONObject data) {
        this.context = context;
        this.endpoint = endpoint;
        this.data = data;
    }

    protected void onPreExecute() {
        progress = ProgressDialog.show(context, title, message);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls) {
        // TODO Do some validation here
        try {
            URL url = new URL(RestGetAdapter.REST_IP + endpoint);// + "/apiKey=" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
//            urlConnection.setRequestMethod("POST");
//            urlConnection.setDoInput(true);
//            urlConnection.setChunkedStreamingMode(params.toString().getBytes(StandardCharsets.UTF_8).length);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//            urlConnection.setConnectTimeout(Integer.parseInt(context.getResources().getString(R.string.connectionTimeOut)));
//            urlConnection.setReadTimeout(Integer.parseInt(context.getResources().getString(R.string.connectionTimeOut)));
//            urlConnection.setRequestProperty( "Content-Type", "text/html");
//            urlConnection.setRequestProperty( "charset", "utf-8");
//            urlConnection.setRequestProperty( "Content-Length", Integer.toString( postData.length ));
            OutputStream out = urlConnection.getOutputStream();
            out.write(data.toString().getBytes());
            out.flush();

            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                Log.e("tag", "Failed : HTTP error code : " + urlConnection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (urlConnection.getInputStream())));

            String output;
            Log.e("tag", "Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                Log.e("tag", output);
            }
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//            in.read();
//            String response = in.toString();
//                StringBuilder stringBuilder = new StringBuilder();
////                String line;
////                while ((line = bufferedReader.readLine()) != null) {
////                    stringBuilder.append("{\"response\":").append(line).append("}");
////                }
////                bufferedReader.close();
////                return stringBuilder.toString();

            urlConnection.disconnect();
        } catch (IOException e) {
            //todo something
            Log.e("ERROR", e.getMessage(), e);
        }
        return null;
    }

    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        progress.dismiss();
        if (response == null)
            return;
        try {
            updatePage(new JSONObject(response));
        } catch (JSONException e) {
            // todo something
        }
    }

    abstract protected void updatePage(JSONObject response);
}
