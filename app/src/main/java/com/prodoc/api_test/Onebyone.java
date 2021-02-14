package com.prodoc.api_test;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Onebyone extends AppCompatActivity {

    private LinearLayout Popup_ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onebyone);

        Popup_ok = findViewById(R.id.popup_ok);
        Popup_ok.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

    }
}
