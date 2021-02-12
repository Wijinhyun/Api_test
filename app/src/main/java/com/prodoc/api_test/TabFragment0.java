package com.prodoc.api_test;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.io.IOException;
import java.util.List;

public class TabFragment0 extends Fragment implements View.OnClickListener{
    ViewPager viewPager;

    private String city_name;
    private String gu_name;
    private String search;
    private String MedicalsubCd;

    GPSTracker gps;
    private double latitude;
    private double longitude;

    public TabFragment0(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabfragment0, container, false);


        city_name = getActivity().getIntent().getStringExtra("city_name");
        gu_name = getActivity().getIntent().getStringExtra("gu_name");
        search = getActivity().getIntent().getStringExtra("search");
        final Geocoder geocoder = new Geocoder(getActivity());

        if (city_name == null && gu_name == null) {
            // Create class object
            gps = new GPSTracker(getActivity());
            Log.e("geogeogeo", "51번째 줄");

            // Check if GPS enabled
            if(gps.canGetLocation()) {

                getpermisson();

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                Log.e("geogeogeo", "latitude" + latitude);

                if(latitude == 0 || longitude == 0){
                    Toast.makeText(getActivity(), "GPS 활용 거부로 인해 초기위치값이 경북대로 설정되었습니다", Toast.LENGTH_LONG).show();
                    latitude = 35.887515;      // gps 거부한 경우에 초기위치값으로 경대 2호관 설정
                    longitude = 128.611553;
                }

                // \n is for new line
                //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                // 위치정보 못얻어서 대구 북구로 설정한다는 토스트 띄우고, 경대 2호관으로 초기값 설정
//                Toast.makeText(getActivity(), "GPS 활용 거부로 인해 초기위치값이 경북대로 설정되었습니다", Toast.LENGTH_LONG).show();
//                latitude = 35.887515;      // gps 거부한 경우에 초기위치값으로 경대 2호관 설정
//                longitude = 128.611553;
                gps.showSettingsAlert();
                Log.e("geogeogeo", "latitude" + latitude);
            }

            List<Address> list = null;
            try {

                list = geocoder.getFromLocation(
                        latitude, // 위도
                        longitude, // 경도
                        1); // 얻어올 값의 개수
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
            }
            if (list != null) {
                if (list.size()==0) {

                } else {
                    city_name = list.get(0).getAdminArea();
                    if(list.get(0).getLocality() != null && list.get(0).getLocality().length() > 0){
                        gu_name = list.get(0).getLocality();
                    }else{
                        gu_name = list.get(0).getSubLocality();
                    }
                }
            }

        }

        LinearLayout l1 = view.findViewById(R.id.tab_DER);
        l1.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tab_DER:
                Log.e("geocode_check", "city_name" + city_name + "gu_name" + gu_name);
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

    private void getpermisson() {

        // 메니패스트에 권한이 있는지 확인
        int permiCheck_loca = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        //앱권한이 없으면 권한 요청
        if(permiCheck_loca == PackageManager.PERMISSION_DENIED){
            Log.d("전화 권한 없는 상태", "");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        //권한 있다면
        else{
            Log.d("전화 권한 있는 상태", "");
        }
    }

}
