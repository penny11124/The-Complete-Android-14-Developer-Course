package com.techmania.contactmanagerwithdatabinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.techmania.contactmanagerwithdatabinding.databinding.ContactListItemBinding;

import java.util.ArrayList;

public class ContactDataAdapter extends RecyclerView.Adapter<ContactDataAdapter.ContactViewHolder> {
    private ArrayList<Contact> contacts;

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContactListItemBinding contactListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.contact_list_item,parent,false);

        return new ContactViewHolder(contactListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactDataAdapter.ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.contactListItemBinding.setContact(contact);
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

        private ContactListItemBinding contactListItemBinding;
        public ContactViewHolder(@NonNull ContactListItemBinding contactListItemBinding) {
            super(contactListItemBinding.getRoot());

            this.contactListItemBinding = contactListItemBinding;
        }
    }
}
