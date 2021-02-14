package com.prodoc.api_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Search_btn extends AppCompatActivity {

    private EditText editText;
    private String search;
    private String city_name;
    private String gu_name;
    private String Imfrom;
    private String MedicalsubCd;
    private LinearLayout ll11;
    private ImageView Sch_map_btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_btn);

        Intent intent = getIntent();
        city_name = intent.getStringExtra("city_name");
        gu_name = intent.getStringExtra("gu_name");
        Imfrom = intent.getStringExtra("Imfrom");
        MedicalsubCd = intent.getStringExtra("MedicalsubCd");

        editText = (EditText) findViewById(R.id.et_search);
        editText.requestFocus();
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        Sch_map_btn_back = findViewById(R.id.sch_map_btn_back);
        Sch_map_btn_back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                onBackPressed();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        ll11 = findViewById(R.id.search_search_button);
        ll11.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                if(Imfrom.equals("recyclerview")) {
                    Intent intent1 = new Intent(getApplicationContext(), Recyclerview_HospitalList.class);
                    intent1.putExtra("MedicalsubCd", MedicalsubCd);
                    if (editText.getText().toString().length() != 0) {
                        search = editText.getText().toString();
                        intent1.putExtra("search", search);
                    }
                    intent1.putExtra("city_name", city_name);
                    intent1.putExtra("gu_name", gu_name);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }else {
                    Intent intent = new Intent(getApplicationContext(), MapActivityGooglemap.class);
                    intent.putExtra("MedicalsubCd", MedicalsubCd);
                    if (editText.getText().toString().length() != 0) {
                        search = editText.getText().toString();
                        intent.putExtra("search", search);
                    }
                    intent.putExtra("city_name", city_name);
                    intent.putExtra("gu_name", gu_name);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 검색 동작

                        break;
                    default:
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        if(Imfrom.equals("recyclerview")) {
                            Intent intent1 = new Intent(getApplicationContext(), Recyclerview_HospitalList.class);
                            intent1.putExtra("MedicalsubCd", MedicalsubCd);
                            if (editText.getText().toString().length() != 0) {
                                search = editText.getText().toString();
                                intent1.putExtra("search", search);
                            }
                            intent1.putExtra("city_name", city_name);
                            intent1.putExtra("gu_name", gu_name);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent1);
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        }else {
                            Intent intent = new Intent(getApplicationContext(), MapActivityGooglemap.class);
                            intent.putExtra("MedicalsubCd", MedicalsubCd);
                            if (editText.getText().toString().length() != 0) {
                                search = editText.getText().toString();
                                intent.putExtra("search", search);
                            }
                            intent.putExtra("city_name", city_name);
                            intent.putExtra("gu_name", gu_name);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        }
                        return false;
                }
                return true;
            }
        });


    }
}