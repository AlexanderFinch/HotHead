package com.finch.hothead.utils;

import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by finchrat on 7/9/2016.
 */
public class IOUtils {


    public static JSONObject getJsonFromAsset(AssetManager assetManager, String file) throws IOException {
        try {
            InputStream is = assetManager.open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new JSONObject(new String(buffer, "UTF-8"));
        } catch (IOException | JSONException e) {
            throw new IOException("Could not load from assets", e);
        }
    }

    public static JSONObject getJsonFromInternal(ContextWrapper contextWrapper, String file) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = contextWrapper.openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String in = br.readLine();
            while (in != null) {
                sb.append(in);
                in = br.readLine();
            }
            isr.close();

            return new JSONObject(sb.toString());
        } catch (IOException | JSONException e) {
            throw new IOException("Could not load from file system", e);
        }
    }

    public static void saveJsonToInternal(ContextWrapper contextWrapper, JSONObject jsonObject, String fileName) {
        try {
            FileOutputStream fos = contextWrapper.openFileOutput(fileName, ContextWrapper.MODE_PRIVATE);
            fos.write(jsonObject.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e("tag", "Could not save to file system", e);
        }
    }

    public static boolean deleteFromInternal(ContextWrapper contextWrapper, String fileName) {
        File file = new File(contextWrapper.getFilesDir(), fileName);
        return file.delete();
    }
}
