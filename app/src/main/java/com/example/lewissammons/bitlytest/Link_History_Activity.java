package com.example.lewissammons.bitlytest;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


public class Link_History_Activity extends AppCompatActivity{
    String oauthToken = "";
    Link_History lh;
    Clicks click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Data from previous activity
        Intent intent = getIntent();
        lh = new Link_History(intent.getStringExtra("oauthToken"));
        oauthToken = intent.getStringExtra("oauthToken");
        //Execute thread
        lh.execute();
        setContentView(R.layout.activity_link__history_);
    }
    public void displayData(View view){
        //Change the text box, to the data from the API
        TextView txt = (TextView) findViewById(R.id.textbox);
        txt.setClickable(true);
        txt.setMovementMethod(LinkMovementMethod.getInstance());
        txt.setText(setOutput(lh.getBitlinks(), lh.getTitles()));
    }
    //setOutput displays both the bitlinks and the amount of clicks they have generated.
    //Each bitlink that is in the arraylist bitlinks, is passed to a clicks object
    //which generates the amount of clicks that that link has.
    public String setOutput(ArrayList<String> bitlinks, ArrayList<String>  titles){
        //Looks strange, but thetext view is appearing in the wrong spot, this is a quick fix.
        String text = "\n\n\n\n\n\n\n\n\n";
        String clickText = text;
        TextView clickTxt = (TextView) findViewById(R.id.clickNum);
        //bitlinks and titles will be the same size
        for(int i = 0; i<bitlinks.size(); ++i){
            click = new Clicks(oauthToken, bitlinks.get(i));
            click.execute();
            //Have main thread sleep so  the URL threads can catch up.
            SystemClock.sleep(150);
            //Append two different string objects that
            clickText += "\n" + Integer.toString(click.getClicks()) + "\n";
            text += "\n" + titles.get(i) + ":\n" + bitlinks.get(i) + "\n";
        }
        clickTxt.setText(clickText);
        return text;
    }
}
