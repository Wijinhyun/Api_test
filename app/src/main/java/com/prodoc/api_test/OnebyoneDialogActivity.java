package com.prodoc.api_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class OnebyoneDialogActivity extends Activity implements View.OnClickListener {

    private LinearLayout Popup_ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //WindowManager.LayoutParams  layoutParams = new WindowManager.LayoutParams();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setGravity(Gravity.CENTER);

        setContentView(R.layout.activity_onebyone);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 1.0); //Display 사이즈의 70%
        //int height = (int) (display.getHeight() * 0.2);  //Display 사이즈의 90%
        getWindow().getAttributes().width = width;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Popup_ok = findViewById(R.id.popup_ok);
        Popup_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.popup_ok:
                onBackPressed();
                //Toast.makeText(ListDialogActivity.this, "세부정보로 넘어가게 구현", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
