package com.example.lewissammons.bitlytest;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.*;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Lewis Sammons on 3/7/2017.
 */

public class Link_History extends AsyncTask<String, Void, String> {
    private
        String OauthToken = "";
        ArrayList<String> bitlinks = new ArrayList<String>();
        ArrayList<String> titleList = new ArrayList<String>();
        int code = 0;
        String data = "";
    public Link_History(String token){
        OauthToken = token;
    }
    public int getCode(){
        return code;
    }
    public ArrayList<String> getBitlinks(){
        return bitlinks;
    }
    public ArrayList<String> getTitles(){
        return titleList;
    }
    public String getData(){
        return data;
    }
    protected  String doInBackground(String... params) {
        getLinkHistory(OauthToken);
        return data;
    }
    public void getLinkHistory(String oauthToken) {
        try {
            //Hard coding in generic access token
            URL url = new URL("https://api-ssl.bitly.com/v3/user/link_history?access_token=" + oauthToken);
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
            //Grab the large Json object passed "data"
            JSONObject json = new JSONObject(sb.toString());
            data = json.getString("data");
            json = new JSONObject(data);
            //Grab the nested JSON array "link_history"
            JSONArray jarray = json.getJSONArray("link_history");
            //for all the json objects in the array get the title and bitlink
            for(int i =0; i< jarray.length();++i){
                json = jarray.getJSONObject(i);
                bitlinks.add(json.getString("aggregate_link"));
                titleList.add(json.getString("title"));
            }
            connection.disconnect();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
