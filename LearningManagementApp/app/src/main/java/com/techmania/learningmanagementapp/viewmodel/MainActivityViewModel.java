package com.techmania.learningmanagementapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.techmania.learningmanagementapp.model.Category;
import com.techmania.learningmanagementapp.model.Course;
import com.techmania.learningmanagementapp.model.CourseShopRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    //Repository
    private CourseShopRepository repository;

    //Live Data
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Course>> allCourses;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseShopRepository(application);
    }

    public LiveData<List<Category>> getAllCategories() {
        allCategories = repository.getCategories();
        return allCategories;
    }

    public LiveData<List<Course>> getAllCourses(int categoryId) {
        allCourses = repository.getCourses(categoryId);
        return allCourses;
    }


    public void addNewCourse(Course course) {
        repository.insertCourse(course);
    }
    public void updateCourse(Course course) {
        repository.updateCourse(course);
    }
    public void deleteCourse(Course course) {
        repository.deleteCourse(course);
    }
}
