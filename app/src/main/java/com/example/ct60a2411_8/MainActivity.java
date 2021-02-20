package com.example.ct60a2411_8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myInit();
    }

    public void myInit() {
        System.out.println("Initializing project CT60A2411");
        System.out.println("Local Git repository created");
        System.out.println("GitHub connected");
    }
}