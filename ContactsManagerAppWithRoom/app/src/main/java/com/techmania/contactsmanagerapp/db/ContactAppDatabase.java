package com.techmania.contactsmanagerapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.techmania.contactsmanagerapp.db.entity.Contact;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactAppDatabase extends RoomDatabase {

    //Linking the DAO with our Database
    public abstract ContactDAO getContactDAO();
}
