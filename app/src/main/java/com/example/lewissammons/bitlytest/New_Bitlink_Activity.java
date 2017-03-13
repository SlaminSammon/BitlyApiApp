package com.example.lewissammons.bitlytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class New_Bitlink_Activity extends AppCompatActivity {
    private
        NewBitlink bitlink;
        String token = "";
        String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        token = intent.getStringExtra("oauthToken");
        setContentView(R.layout.activity_new__bitlink_);
    }
    public void sendBitlink(View view){
        //Grab the value that the user inputted.
        EditText edited = (EditText) findViewById(R.id.newLinkEdit);
        link = edited.getText().toString();
        //Pass data to local NewBitlink
        bitlink = new NewBitlink(link, token);
        //Execute bitlink send thread
        bitlink.execute();
        TextView txt = (TextView) findViewById(R.id.outputCheck);
        //If we recieved an error due to an invalid URL, let user know
        if(bitlink.getOutput() == "Error") Toast.makeText(getApplicationContext(), "Invalid URL", Toast.LENGTH_LONG).show();
    }
}
