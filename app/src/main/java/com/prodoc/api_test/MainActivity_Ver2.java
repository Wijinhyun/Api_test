package com.prodoc.api_test;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aqoong.lib.slidephotoviewer.MaxSizeException;
import com.aqoong.lib.slidephotoviewer.SlidePhotoViewer;

public class MainActivity_Ver2 extends AppCompatActivity {

    SlidePhotoViewer mSlideViewr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.avtivity_main_ver2);

        mSlideViewr = findViewById(R.id.slideViewer);
        mSlideViewr.setSidePreview(true);
        try {
            mSlideViewr.addResource(R.drawable.ic_test, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "TEST", Toast.LENGTH_LONG).show();
                }
            });
            mSlideViewr.addResource(R.drawable.icon_background2, null);
            mSlideViewr.addResource(R.drawable.icon_background2, null);
            mSlideViewr.addResource(R.drawable.icon_background2, null);
        } catch (MaxSizeException e) {
            e.printStackTrace();
        }

    }
}
