package com.example.api_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Region_listview extends AppCompatActivity {

    private ListView Lv_city;
    private String Imfrom;
    private String MedicalsubCd;
    private String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_listview);

        init();

        Intent intent = getIntent();
        Imfrom = intent.getStringExtra("Imfrom");
        MedicalsubCd = intent.getStringExtra("MedicalsubCd");
        search = intent.getStringExtra("search");

        Lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Seoul_listview.class);

                intent.putExtra("city_name", (String) Lv_city.getAdapter().getItem(position)); /*송신*/
                intent.putExtra("Imfrom", Imfrom); /*송신*/
                intent.putExtra("MedicalsubCd", MedicalsubCd);
                intent.putExtra("search", search);

                startActivity(intent);
            }
        });

    }

    private void init() {
        Lv_city = (ListView) findViewById(R.id.lv_city);

        String[] city = getResources().getStringArray(R.array.array_city);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, city);

        Lv_city.setAdapter(adapter);
    }

}