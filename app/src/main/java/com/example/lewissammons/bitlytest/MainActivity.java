package com.example.lewissammons.bitlytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String oauthToken = "5e35ea765a403ba93f1943ef81c8a6015814c3a4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void showLinkHistory(View view){
        Intent myIntent = new Intent(MainActivity.this, Link_History_Activity.class);
        myIntent.putExtra("oauthToken", oauthToken);
        MainActivity.this.startActivity(myIntent);
    }public void goNewBitlink(View view){
        Intent myIntent = new Intent(MainActivity.this, New_Bitlink_Activity.class);
        myIntent.putExtra("oauthToken", oauthToken);
        MainActivity.this.startActivity(myIntent);
    }

}
