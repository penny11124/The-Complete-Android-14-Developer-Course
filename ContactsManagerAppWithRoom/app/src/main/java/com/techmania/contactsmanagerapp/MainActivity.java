package com.techmania.contactsmanagerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.techmania.contactsmanagerapp.adapter.ContactAdapter;
import com.techmania.contactsmanagerapp.db.ContactAppDatabase;
import com.techmania.contactsmanagerapp.db.entity.Contact;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    //Room database project

    //variables
    private ContactAdapter contactAdapter;
    private ArrayList<Contact> contactArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactAppDatabase contactAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Favorite Contacts");

        //RecyclerView
        recyclerView = findViewById(R.id.recycler_view_contacts);

        //Callbacks
        RoomDatabase.Callback callback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                //These are 4 contacts already created in the app when installed (Build-in contacts)
//                CreateContact("Bill Gate","billgate@microsoft.com");
//                CreateContact("Nicola Tesla","nicolatesla@tesla.com");
//                CreateContact("Mark Zuker","markzuker@fackbook.com");
//                CreateContact("Satushi Namk","satushi@bitcoin.com");
                Log.i("TAG","Database has been Created.");
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
                Log.i("TAG","Database has been Opened.");
            }
        };

        //Database
        contactAppDatabase = Room.databaseBuilder(
                getApplicationContext(),
                ContactAppDatabase.class,
                "ContactDB").addCallback(callback).build();

        //Displaying All Contact List
        DisplayAllContactInBackground();

        contactAdapter = new ContactAdapter(this,contactArrayList,MainActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAndEditContacts(false,null,-1);
            }
        });
    }

    public void addAndEditContacts(final boolean isUpdated, final Contact contact, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.layout_add_contact,null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(view);

        TextView contactTitle = view.findViewById(R.id.new_contact_title);
        final EditText newContact = view.findViewById(R.id.name);
        final EditText contactEmail = view.findViewById(R.id.email);

        contactTitle.setText(!isUpdated ? "Add New Contact" : "Edit Contact");

        if(isUpdated && contact!=null) {
            newContact.setText(contact.getName());
            contactEmail.setText(contact.getEmail());
        }

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(isUpdated ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(isUpdated) {
                            DeletedContact(contact,position);
                        } else {
                            dialog.cancel();
                        }
                    }
                });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(newContact.getText().toString())){
                    Toast.makeText(MainActivity.this,"Please Enter a Name",Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }
                if(isUpdated && contact!=null) {
                    UpdateContact(newContact.getText().toString(),contactEmail.getText().toString(),position);
                } else {
                    CreateContact(newContact.getText().toString(),contactEmail.getText().toString());
                }
            }
        });
    }

    private void DeletedContact(Contact contact,int position) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                contactAppDatabase.getContactDAO().deleteContact(contact);

                // Update UI on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contactArrayList.remove(position);
                        contactAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void UpdateContact(String name,String email,int position) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Contact contact = contactArrayList.get(position);
                contact.setName(name);
                contact.setEmail(email);

                contactAppDatabase.getContactDAO().updateContact(contact);

                // Update UI on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        contactArrayList.set(position, contact);
                        contactAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void CreateContact(String name, String email) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                long id = contactAppDatabase.getContactDAO().addContact(new Contact(name,email,0));
                Contact contact = contactAppDatabase.getContactDAO().getContact(id);

                if (contact != null) {
                    // Use the main thread handler to post UI updates
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            contactArrayList.add(0, contact);
                            contactAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    private void DisplayAllContactInBackground() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Background Work
                contactArrayList.addAll(contactAppDatabase.getContactDAO().getContacts());

                //Execute after the background work had finished
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        contactAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    //Menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}