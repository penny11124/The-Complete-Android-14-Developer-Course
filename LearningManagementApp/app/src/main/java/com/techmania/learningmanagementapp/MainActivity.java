package com.techmania.learningmanagementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.techmania.learningmanagementapp.databinding.ActivityMainBinding;
import com.techmania.learningmanagementapp.model.Category;
import com.techmania.learningmanagementapp.model.Course;
import com.techmania.learningmanagementapp.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;
    private ArrayList<Category> categoryArrayList;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandlers clickHandler;
    private Category selectCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        clickHandler = new MainActivityClickHandlers();
        activityMainBinding.setClickHandler(clickHandler);

        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryArrayList = (ArrayList<Category>) categories;
                for(Category c : categories) {
                    Log.i("TAG",c.getCategoryName());
                }
                
                showOnSpinner();
            }
        });
        mainActivityViewModel.getAllCourses(1).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                for(Course c : courses) {
                    Log.v("TAG",c.getCourseName());
                }
            }
        });
    }

    private void showOnSpinner() {
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(
                this,R.layout.spinner_item,categoryArrayList);
        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);
    }

    public class MainActivityClickHandlers {
        public void onFABClicked(View view) {
            Toast.makeText(getApplicationContext(),"FAB CLICKED!",Toast.LENGTH_SHORT).show();
        }

        public void onSelectItem(AdapterView<?> parent,View view,int pos,long id) {
            selectCategory = (Category) parent.getItemAtPosition(pos);
            String message = "id is: " + selectCategory.getId() + "\n name is " + selectCategory.getCategoryName();
            Toast.makeText(parent.getContext()," " + message,Toast.LENGTH_SHORT).show();
        }
    }
}