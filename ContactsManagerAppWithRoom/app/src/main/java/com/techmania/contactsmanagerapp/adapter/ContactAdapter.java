package com.techmania.contactsmanagerapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techmania.contactsmanagerapp.MainActivity;
import com.techmania.contactsmanagerapp.R;
import com.techmania.contactsmanagerapp.db.entity.Contact;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    //Variables
    private Context context;
    private ArrayList<Contact> contactArrayList;
    private MainActivity mainActivity;

    //View Holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.email = itemView.findViewById(R.id.email);
        }
    }

    public ContactAdapter(Context context,ArrayList<Contact> contacts,MainActivity mainActivity){
        this.context = context;
        this.contactArrayList = contacts;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Contact contact = contactArrayList.get(position);
        holder.name.setText(contact.getName());
        holder.email.setText(contact.getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.addAndEditContacts(true,contact,position);
            }
        });
    }

    public int getItemCount() {
        return contactArrayList.size();
    }
}
