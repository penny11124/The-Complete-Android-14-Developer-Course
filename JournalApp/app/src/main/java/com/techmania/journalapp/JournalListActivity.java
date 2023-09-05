package com.techmania.journalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.techmania.journalapp.model.Journal;
import com.techmania.journalapp.ui.JournalRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import util.JournalUser;

public class JournalListActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private StorageReference storageReference;
    private List<Journal> journalList;

    private RecyclerView recyclerView;
    private JournalRecyclerAdapter journalRecyclerAdapter;

    private CollectionReference collectionReference = db.collection("Journal");
    private TextView noPostEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jounal_list);

        //Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        //Widgets
        noPostEntry = findViewById(R.id.listNoPosts);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Post ArrayList
        journalList = new ArrayList<>();
    }

    //Add the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.actionAdd) {
            //Go to AddJournalActivity
            if(user != null && firebaseAuth != null) {
                startActivity(new Intent(JournalListActivity.this,AddJournalActivity.class));
            }
        } else if(item.getItemId() == R.id.actionSignOut) {
            //Sign out the user
            if(user != null && firebaseAuth != null) {
                firebaseAuth.signOut();
                startActivity(new Intent(JournalListActivity.this,MainActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //Get all posts
    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.whereEqualTo("userId", JournalUser.getInstance().getUserId())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            for(QueryDocumentSnapshot journals : queryDocumentSnapshots) {
                                Journal journal = journals.toObject(Journal.class);
                                journalList.add(journal);
                            }

                            //RecyclerView
                            journalRecyclerAdapter = new JournalRecyclerAdapter(JournalListActivity.this,journalList);
                            recyclerView.setAdapter(journalRecyclerAdapter);
                            journalRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            noPostEntry.setVisibility(View.VISIBLE);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //any failure
                        Toast.makeText(JournalListActivity.this,"Oops! Something went wrong!",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}