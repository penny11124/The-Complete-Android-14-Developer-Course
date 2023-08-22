package com.techmania.contactmanagerwithdatabinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.techmania.contactmanagerwithdatabinding.databinding.ActivityAddNewContactBinding;

public class AddNewContactActivity extends AppCompatActivity {

    private ActivityAddNewContactBinding activityAddNewContactBinding;
    Contact contact;
    private AddNewContactClickHandlers addNewContactClickHandlers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);

        contact = new Contact();
        activityAddNewContactBinding = DataBindingUtil .setContentView(this,R.layout.activity_add_new_contact);
        activityAddNewContactBinding.setContact(contact);

        addNewContactClickHandlers = new AddNewContactClickHandlers(this);
        activityAddNewContactBinding.setClickHandler(addNewContactClickHandlers);
    }

    public class AddNewContactClickHandlers {
        Context context;

        public AddNewContactClickHandlers(Context context) {
            this.context = context;
        }

        public void onSubmitClicked(View view) {
            if(contact.getName() == null) {
                Toast.makeText(getApplicationContext(),"Fields cannot be empty.",Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra("NAME",contact.getName());
                intent.putExtra("EMAIL",contact.getEmail());

                setResult(RESULT_OK,intent);
                finish();
            }
        }
    }
}