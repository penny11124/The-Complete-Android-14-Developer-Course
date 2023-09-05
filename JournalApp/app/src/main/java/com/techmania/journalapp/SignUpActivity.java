package com.techmania.journalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.StringPrepParseException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import util.JournalUser;

public class SignUpActivity extends AppCompatActivity {

    EditText userNameCreate, emailCreate, passwordCreate;
    Button buttonCreate;

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
        setContentView(R.layout.activity_sign_up);

        //Fire Auth
        firebaseAuth = FirebaseAuth.getInstance();
        //Fire Auth require Google Account on the

        emailCreate = findViewById(R.id.emailCreate);
        userNameCreate = findViewById(R.id.userNameCreate);
        passwordCreate = findViewById(R.id.passwordCreate);
        buttonCreate = findViewById(R.id.buttonSignUp);

        //Authentication
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();

                if(currentUser != null) {
                    //User already logged in
                } else {
                    //No user yet
                }
            }
        };
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(emailCreate.getText().toString())
                        && !TextUtils.isEmpty(passwordCreate.getText().toString())) {
                    String userName = userNameCreate.getText().toString().trim();
                    String email = emailCreate.getText().toString().trim();
                    String password = passwordCreate.getText().toString().trim();
                    if(password.length() < 6) {
                        Toast.makeText(SignUpActivity.this,"Password should be at least 6 characters.",Toast.LENGTH_SHORT).show();
                    } else {
                        CreateUserAccount(email,password,userName);
                    }
                } else {
                    Toast.makeText(SignUpActivity.this,"Empty Field!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    private void CreateUserAccount(String email, String password,final String userName) {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //we take user to next activity (AddJournalActivity)
                            currentUser = firebaseAuth.getCurrentUser();
                            assert currentUser != null;
                            final String currentUserId = currentUser.getUid();

                            //Create a userMap so we can create a user in the User Collection in Firebase
                            Map<String, String> userObj = new HashMap<>();
                            userObj.put("userId",currentUserId);
                            userObj.put("userName",userName);

                            //Adding users to Firebase
                            collectionReference.add(userObj)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            documentReference.get()
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if(Objects.requireNonNull(task.getResult()).exists()) {
                                                                String name = task.getResult().getString("userName");

                                                                //If the user is registered successfully
                                                                //then move to the AddJournalActivity

                                                                //Get use of Global Journal App user
                                                                JournalUser journalUser = JournalUser.getInstance();
                                                                journalUser.setUserId(currentUserId);
                                                                journalUser.setUserName(name);

                                                                Intent intent = new Intent(SignUpActivity.this,AddJournalActivity.class);
                                                                intent.putExtra("userName",name);
                                                                intent.putExtra("userId",currentUserId);
                                                                startActivity(intent);
                                                            } else {

                                                            }
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            //display failed message
                                                            Toast.makeText(SignUpActivity.this,"Something went wrong ><", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    });
                        }
                    }
                });
    }
}