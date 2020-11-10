package com.example.api_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api_test.R;
import com.example.api_test.Region_listview;
import com.github.edsergeev.TextFloatingActionButton;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button Btn_region, Btn_search;
    private ImageButton Btn_Internal_Medicine, Btn_Dermatology, Btn_Ophthalmology, Btn_Cosmetic_Surgery, Btn_Orthopedics, Btn_Otolaryngology, Btn_Surgical, Btn_Urology, Btn_Obstetrics_Gynecology;

    private String city_name;
    private String gu_name;
    private String search;

    private String MedicalsubCd;
    private TextFloatingActionButton Fb_qna;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        Intent intent = getIntent();
        city_name = intent.getStringExtra("city_name");
        gu_name = intent.getStringExtra("gu_name");
        search = intent.getStringExtra("search");

        if(search != null){
            Btn_search.setText(search);
        }
        if (city_name != null && gu_name != null) {
            Btn_region.setText(city_name + " - " + gu_name);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_region:
                Intent intent = new Intent(MainActivity.this, Region_listview.class);
                intent.putExtra("Imfrom", "mainactivity"); /*송신*/
                intent.putExtra("search", search);
                startActivity(intent);
                break;
            case R.id.btn_Internal_Medicine:
                MedicalsubCd = "01";
                Intent intent01 = new Intent(MainActivity.this, Recyclerview_HospitalList.class);
                intent01.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent01.putExtra("city_name", city_name);
                intent01.putExtra("gu_name", gu_name);
                intent01.putExtra("search", search);
                startActivity(intent01);
                break;
            case R.id.btn_Dermatology:
                MedicalsubCd = "14";
                Intent intent14 = new Intent(MainActivity.this, Recyclerview_HospitalList.class);
                intent14.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent14.putExtra("city_name", city_name);
                intent14.putExtra("gu_name", gu_name);
                intent14.putExtra("search", search);
                startActivity(intent14);
                break;
            case R.id.btn_Ophthalmology:
                MedicalsubCd = "12";
                Intent intent12 = new Intent(MainActivity.this, Recyclerview_HospitalList.class);
                intent12.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent12.putExtra("city_name", city_name);
                intent12.putExtra("gu_name", gu_name);
                intent12.putExtra("search", search);
                startActivity(intent12);
                break;
            case R.id.btn_Cosmetic_Surgery:
                MedicalsubCd = "08";
                Intent intent08 = new Intent(MainActivity.this, Recyclerview_HospitalList.class);
                intent08.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent08.putExtra("city_name", city_name);
                intent08.putExtra("gu_name", gu_name);
                intent08.putExtra("search", search);
                startActivity(intent08);
                break;
            case R.id.btn_Orthopedics:
                MedicalsubCd = "05";
                Intent intent05 = new Intent(MainActivity.this, Recyclerview_HospitalList.class);
                intent05.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent05.putExtra("city_name", city_name);
                intent05.putExtra("gu_name", gu_name);
                intent05.putExtra("search", search);
                startActivity(intent05);
                break;
            case R.id.btn_Otolaryngology:
                MedicalsubCd = "13";
                Intent intent49 = new Intent(MainActivity.this, Recyclerview_HospitalList.class);
                intent49.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent49.putExtra("city_name", city_name);
                intent49.putExtra("gu_name", gu_name);
                intent49.putExtra("search", search);
                startActivity(intent49);
                break;
            case R.id.btn_Surgical:
                MedicalsubCd = "04";
                Intent intent04 = new Intent(MainActivity.this, Recyclerview_HospitalList.class);
                intent04.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent04.putExtra("city_name", city_name);
                intent04.putExtra("gu_name", gu_name);
                intent04.putExtra("search", search);
                startActivity(intent04);
                break;
            case R.id.btn_Urology:
                MedicalsubCd = "15";
                Intent intent15 = new Intent(MainActivity.this, Recyclerview_HospitalList.class);
                intent15.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent15.putExtra("city_name", city_name);
                intent15.putExtra("gu_name", gu_name);
                intent15.putExtra("search", search);
                startActivity(intent15);
                break;
            case R.id.btn_Obstetrics_Gynecology:
                MedicalsubCd = "10";
                Intent intent10 = new Intent(MainActivity.this, Recyclerview_HospitalList.class);
                intent10.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent10.putExtra("city_name", city_name);
                intent10.putExtra("gu_name", gu_name);
                intent10.putExtra("search", search);
                startActivity(intent10);
                break;
            case R.id.fb_qna:
                Intent qna = new Intent(MainActivity.this, QnA_page.class);
                /*qna.putExtra("city_name", city_name);
                qna.putExtra("gu_name", gu_name);
                qna.putExtra("search", search);*/
                startActivity(qna);
                break;
            case R.id.btn_search:
                Intent search = new Intent(MainActivity.this, Search_btn.class);
                search.putExtra("city_name", city_name);
                search.putExtra("gu_name", gu_name);
                startActivity(search);
                break;
            default:
                break;

        }

    }

    private void init() {
        Btn_region = (Button) findViewById(R.id.btn_region);
        Btn_Internal_Medicine = (ImageButton) findViewById(R.id.btn_Internal_Medicine);
        Btn_Dermatology = (ImageButton) findViewById(R.id.btn_Dermatology);
        Btn_Ophthalmology = (ImageButton) findViewById(R.id.btn_Ophthalmology);
        Btn_Cosmetic_Surgery = (ImageButton) findViewById(R.id.btn_Cosmetic_Surgery);
        Btn_Orthopedics = (ImageButton) findViewById(R.id.btn_Orthopedics);
        Btn_Otolaryngology = (ImageButton) findViewById(R.id.btn_Otolaryngology);
        Btn_Surgical = (ImageButton) findViewById(R.id.btn_Surgical);
        Btn_Urology = (ImageButton) findViewById(R.id.btn_Urology);
        Btn_Obstetrics_Gynecology = (ImageButton) findViewById(R.id.btn_Obstetrics_Gynecology);
        Btn_search = (Button) findViewById(R.id.btn_search);
        Fb_qna = findViewById(R.id.fb_qna);


        Btn_region.setOnClickListener(this);
        Btn_Internal_Medicine.setOnClickListener(this);
        Btn_Dermatology.setOnClickListener(this);
        Btn_Ophthalmology.setOnClickListener(this);
        Btn_Cosmetic_Surgery.setOnClickListener(this);
        Btn_Orthopedics.setOnClickListener(this);
        Btn_Otolaryngology.setOnClickListener(this);
        Btn_Surgical.setOnClickListener(this);
        Btn_Urology.setOnClickListener(this);
        Btn_Obstetrics_Gynecology.setOnClickListener(this);
        Btn_search.setOnClickListener(this);
        Fb_qna.setOnClickListener(this);
    }

}

