package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hackathon.volunteer.LoginActivity;

public class Replicating_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replicating_activity);
        ((ImageView)findViewById(R.id.a1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Replicating_activity.this,MyBookings.class));
            }
        });

        ((ImageView)findViewById(R.id.a2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Replicating_activity.this,PassActivity.class));
            }
        });
        ((ImageView)findViewById(R.id.a3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Replicating_activity.this, LoginActivity.class));
            }
        });
        ((ImageView)findViewById(R.id.a4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Replicating_activity.this,MapsZone.class));
            }
        });
        ((ImageView)findViewById(R.id.a5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Replicating_activity.this,Doctor.class));
            }
        });
        ((ImageView)findViewById(R.id.a6)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Replicating_activity.this,Labs.class));
            }
        });
        ((ImageView)findViewById(R.id.a7)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Replicating_activity.this,));
            }
        });
        ((ImageView)findViewById(R.id.a8)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Replicating_activity.this,));
            }
        });
        ((ImageView)findViewById(R.id.a9)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Replicating_activity.this,));
            }
        });
    }
}