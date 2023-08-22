package com.techmania.contactmanagerwithdatabinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_table")
public class Contact extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    int countId;
    String name, email;

    @Ignore
    public Contact() {
    }

    public Contact(String name, String email, int countId) {
        this.name = name;
        this.email = email;
        this.countId = countId;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public int getCountId() {
        return countId;
    }

    public void setCountId(int countId) {
        this.countId = countId;
    }
}
