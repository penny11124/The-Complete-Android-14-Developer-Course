package com.techmania.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    Button buttonSave;
    EditText userName,password;
    Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Writing Custom Object on Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("MyUsers");
        buttonSave = findViewById(R.id.buttonSave);
        userName = findViewById(R.id.editTextUserName);
        password = findViewById(R.id.editTextPassword);

        //Write data to Firebase
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataModel user1 = new DataModel(userName.getText().toString(),password.getText().toString());
                mDatabase.setValue(user1);
            }
        });



        buttonNext = findViewById(R.id.buttonNextActivity);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GoldActivity.class);
                startActivity(intent);
            }
        });
    }
}