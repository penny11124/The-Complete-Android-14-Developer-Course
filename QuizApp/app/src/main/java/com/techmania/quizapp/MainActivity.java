 package com.techmania.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.techmania.quizapp.databinding.ActivityMainBinding;
import com.techmania.quizapp.model.Question;
import com.techmania.quizapp.model.QuestionList;
import com.techmania.quizapp.viewmodel.QuizViewModel;

import java.util.List;
import java.util.ResourceBundle;

 public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    QuizViewModel viewModel;
    List<Question> questionList;
    static int result = 0;
    static int totalQuestions = 0;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionList = new QuestionList();

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        //reset the scores
        result = 0;
        totalQuestions = 0;

        //get the response
        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        //display the first question
        viewModel.getQuestionListLiveData().observe(this, new Observer<QuestionList>() {
            @Override
            public void onChanged(QuestionList questions) {
                questionList = questions;
                Log.i("TAG","The first question: " + questions.get(0));
                binding.txtQuestion.setText("Question 1 :  " + questions.get(0).getQuestion());
                binding.radio1.setText(questions.get(0).getOption1());
                binding.radio2.setText(questions.get(0).getOption2());
                binding.radio3.setText(questions.get(0).getOption3());
                binding.radio4.setText(questions.get(0).getOption4());
            }
        });

        //display the next question
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedOption = binding.radioGroup.getCheckedRadioButtonId();

                // Direct the user to the results Activity
                if (binding.btnNext.getText().equals("Finish")){
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    startActivity(intent);
                    finish();
                }

                if(selectedOption != -1) {
                    RadioButton radioButton = findViewById(selectedOption);
                    //More questions to Display?
                    if((questionList.size() - i) > 0) {
                        //get the number of questions
                        totalQuestions = questionList.size();

                        //check if the chosen option is correct
                        if(radioButton.getText().toString().equals(questionList.get(i).getCorrectOption())) {
                            result++;
                            binding.txtResult.setText("Correct Answer: " + result);
                        }

                        if(i == 0){
                            i++;
                        }

                        //display the next question
                        binding.txtQuestion.setText("Question " + (i+1) + ":  " + questionList.get(i).getQuestion());
                        binding.radio1.setText(questionList.get(i).getOption1());
                        binding.radio2.setText(questionList.get(i).getOption2());
                        binding.radio3.setText(questionList.get(i).getOption3());
                        binding.radio4.setText(questionList.get(i).getOption4());

                        //check if it is the last question
                        if(i == questionList.size() - 1) {
                            binding.btnNext.setText("Finish");
                        }
                        binding.radioGroup.clearCheck();
                        i++;
                    } else  {
                        if(radioButton.getText().toString().equals(questionList.get(i-1).getCorrectOption())) {
                            result++;
                            binding.txtResult.setText("Correct Answer: " + result);
                        }else{

                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this,"You need to make a selection",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}