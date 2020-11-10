package com.example.api_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class QnA_page extends AppCompatActivity {
    private Button Btn_back;

    private Button Question1;

    private boolean state1 = false;

    private TextView Answer1;

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
        Answer1 = findViewById(R.id.answer1);
        Question1 = findViewById(R.id.question1);


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


    }
}