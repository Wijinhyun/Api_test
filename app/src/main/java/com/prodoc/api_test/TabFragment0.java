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
    private String Imfrom;

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
        Imfrom = getActivity().getIntent().getStringExtra("Imfrom");

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
                    city_name = "대구광역시";
                    gu_name = "북구";
                } else {
                    city_name = list.get(0).getAdminArea();
                    if(list.get(0).getLocality() != null && list.get(0).getLocality().length() > 0){
                        gu_name = list.get(0).getLocality();
                    }else{
                        gu_name = list.get(0).getSubLocality();
                    }

                }
            }else{
                city_name = "대구광역시";
                gu_name = "북구";
            }

        }



        LinearLayout l01 = view.findViewById(R.id.tab_IM);
        LinearLayout l02 = view.findViewById(R.id.tab_NR);
        LinearLayout l03 = view.findViewById(R.id.tab_NP);
        LinearLayout l04 = view.findViewById(R.id.tab_GS);
        LinearLayout l05 = view.findViewById(R.id.tab_OS);
        LinearLayout l06 = view.findViewById(R.id.tab_NS);
        LinearLayout l07 = view.findViewById(R.id.tab_CS);
        LinearLayout l08 = view.findViewById(R.id.tab_PS);
        LinearLayout l09 = view.findViewById(R.id.tab_AN);
        LinearLayout l10 = view.findViewById(R.id.tab_OBGY);
        LinearLayout l11 = view.findViewById(R.id.tab_PED);
        LinearLayout l12 = view.findViewById(R.id.tab_EY);
        LinearLayout l13 = view.findViewById(R.id.tab_ENT);
        LinearLayout l14 = view.findViewById(R.id.tab_DER);
        LinearLayout l15 = view.findViewById(R.id.tab_UR);
        LinearLayout l16 = view.findViewById(R.id.tab_DR);
        LinearLayout l17 = view.findViewById(R.id.tab_RO);
        LinearLayout l19 = view.findViewById(R.id.tab_LM);
        LinearLayout l20 = view.findViewById(R.id.tab_TB);
        LinearLayout l21 = view.findViewById(R.id.tab_RM);
        LinearLayout l22 = view.findViewById(R.id.tab_NM);
        LinearLayout l23 = view.findViewById(R.id.tab_FM);
        LinearLayout l24 = view.findViewById(R.id.tab_EM);
        LinearLayout l25 = view.findViewById(R.id.tab_OEM);

        l01.setOnClickListener(this);
        l02.setOnClickListener(this);
        l03.setOnClickListener(this);
        l04.setOnClickListener(this);
        l05.setOnClickListener(this);
        l06.setOnClickListener(this);
        l07.setOnClickListener(this);
        l08.setOnClickListener(this);
        l09.setOnClickListener(this);
        l10.setOnClickListener(this);
        l11.setOnClickListener(this);
        l12.setOnClickListener(this);
        l13.setOnClickListener(this);
        l14.setOnClickListener(this);
        l15.setOnClickListener(this);
        l16.setOnClickListener(this);
        l17.setOnClickListener(this);
        l19.setOnClickListener(this);
        l20.setOnClickListener(this);
        l21.setOnClickListener(this);
        l22.setOnClickListener(this);
        l23.setOnClickListener(this);
        l24.setOnClickListener(this);
        l25.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tab_IM:
                PushIntent("01");
                break;
            case R.id.tab_NR:
                PushIntent("02");
                break;
            case R.id.tab_NP:
                PushIntent("03");
                break;
            case R.id.tab_GS:
                PushIntent("04");
                break;
            case R.id.tab_OS:
                PushIntent("05");
                break;
            case R.id.tab_NS:
                PushIntent("06");
                break;
            case R.id.tab_CS:
                PushIntent("07");
                break;
            case R.id.tab_PS:
                PushIntent("08");
                break;
            case R.id.tab_AN:
                PushIntent("09");
                break;
            case R.id.tab_OBGY:
                PushIntent("10");
                break;
            case R.id.tab_PED:
                PushIntent("11");
                break;
            case R.id.tab_EY:
                PushIntent("12");
                break;
            case R.id.tab_ENT:
                PushIntent("13");
                break;
            case R.id.tab_DER:
                PushIntent("14");
                break;
            case R.id.tab_UR:
                PushIntent("15");
                break;
            case R.id.tab_DR:
                PushIntent("16");
                break;
            case R.id.tab_RO:
                PushIntent("17");
                break;
            case R.id.tab_LM:
                PushIntent("19");
                break;
            case R.id.tab_TB:
                PushIntent("20");
                break;
            case R.id.tab_RM:
                PushIntent("21");
                break;
            case R.id.tab_NM:
                PushIntent("22");
                break;
            case R.id.tab_FM:
                PushIntent("23");
                break;
            case R.id.tab_EM:
                PushIntent("24");
                break;
            case R.id.tab_OEM:
                PushIntent("25");
                break;
        }
    }

    private void PushIntent(String code){
        MedicalsubCd = code;
        if(Imfrom == null){
            Intent intent14 = new Intent(getActivity(), Recyclerview_HospitalList.class);
            intent14.putExtra("MedicalsubCd", MedicalsubCd);
            intent14.putExtra("city_name", city_name);
            intent14.putExtra("gu_name", gu_name);
            intent14.putExtra("search", search);
            startActivity(intent14);
            getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }else if(Imfrom.equals("recyclerview")) {
            Log.e("geocode_check", "city_name" + city_name + "gu_name" + gu_name);
            Intent intent14 = new Intent(getActivity(), Recyclerview_HospitalList.class);
            intent14.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
            intent14.putExtra("city_name", city_name);
            intent14.putExtra("gu_name", gu_name);
            intent14.putExtra("search", search);
            intent14.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent14);
            getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }else if(Imfrom.equals("googlemap")){
            Log.e("geocode_check", "city_name" + city_name + "gu_name" + gu_name);
            Intent intent14 = new Intent(getActivity(), MapActivityGooglemap.class);
            intent14.putExtra("MedicalsubCd", MedicalsubCd); /*송신*/
            intent14.putExtra("city_name", city_name);
            intent14.putExtra("gu_name", gu_name);
            intent14.putExtra("search", search);
            intent14.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent14);
            getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
