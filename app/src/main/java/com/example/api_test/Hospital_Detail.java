package com.example.api_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Hospital_Detail extends AppCompatActivity {

    private HospitalItem hospitalItem;

    private TextView Tv_title, Tv_adr, Tv_tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital__detail);

        hospitalItem = (HospitalItem) getIntent().getSerializableExtra("array");

        init();

    }

    private void init() {
        Tv_title = findViewById(R.id.tv_title);
        Tv_tel = findViewById(R.id.tv_tel);
        Tv_adr = findViewById(R.id.tv_adr);

        Tv_title.setText(hospitalItem.getYadmNm());
        Tv_adr.setText(hospitalItem.getAddr());
        Tv_tel.setText(hospitalItem.getTelno());
    }
}