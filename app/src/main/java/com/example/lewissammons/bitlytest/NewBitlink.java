package com.example.lewissammons.bitlytest;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Lewis Sammons on 3/8/2017.
 */

public class NewBitlink extends AsyncTask<String, Void, String> {
    private
        String link = "http://www.";
        String oauthToken = "";
        String output = "";
    public NewBitlink(String url, String token) {
        //Error checking the url string
        if (url.substring(0,10) != "http://www."){
            //If we are lacking the http:// aspect add it
            if(url.substring(0,3) == "www.") link = "http://"  + url;
            //If we for some reason are only missing www. add it
            else if(url.substring(0,6) == "http://") link = "http://www."  + url.substring(7,url.length()-1);
            // If we got just a basic url add http://www.
            else link = "http://www." + url;
        }
        //if the passed url is aight then set it.
        else link = url;
        oauthToken = token;
    }
    public String getLink(){
        return link;
    }
    public String getOutput(){
        return output;
    }
    protected String doInBackground(String... params) {
        sendBitlink();
        return link;
    }
    public void sendBitlink() {
        try {
            //Create HTTP connections using the API URL
            URL url = new URL("https://api-ssl.bitly.com/v3/user/link_save?access_token=" + oauthToken + "&longUrl=" + link);
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
            //Recieve the data from the response header.
            //Currently not recievind data from link_save. Explore further.
            //Need to figure this out so we can let user know if we recieved an error or not.
            while (true) {
                readin = br.readLine();
                if(readin == null) {
                    Log.d("empty","now");
                    break;
                }
                sb.append(readin);
            }
            JSONObject json = new JSONObject(sb.toString());
            output = json.getString("data");
            connection.disconnect();
        } catch(Exception e){
            output = "Error";
            e.printStackTrace();
        }
    }
}
