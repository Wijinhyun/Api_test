package com.prodoc.api_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class TabFragment0 extends Fragment implements View.OnClickListener{
    ViewPager viewPager;

    private String city_name;
    private String gu_name;
    private String search;
    private String MedicalsubCd;

    public TabFragment0(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabfragment0, container, false);

        LinearLayout l1 = view.findViewById(R.id.tab_DER);
        l1.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tab_DER:
                MedicalsubCd = "14";
                Intent intent14 = new Intent(getActivity(), Recyclerview_HospitalList.class);
                intent14.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
                intent14.putExtra("city_name", city_name);
                intent14.putExtra("gu_name", gu_name);
                intent14.putExtra("search", search);
                startActivity(intent14);
                break;
        }
    }
}
