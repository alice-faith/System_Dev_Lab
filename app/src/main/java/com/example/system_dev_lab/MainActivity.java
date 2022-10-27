package com.example.system_dev_lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Activities.DocLoginActivity;
import Activities.LoginActivity;

public class MainActivity extends AppCompatActivity {

    //Declare UI
     Button signUpPatBtn, signUpDocBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize view
        signUpPatBtn = findViewById(R.id.signUpPatBtn);
        signUpDocBtn = findViewById(R.id.signUpDocBtn);

        //handle signUpPatBtn listener
        signUpPatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Patient Login Activity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //handle signUpDocBtn listener
        signUpDocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Patient Login Activity
                Intent intent = new Intent(MainActivity.this, DocLoginActivity.class);
                startActivity(intent);
            }
        });

    }
}