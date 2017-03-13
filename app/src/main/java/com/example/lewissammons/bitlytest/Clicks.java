package com.example.lewissammons.bitlytest;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Lewis Sammons on 3/11/2017.
 */

public class Clicks extends AsyncTask<String, Void, String> {
    private
        String OauthToken = "";
        int clickNum = 8000;
        String link = "";
    public Clicks(String token, String url){
        OauthToken = token;
        link = url;
    }
    public int getClicks(){
        return clickNum;
    }

    protected  String doInBackground(String... params) {
        getClickTotal();
        return link;
    }
    public void getClickTotal() {
        try {
            //Hard coding in generic access token
            URL url = new URL("https://api-ssl.bitly.com/v3/link/clicks?access_token=" + OauthToken + "&link=" + link);
            //Open connection to the URL
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            //Check to see ifw e got a 200 as response "ok"
            if(connection.getResponseCode() != 200){
                throw new RuntimeException("Failure: Bad HTTP response" + connection.getResponseCode());
            }
            //Open up communications
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String readin;
            //Read data from the response headers
            while (true) {
                readin = br.readLine();
                if(readin == null) break;
                sb.append(readin);
            }
            String data = "";
            //Grab the large Json object passed "data"
            JSONObject json = new JSONObject(sb.toString());
            data = json.getString("data");
            json = new JSONObject(data);
            //Get the amount of clicks which is stored in "linkclicks"
            clickNum = json.getInt("link_clicks");
            Log.d("sb",Integer.toString(clickNum));
            connection.disconnect();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
