package com.techmania.firestoreapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.nio.file.FileVisitOption;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName,editTextEmail;
    private Button buttonSave, buttonRead, buttonUpdate, buttonDelete;
    private TextView textViewData;

    //Firebase Firestore
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private DocumentReference friendRef = firestore.collection("Users").document("Friends");
    private CollectionReference collectionReference = firestore.collection("Users");

    //KEYs
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextUserName);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SaveDataToFireStore();

                //save data to new document
                SaveDataToNewDocument();
            }
        });

        textViewData = findViewById(R.id.textViewData);
        buttonRead = findViewById(R.id.buttonRead);
        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ReadData();

                //get all documents in collection
                GetAllDocumentsInCollection();
            }
        });

        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData();
            }
        });

        buttonDelete = findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DeleteData();
                DeleteAll();
            }
        });

    }

    private void GetAllDocumentsInCollection() {
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data = "";
                //Looping through all the documents in collection
                for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
//                    Log.v("TAG",snapshot.getString(KEY_NAME));
//                    Log.v("TAG",snapshot.getString(KEY_EMAIL));

                    //Transforming snapshot into objects
                    //Each document is now an object of type friend
                    Friend friend = snapshot.toObject(Friend.class);

                    //Adding object data to string
                    data += "Name : " + friend.getName() + "  Email : " + friend.getEmail() + "\n";
                }
                //Setting the textView to the retrieved data
                textViewData.setText(data);
            }
        });
    }

    private void SaveDataToNewDocument() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        Friend friend = new Friend(name,email);

        collectionReference.add(friend);
    }

    private void DeleteData() {
        //delete value according to name or email
        friendRef.update(KEY_NAME, FieldValue.delete());
        friendRef.update(KEY_EMAIL,FieldValue.delete());
    }

    private void DeleteAll() {
        //delete all data from document
        friendRef.delete();
    }
    private void DeletePair() {
        //delete data according to pair key(name)
        Map<String, Object> data = new HashMap<>();

        data.put(KEY_NAME, FieldValue.delete());
        friendRef.update(KEY_NAME,FieldValue.delete());
    }

    private void UpdateData() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        Map<String,Object> data = new HashMap<>();
        data.put(KEY_NAME,name);
        data.put(KEY_EMAIL,email);

        friendRef.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Update Successfully!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ReadData() {
        friendRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //Display retrieved data into textView
                if(documentSnapshot.exists()) {
                    Friend friend = documentSnapshot.toObject(Friend.class);

//                    String fname = value.getString(KEY_NAME);
//                    String femail = value.getString(KEY_EMAIL);
                    textViewData.setText("User : " + friend.getName() + "\nEmail : " + friend.getEmail());
                }
            }
        });
    }

    private void SaveDataToFireStore() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        Friend f1 = new Friend();
        f1.setName(name);
        f1.setEmail(email);
        //Save custom object(Java Object: Friend.class)


        //Save data as key-value pairs (MAP data structure)
//        Map<String,Object> data = new HashMap<>();
//        data.put(KEY_NAME,name);
//        data.put(KEY_EMAIL,email);

        //Save in Collections
        firestore.collection("Users")
                .document("Friends")
                .set(f1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"Successfully created!",Toast.LENGTH_SHORT).show();
                    }
                })
                //Can add other listener
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed!",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        GetAllDocumentsInCollection();
        //Listening all the time during the app lifecycle
//        friendRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                if(error != null) {
//                    Toast.makeText(MainActivity.this,"Error Found!",Toast.LENGTH_SHORT).show();
//                }
//                if(value != null && value.exists()) {
//                    //Get data(custom object)
//                    Friend friend = value.toObject(Friend.class);
//
////                    String fname = value.getString(KEY_NAME);
////                    String femail = value.getString(KEY_EMAIL);
//                    textViewData.setText("User : " + friend.getName() + "\nEmail : " + friend.getEmail());
//                }
//            }
//        });
    }

    //Build CURD App
    //1 - Saving Data on Firestore (Creating Data)
    //2 - Reading Data from Firestore (Retrieving Data)
    //2.1 - Listening to Snapshot change
    //3 - Updating Simple Data
    //4 - Deleting Data (K-V pairs)

    //5 - Saving Custom Data (Java POJO) into Firestore

    //6 - Creating multiple documents
    //7 - Retrieving multiple documents into LOG

    //8 - Retrieving multiple documents into objects(Friends)

}