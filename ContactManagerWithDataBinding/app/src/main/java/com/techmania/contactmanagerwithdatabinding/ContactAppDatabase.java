package com.techmania.contactmanagerwithdatabinding;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactAppDatabase extends RoomDatabase {
    public abstract ContactDAO getContactDAO();
}
