package com.example.cce;

import static android.R.color.holo_green_dark;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    Button aBtn, bBtn, cBtn, dBtn;
    TextView question, totalqstn;
    int correct = 0;
    int currentQuestionIndex = 0; //keeps current question track
    int score=0;
    int totalquestion = Questions.question.length;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //setting up buttons
        totalqstn = findViewById(R.id.totalqstn);
        question = findViewById(R.id.question);
        aBtn = (Button) findViewById(R.id.a);
        bBtn = (Button) findViewById(R.id.b);
        cBtn = (Button) findViewById(R.id.c);
        dBtn = (Button) findViewById(R.id.d);
        //registering button to listen to events
        aBtn.setOnClickListener(this);
        bBtn.setOnClickListener(this);
        cBtn.setOnClickListener(this);
        dBtn.setOnClickListener(this);

        totalqstn.setText("Total Questions :"+totalquestion);

        loadNewQuestion();


    }

    @Override
    public void onClick(View v) {
        Button clickedButton = (Button) v;

        switch (clickedButton.getId()){
            case R.id.a:
            case R.id.b:
            case R.id.c:
            case R.id.d:
                selectedAnswer = clickedButton.getText().toString();
                if(selectedAnswer.equals(Questions.correctAnswer[currentQuestionIndex])){
                    score++;
                }
                currentQuestionIndex++;
                loadNewQuestion();

                break;
        }
        }

    void loadNewQuestion(){
        
        if(currentQuestionIndex == totalquestion){
            finishQuiz();
            return;
        }
        
        question.setText(Questions.question[currentQuestionIndex]);
        aBtn.setText(Questions.choices[currentQuestionIndex][0]);
        bBtn.setText(Questions.choices[currentQuestionIndex][1]);
        cBtn.setText(Questions.choices[currentQuestionIndex][2]);
        dBtn.setText(Questions.choices[currentQuestionIndex][3]);

    }
    void finishQuiz(){
        String passStatus = "";
        if(score > totalquestion*0.60){
            passStatus = "passed";
        }else{
            passStatus = "Failed";
        }
        new AlertDialog.Builder(QuizActivity.this)
                .setTitle(passStatus)
                .setMessage("Score is "+ score+ " out of"+ totalquestion)
                .setPositiveButton("Restart",(dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }
    void restartQuiz(){
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}