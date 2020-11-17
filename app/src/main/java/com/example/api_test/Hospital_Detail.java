package com.example.api_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Hospital_Detail extends AppCompatActivity {

    private HospitalItem hospitalItem;

    private TextView Tv_title, Tv_adr, Tv_tel;
    private Button btn_listcall;
    private String callnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital__detail);

        hospitalItem = (HospitalItem) getIntent().getSerializableExtra("array");

        init();

        btn_listcall.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(callnum)));
            }
        });

    }

    private void init() {
        Tv_title = findViewById(R.id.tv_title);
        Tv_tel = findViewById(R.id.tv_tel);
        Tv_adr = findViewById(R.id.tv_adr);
        btn_listcall=findViewById(R.id.list_call);

        Tv_title.setText(hospitalItem.getYadmNm());
        Tv_adr.setText(hospitalItem.getAddr());
        Tv_tel.setText(hospitalItem.getTelno());
        callnum="tel: "+hospitalItem.getTelno();
    }
}