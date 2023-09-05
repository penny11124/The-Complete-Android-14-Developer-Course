package com.techmania.journalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import util.JournalUser;

public class MainActivity extends AppCompatActivity {

    //Widgets
    Button buttonLogIn, buttonCreateAcc;
    private EditText editTextEmail,editTextPassword;

    //Firebase Authentication
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    //Firebase Connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collectionReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogIn = findViewById(R.id.buttonLogIn);
        buttonCreateAcc = findViewById(R.id.buttonCreateAccount);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        //Initialize the Auth Ref
        firebaseAuth = FirebaseAuth.getInstance();

        buttonCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(editTextEmail.getText().toString().trim(),editTextPassword.getText().toString().trim());
            }
        });
    }

    private void Login(String email, String password) {
        //Check for empty texts
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            assert user != null;
                            final String currentUserId = user.getUid();

                            collectionReference.whereEqualTo("userId", currentUserId)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null) {

                                            }
                                            assert value != null;
                                            if (!value.isEmpty()) {
                                                //Get all QueryDocumentSnapshot
                                                for (QueryDocumentSnapshot snapshot : value) {
                                                    JournalUser journalUser = JournalUser.getInstance();
                                                    journalUser.setUserName(snapshot.getString("userName"));
                                                    journalUser.setUserId(snapshot.getString("userId"));

                                                    //Go to AddJournalActivity after successfully login
                                                    //display the list of journals after login
                                                    startActivity(new Intent(MainActivity.this, JournalListActivity.class));
                                                }
                                            }
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //If failed:
                            Toast.makeText(MainActivity.this, "Something went wrong ><", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(MainActivity.this, "Please enter email & password", Toast.LENGTH_SHORT).show();
        }
    }
}