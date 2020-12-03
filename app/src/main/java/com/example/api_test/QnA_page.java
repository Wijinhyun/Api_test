package com.example.api_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class QnA_page extends AppCompatActivity {
    private Button Btn_back;

    private Button Question1, Question2, Question3, Question4, Question5, Question6;

    private boolean state1, state2, state3, state4, state5, state6;

    private TextView Answer1, Answer2, Answer3, Answer4, Answer5, Answer6;

    private Drawable faq_updown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qn_a_page);


        Btn_back = findViewById(R.id.btn_back);
        Btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //faq_updown = findViewById(R.drawable.faq_up);
        Answer1 = findViewById(R.id.answer1);
        Question1 = findViewById(R.id.question1);

        Answer2 = findViewById(R.id.answer2);
        Question2 = findViewById(R.id.question2);

        Answer3 = findViewById(R.id.answer3);
        Question3 = findViewById(R.id.question3);

        Answer4 = findViewById(R.id.answer4);
        Question4 = findViewById(R.id.question4);

        Answer5 = findViewById(R.id.answer5);
        Question5 = findViewById(R.id.question5);

        Answer6 = findViewById(R.id.answer6);
        Question6 = findViewById(R.id.question6);

        Question1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state1 == false){
                    Answer1.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    Answer1.requestLayout();
                    state1 = true;
                }else if(state1 == true){
                    Answer1.getLayoutParams().height = 0;
                    Answer1.requestLayout();
                    state1 = false;
                }
            }
        });

        Question2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state2 == false){
                    Answer2.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    Answer2.requestLayout();
                    state2 = true;
                }else if(state2 == true){
                    Answer2.getLayoutParams().height = 0;
                    Answer2.requestLayout();
                    state2 = false;
                }
            }
        });


        Question3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state3 == false){
                    Answer3.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    Answer3.requestLayout();
                    state3 = true;
                }else if(state3 == true){
                    Answer3.getLayoutParams().height = 0;
                    Answer3.requestLayout();
                    state3 = false;
                }
            }
        });


        Question4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state4 == false){
                    Answer4.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    Answer4.requestLayout();
                    state4 = true;
                }else if(state4 == true){
                    Answer4.getLayoutParams().height = 0;
                    Answer4.requestLayout();
                    state4 = false;
                }
            }
        });


        Question5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state5 == false){
                    Answer5.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    Answer5.requestLayout();
                    state5 = true;
                }else if(state5 == true){
                    Answer5.getLayoutParams().height = 0;
                    Answer5.requestLayout();
                    state5 = false;
                }
            }
        });


        Question6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state6 == false){
                    Answer6.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    Answer6.requestLayout();
                    state6 = true;
                }else if(state6 == true){
                    Answer6.getLayoutParams().height = 0;
                    Answer6.requestLayout();
                    state6 = false;
                }
            }
        });


    }
}