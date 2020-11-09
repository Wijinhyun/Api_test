package com.example.api_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class Search_btn extends AppCompatActivity {

    private EditText editText;
    private String search;
    private String city_name;
    private String gu_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_btn);

        Intent intent = getIntent();
        city_name = intent.getStringExtra("city_name");
        gu_name = intent.getStringExtra("gu_name");

        editText = (EditText) findViewById(R.id.et_search);
        editText.requestFocus();
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 검색 동작

                        break;
                    default:
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        if(editText.getText().toString().length() != 0) {
                            search = editText.getText().toString();
                            intent.putExtra("search", search);
                        }
                        intent.putExtra("city_name", city_name);
                        intent.putExtra("gu_name", gu_name);
                        startActivity(intent);
                        return false;
                }
                return true;
            }
        });


    }
}