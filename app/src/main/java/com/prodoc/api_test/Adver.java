package com.prodoc.api_test;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Adver extends AppCompatActivity {

    private LinearLayout Popup_ok11;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        Popup_ok11 = findViewById(R.id.popup_ok11);
        Popup_ok11.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

    }
}
