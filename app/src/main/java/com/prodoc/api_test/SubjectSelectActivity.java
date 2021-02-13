package com.prodoc.api_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class SubjectSelectActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    Fragment fragment0, fragment1, fragment2, fragment3;
    private int posi;
    private String city_name;
    private String gu_name;
    private String search;
    private String Imfrom;
    private ImageView select_subject_Back;

    private String MedicalsubCd;

    SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);

        mViewPager = (ViewPager) findViewById(R.id.container1);
        setupViewPager(mViewPager);


        Intent intent = getIntent();
        posi = intent.getIntExtra("pagenumber",0);
        city_name = intent.getStringExtra("city_name");
        gu_name = intent.getStringExtra("gu_name");
        search = intent.getStringExtra("search");
        Imfrom = intent.getStringExtra("Imfrom");

        select_subject_Back = findViewById(R.id.select_subject_back);
        select_subject_Back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);

        fragment0 = new TabFragment0();
        fragment1 = new TabFragment1();
        fragment2 = new TabFragment2();
        fragment3 = new TabFragment3();

        if(posi == 0){
            TabLayout.Tab tab = tabs.getTabAt(0);
            tab.select();
            //getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment0).commit();
        }else if(posi == 1){
            TabLayout.Tab tab = tabs.getTabAt(1);
            tab.select();
            //getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment1).commit();
        }else if(posi == 2){
            TabLayout.Tab tab = tabs.getTabAt(2);
            tab.select();
            //getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment2).commit();
        }else if(posi == 3){
            TabLayout.Tab tab = tabs.getTabAt(3);
            tab.select();
            //getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment2).commit();
        }


//        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                int position = tab.getPosition();
//
//                Fragment selected = null;
//                if(position == 0){
//
//                    selected = fragment0;
//
//                }else if (position == 1){
//
//                    selected = fragment1;
//
//                }else if (position == 2){
//
//                    selected = fragment2;
//
//                }
//
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    public void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new TabFragment0(), "의원");
        adapter.addFragment(new TabFragment1(), "치과의원");
        adapter.addFragment(new TabFragment2(), "한의원");
        adapter.addFragment(new TabFragment3(), "약국");
        viewPager.setAdapter(adapter);
    }


}
