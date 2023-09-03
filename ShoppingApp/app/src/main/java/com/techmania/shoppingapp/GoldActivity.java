package com.techmania.shoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GoldActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private Button button;
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold);

        mDatabase = FirebaseDatabase.getInstance().getReference("GoldPrice").child("price");

        //Write data to Firebase
        //Write a simple data (not custom object)

        //Update the GoldPrice to 2000 upon launching the app
        mDatabase.setValue("2000");

        button = findViewById(R.id.button);
        editText = findViewById(R.id.editTextNumber);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.setValue(editText.getText().toString());
            }
        });

        //Reading data from Firebase
        textView = findViewById(R.id.textViewGoldPrice);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //When any change in the data occurs
                textView.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}