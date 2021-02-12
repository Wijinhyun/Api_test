package com.prodoc.api_test;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity_Ver2 extends AppCompatActivity implements View.OnClickListener{

    private ViewPager mViewPager;
    private LinearLayout hospital, pharmacy, dental, oriental;

    SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.avtivity_main_ver2);

        getpermisson();

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth = outMetrics.widthPixels / density; // dp단위

        float heig = (dpWidth * 9) / 16;
        float empty_space_heig = dpHeight - (428 + heig);

        int garo = (int) dpWidth;
        int sero = (int) heig;

        final int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpWidth, getResources().getDisplayMetrics());
        final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpHeight, getResources().getDisplayMetrics());

        final FrameLayout layout = (FrameLayout) findViewById(R.id.framelayout_nestedviewpager);

        // 뷰페이저 부분 16:9 계산
        final LinearLayout.LayoutParams mLayoutParam = (LinearLayout.LayoutParams)layout.getLayoutParams();
        mLayoutParam.height = (width * 9) / 16;

        // 버튼들 margintop 주기 위한 계산
        final int empty_space = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, empty_space_heig, getResources().getDisplayMetrics());
        View view1 = (View)findViewById(R.id.homeDivider2);
        LinearLayout.LayoutParams plControl = (LinearLayout.LayoutParams) view1.getLayoutParams();
        if(empty_space_heig > 0.0){
            plControl.topMargin = Math.round(empty_space / 3);
        }

        hospital = (LinearLayout) findViewById(R.id.main_hospital);
        pharmacy = (LinearLayout) findViewById(R.id.main_pharmacy);
        dental = (LinearLayout) findViewById(R.id.main_dental);
        oriental = (LinearLayout) findViewById(R.id.main_oriental);

        hospital.setOnClickListener(this);
        pharmacy.setOnClickListener(this);
        dental.setOnClickListener(this);
        oriental.setOnClickListener(this);

    }
    public void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new MainActivityFrag1(), "1");
        adapter.addFragment(new MainActivityFrag2(), "2");
        adapter.addFragment(new MainActivityFrag3(), "3");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_hospital:
                Intent intent = new Intent(MainActivity_Ver2.this, SubjectSelectActivity.class);
                intent.putExtra("pagenumber", 0);
                startActivity(intent);
                break;
            case R.id.main_dental:
                Intent intent1 = new Intent(MainActivity_Ver2.this, SubjectSelectActivity.class);
                intent1.putExtra("pagenumber", 1);
                startActivity(intent1);
                break;
            case R.id.main_oriental:
                Intent intent2 = new Intent(MainActivity_Ver2.this, SubjectSelectActivity.class);
                intent2.putExtra("pagenumber", 2);
                startActivity(intent2);
                break;
            case R.id.main_pharmacy:
                break;
        }
    }

    private void getpermisson() {

        // 메니패스트에 권한이 있는지 확인
        int permiCheck_loca = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        //앱권한이 없으면 권한 요청
        if(permiCheck_loca == PackageManager.PERMISSION_DENIED){
            Log.d("전화 권한 없는 상태", "");
            ActivityCompat.requestPermissions(MainActivity_Ver2.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        //권한 있다면
        else{
            Log.d("전화 권한 있는 상태", "");
        }
    }
}
