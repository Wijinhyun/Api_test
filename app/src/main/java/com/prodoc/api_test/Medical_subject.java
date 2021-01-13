package com.prodoc.api_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Medical_subject extends AppCompatActivity implements View.OnClickListener {
    private ImageButton Btn_Internal_Medicine, Btn_Dermatology, Btn_Ophthalmology, Btn_Cosmetic_Surgery, Btn_Orthopedics, Btn_Otolaryngology, Btn_Surgical, Btn_Urology, Btn_Obstetrics_Gynecology;

    private String city_name;
    private String gu_name;
    private String search;

    private String MedicalsubCd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_subject);

        Intent intent = getIntent();
        city_name = intent.getStringExtra("city_name");
        gu_name = intent.getStringExtra("gu_name");
        search = intent.getStringExtra("search");

        init();

    }

    private void init() {
        Btn_Internal_Medicine = (ImageButton) findViewById(R.id.btn_Internal_Medicine);
        Btn_Dermatology = (ImageButton) findViewById(R.id.btn_Dermatology);
        Btn_Ophthalmology = (ImageButton) findViewById(R.id.btn_Ophthalmology);
        Btn_Cosmetic_Surgery = (ImageButton) findViewById(R.id.btn_Cosmetic_Surgery);
        Btn_Orthopedics = (ImageButton) findViewById(R.id.btn_Orthopedics);
        Btn_Otolaryngology = (ImageButton) findViewById(R.id.btn_Otolaryngology);
        Btn_Surgical = (ImageButton) findViewById(R.id.btn_Surgical);
        Btn_Urology = (ImageButton) findViewById(R.id.btn_Urology);
        Btn_Obstetrics_Gynecology = (ImageButton) findViewById(R.id.btn_Obstetrics_Gynecology);


        Btn_Internal_Medicine.setOnClickListener(this);
        Btn_Dermatology.setOnClickListener(this);
        Btn_Ophthalmology.setOnClickListener(this);
        Btn_Cosmetic_Surgery.setOnClickListener(this);
        Btn_Orthopedics.setOnClickListener(this);
        Btn_Otolaryngology.setOnClickListener(this);
        Btn_Surgical.setOnClickListener(this);
        Btn_Urology.setOnClickListener(this);
        Btn_Obstetrics_Gynecology.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_Internal_Medicine:
                MedicalsubCd = "01";
                Intent intent01 = new Intent(getApplicationContext(), Recyclerview_HospitalList.class);
                intent01.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent01.putExtra("city_name", city_name);
                intent01.putExtra("gu_name", gu_name);
                intent01.putExtra("search", search);
                intent01.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent01);
                break;
            case R.id.btn_Dermatology:
                MedicalsubCd = "14";
                Intent intent14 = new Intent(getApplicationContext(), Recyclerview_HospitalList.class);
                intent14.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent14.putExtra("city_name", city_name);
                intent14.putExtra("gu_name", gu_name);
                intent14.putExtra("search", search);
                intent14.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent14);
                break;
            case R.id.btn_Ophthalmology:
                MedicalsubCd = "12";
                Intent intent12 = new Intent(getApplicationContext(), Recyclerview_HospitalList.class);
                intent12.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent12.putExtra("city_name", city_name);
                intent12.putExtra("gu_name", gu_name);
                intent12.putExtra("search", search);
                intent12.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent12);
                break;
            case R.id.btn_Cosmetic_Surgery:
                MedicalsubCd = "08";
                Intent intent08 = new Intent(getApplicationContext(), Recyclerview_HospitalList.class);
                intent08.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent08.putExtra("city_name", city_name);
                intent08.putExtra("gu_name", gu_name);
                intent08.putExtra("search", search);
                intent08.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent08);
                break;
            case R.id.btn_Orthopedics:
                MedicalsubCd = "05";
                Intent intent05 = new Intent(getApplicationContext(), Recyclerview_HospitalList.class);
                intent05.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent05.putExtra("city_name", city_name);
                intent05.putExtra("gu_name", gu_name);
                intent05.putExtra("search", search);
                intent05.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent05);
                break;
            case R.id.btn_Otolaryngology:
                MedicalsubCd = "13";
                Intent intent49 = new Intent(getApplicationContext(), Recyclerview_HospitalList.class);
                intent49.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent49.putExtra("city_name", city_name);
                intent49.putExtra("gu_name", gu_name);
                intent49.putExtra("search", search);
                intent49.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent49);
                break;
            case R.id.btn_Surgical:
                MedicalsubCd = "04";
                Intent intent04 = new Intent(getApplicationContext(), Recyclerview_HospitalList.class);
                intent04.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent04.putExtra("city_name", city_name);
                intent04.putExtra("gu_name", gu_name);
                intent04.putExtra("search", search);
                intent04.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent04);
                break;
            case R.id.btn_Urology:
                MedicalsubCd = "15";
                Intent intent15 = new Intent(getApplicationContext(), Recyclerview_HospitalList.class);
                intent15.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent15.putExtra("city_name", city_name);
                intent15.putExtra("gu_name", gu_name);
                intent15.putExtra("search", search);
                intent15.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent15);
                break;
            case R.id.btn_Obstetrics_Gynecology:
                MedicalsubCd = "10";
                Intent intent10 = new Intent(getApplicationContext(), Recyclerview_HospitalList.class);
                intent10.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent10.putExtra("city_name", city_name);
                intent10.putExtra("gu_name", gu_name);
                intent10.putExtra("search", search);
                intent10.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent10);
                break;
            default:
                break;
        }
    }
}