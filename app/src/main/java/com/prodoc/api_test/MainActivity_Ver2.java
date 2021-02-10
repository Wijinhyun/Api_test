package com.prodoc.api_test;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity_Ver2 extends AppCompatActivity {

    private ViewPager mViewPager;
    SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.avtivity_main_ver2);

//        Intent intent = new Intent(this, LoadingActivity.class);
//        startActivity(intent);
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);


//        Button btnStartLinkB = (Button)findViewById(R.id.btnStartLinkB);
//
//        btnStartLinkB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),login.class);
//                startActivity(intent);
//            }
//        });

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth = outMetrics.widthPixels / density; // dp단위

        float heig = (dpWidth * 9) / 16;

        int garo = (int) dpWidth;
        int sero = (int) heig;

        final int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpWidth, getResources().getDisplayMetrics());
        final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpHeight, getResources().getDisplayMetrics());

//
        final FrameLayout layout = (FrameLayout) findViewById(R.id.framelayout_nestedviewpager);
        //LinearLayout layout = (LinearLayout) findViewById(R.id.linearnestframe);
//
        final LinearLayout.LayoutParams mLayoutParam = (LinearLayout.LayoutParams)layout.getLayoutParams();
        mLayoutParam.height = (width * 9) / 16;
        //layout.setLayoutParams(new LinearLayout.LayoutParams(garo,sero));
        //layout.setGravity(Gravity.CENTER);


    }
    public void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new MainActivityFrag1(), "1");
        adapter.addFragment(new MainActivityFrag2(), "2");
        adapter.addFragment(new MainActivityFrag3(), "3");
        viewPager.setAdapter(adapter);
    }
}
