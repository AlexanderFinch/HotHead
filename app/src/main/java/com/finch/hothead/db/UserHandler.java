package com.finch.hothead.db;

import android.content.ContextWrapper;

import com.finch.hothead.G;
import com.finch.hothead.db.tables.User;
import com.finch.hothead.utils.IOUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by finchrat on 7/9/2016.
 */
public class UserHandler {
    private static String userFile = "user_file.json";

    public static boolean save(ContextWrapper contextWrapper, User user) {
        String key = user.save();
        if (key != null) {
            user.setKey(key);
            IOUtils.saveJsonToInternal(contextWrapper, jsonize(user), userFile);

            G.user = user;
            return true;
        } else
            return false;
    }

    public static void delete(ContextWrapper contextWrapper, User user) {
        if (user != null) {
            user.delete();
        }
        G.user = new User();
        IOUtils.deleteFromInternal(contextWrapper, userFile);
    }

    public static User load(ContextWrapper contextWrapper) {
        User user = new User();
        try {
            JSONObject jObjUser = IOUtils.getJsonFromInternal(contextWrapper, userFile);
            user = dejsonize(jObjUser);
        } catch (JSONException | IOException e) {
            // TODO file not found
        }

        if (!user.isAppRegistered()) {
            user.createHash();
            IOUtils.saveJsonToInternal(contextWrapper, jsonize(user), userFile);
        }

        return user;
    }

    private static JSONObject jsonize(User user) {
        JSONObject jObj = new JSONObject();
        try {
            jObj.put("key", user.getKey() == null ? "" : user.getKey());
            jObj.put("screenName", user.getScreenName() == null ? "" : user.getScreenName());
            jObj.put("firstName", user.getFirstName() == null ? "" : user.getFirstName());
            jObj.put("lastName", user.getLastName() == null ? "" : user.getLastName());
            jObj.put("email", user.getEmail() == null ? "" : user.getEmail());
            jObj.put("code", user.getCode() == null ? "" : user.getCode());
            jObj.put("hash", user.getHash() == null ? "" : user.getHash());
        } catch (JSONException e) {
            //TODO something with the exception
        }
        return jObj;
    }

    private static User dejsonize(JSONObject jsonObject) throws JSONException {
        if (jsonObject != null) {
            String email, screenName, firstName, lastName, key, code, hash;
            key = jsonObject.optString("key");
            screenName = jsonObject.optString("screenName");
            firstName = jsonObject.optString("firstName");
            lastName = jsonObject.optString("lastName");
            email = jsonObject.optString("email");
            code = jsonObject.optString("code");
            hash = jsonObject.optString("hash");
            return new User(key, screenName, firstName, lastName, email, code, hash);
        }
        return new User();
    }
}
