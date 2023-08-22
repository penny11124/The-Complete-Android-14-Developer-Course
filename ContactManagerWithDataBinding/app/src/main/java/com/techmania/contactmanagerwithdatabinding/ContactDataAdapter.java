package com.techmania.contactmanagerwithdatabinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactDataAdapter extends RecyclerView.Adapter<ContactDataAdapter.ContactViewHolder> {
    private ArrayList<Contact> contacts;

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item,parent,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactDataAdapter.ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.name.setText(contact.getName());
        holder.email.setText(contact.getEmail());
    }

    @Override
    public int getItemCount() {
        if(contacts != null) {
            return contacts.size();
        }else {
            return 0;
        }
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView name,email;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.textViewName);
            this.email = itemView.findViewById(R.id.textViewEmail);
        }
    }
}
