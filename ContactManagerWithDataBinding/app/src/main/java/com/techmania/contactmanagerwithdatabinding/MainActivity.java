package com.techmania.contactmanagerwithdatabinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Delete;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.techmania.contactmanagerwithdatabinding.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ContactAppDatabase contactAppDatabase;
    private ArrayList<Contact> contacts;
    private ContactDataAdapter contactDataAdapter;

    //Binding
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandlers clickHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Data Binding
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        clickHandlers = new MainActivityClickHandlers(this);
        activityMainBinding.setClickHandler(clickHandlers);

        //RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Adapter
        contactDataAdapter = new ContactDataAdapter();
        recyclerView.setAdapter(contactDataAdapter);

        //Database
        contactAppDatabase = Room.databaseBuilder(getApplicationContext(),ContactAppDatabase.class,"ContactDB").build();

        //Add Data
        LoadData();

        //Handling Swiping
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback() {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Contact contact = contacts.get(viewHolder.getAdapterPosition());
                DeleteContact(contact);
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode ==RESULT_OK) {
            String name = data.getDataString("NAME");
            String email = data.getDataString("EMAIL");

            Contact contact = new Contact(name,email);

            AddNewContact(contact);
        }
    }

    private void DeleteContact(Contact contact) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //On Background
                contactAppDatabase.getContactDAO().delete(contact);

                //On Post Execution
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        LoadData();
                        contactDataAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void LoadData() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //On Background
                contacts = (ArrayList<Contact>) contactAppDatabase.getContactDAO().getAllContacts();

                //On Post Execution
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        contactDataAdapter.setContacts(contacts);
                        contactDataAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void AddNewContact(Contact contact) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //On Background
                contactAppDatabase.getContactDAO().insert(contact);

                //On Post Execution
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        LoadData();
                        contactDataAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public class MainActivityClickHandlers {
        Context context;

        public MainActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onFABClicked(View view) {
            Intent intent = new Intent(MainActivity.this, AddNewContactActivity.class);
            startActivityForResult(intent,1);
        }
    }
}