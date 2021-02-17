package com.prodoc.api_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.prodoc.api_test.R;

public class Seoul_listview extends AppCompatActivity {

    private ListView Lv_gu;
    private TextView Tv_precity;
    private String city_name;
    private String Imfrom;
    private String MedicalsubCd;
    private String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seoul_listview);
        Intent intent = getIntent();
        city_name = intent.getStringExtra("city_name");
        Imfrom = intent.getStringExtra("Imfrom");
        MedicalsubCd = intent.getStringExtra("MedicalsubCd");
        search = intent.getStringExtra("search");

        init();

        Lv_gu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(Imfrom.equals("recyclerview")) {
                    Intent intent = new Intent(getApplicationContext(), Recyclerview_HospitalList.class);

                    intent.putExtra("city_name", city_name);
                    intent.putExtra("gu_name", (String) Lv_gu.getAdapter().getItem(position)); /*송신*/
                    intent.putExtra("MedicalsubCd", MedicalsubCd);
                    intent.putExtra("search", search);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
                /*
                else{

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                    intent.putExtra("city_name", city_name);
                    intent.putExtra("gu_name", (String) Lv_gu.getAdapter().getItem(position));
                    intent.putExtra("search", search);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
                */
            }
        });
    }

    private void init() {
        Lv_gu = (ListView) findViewById(R.id.lv_gu);
        Tv_precity = (TextView) findViewById(R.id.tv_precity);

        String[] gu;
        if(city_name.equals("서울특별시")) {
            gu = getResources().getStringArray(R.array.seoul_middle);
        }else if(city_name.equals("경기도")){
            gu = getResources().getStringArray(R.array.geunggi_middle);
        }else if(city_name.equals("부산광역시")){
            gu = getResources().getStringArray(R.array.busan_middle);
        }else if(city_name.equals("인천광역시")){
            gu = getResources().getStringArray(R.array.incheon_middle);
        }else if(city_name.equals("대구광역시")){
            gu = getResources().getStringArray(R.array.daegu_middle);
        }else if(city_name.equals("대전광역시")){
            gu = getResources().getStringArray(R.array.daejeun_middle);
        }else if(city_name.equals("광주광역시")){
            gu = getResources().getStringArray(R.array.gangju_middle);
        }else if(city_name.equals("울산광역시")){
            gu = getResources().getStringArray(R.array.ulsan_middle);
        }else if(city_name.equals("경상남도")){
            gu = getResources().getStringArray(R.array.geungnam_middle);
        }else if(city_name.equals("경상북도")){
            gu = getResources().getStringArray(R.array.geungbook_middle);
        }else if(city_name.equals("전라남도")){
            gu = getResources().getStringArray(R.array.junnam_midlle);
        }else if(city_name.equals("전라북도")){
            gu = getResources().getStringArray(R.array.junbook_middle);
        }else if(city_name.equals("충청남도")){
            gu = getResources().getStringArray(R.array.choongnam_middle);
        }else if(city_name.equals("충청북도")){
            gu = getResources().getStringArray(R.array.choongbook_middle);
        }else if(city_name.equals("강원도")){
            gu = getResources().getStringArray(R.array.gangwon_middle);
        }else if(city_name.equals("세종특별자치시")){
            gu = getResources().getStringArray(R.array.sejong_middle);
        }else{
            gu = getResources().getStringArray(R.array.jeaju_middle);
        }

        Tv_precity.setText(city_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gu);

        Lv_gu.setAdapter(adapter);
    }
}