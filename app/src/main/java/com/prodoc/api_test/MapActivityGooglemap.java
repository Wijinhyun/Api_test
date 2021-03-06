package com.prodoc.api_test;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapActivityGooglemap extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, ClusterManager.OnClusterClickListener<Cluster_item>, ClusterManager.OnClusterItemClickListener<Cluster_item> {

    private static final int NORMAL_HOSPITAL_RANGE = 25;
    private static final int PHARMACY_RANGE = 30;
    private static final int DENTIST_RANGE = 61;
    private static final int ORIENTAL_HOSPITAL_RANGE = 87;

    private static final int ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 100;
    private GoogleMap mMap;
    private double Xpos;
    private double Ypos;
    private double new_xpos;
    private double new_ypos;
    private double mark_xpos;
    private double mark_ypos;
    private double temp_xpos;
    private double temp_ypos;
    private double init_xpos;
    private double init_ypos;
    private ClusterManager clusterManager;
    MarkerRenderer mRenderer;
    private String city_name;
    private String gu_name;
    private String MedicalsubCd;
    private String search;
    private String subject;
    private FloatingActionButton Fb_tolist;
    private ImageView map_Btn_back, map_Search;
    private Button map_Btn_medical_subject, map_Btn_search;

    private TextView Tv_con_titles;

    private int markerclicked_check;

    List<Marker> AllMarkers = new ArrayList<Marker>();

    GPSTracker gps;
    ArrayList<HospitalItemForCsv> arr = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemap);

        gps = new GPSTracker(MapActivityGooglemap.this);
        if(gps.canGetLocation()) {

            getpermisson();

            init_ypos = gps.getLatitude();
            init_xpos = gps.getLongitude();

            if(init_ypos == 0 || init_xpos == 0){
                Toast.makeText(getApplicationContext(), "GPS 활용 거부로 인해 초기위치값이 경북대로 설정되었습니다", Toast.LENGTH_LONG).show();
                init_ypos = 35.887515;      // gps 거부한 경우에 초기위치값으로 경대 2호관 설정
                init_xpos = 128.611553;
                Log.d("googlemap에서 위치정보 못얻", init_ypos + "");
            }

            // \n is for new line
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            Toast.makeText(getApplicationContext(), "GPS 활용 거부로 인해 초기위치값이 경북대로 설정되었습니다", Toast.LENGTH_LONG).show();
            init_ypos = 35.887515;      // gps 거부한 경우에 초기위치값으로 경대 2호관 설정
            init_xpos = 128.611553;
            gps.showSettingsAlert();
        }

        temp_xpos = init_xpos;
        temp_ypos = init_ypos;

        Intent intent = getIntent();
        city_name = intent.getStringExtra("city_name");
        gu_name = intent.getStringExtra("gu_name");
        MedicalsubCd = intent.getStringExtra("MedicalsubCd");
        search = intent.getStringExtra("search");
        subject = intent.getStringExtra("subject");
//        init_xpos = intent.getDoubleExtra("longitude",128.611553);
//        init_ypos = intent.getDoubleExtra("latitude",35.887515);

        Tv_con_titles = findViewById(R.id.map_con_titles);

        map_Btn_back = findViewById(R.id.map_btn_back);
        map_Search = findViewById(R.id.map_search);
        map_Btn_search = findViewById(R.id.map_btn_search);
        map_Btn_medical_subject = findViewById(R.id.map_btn_medical_subject);

        map_Btn_back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        map_Search.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), Search_btn.class);
                intent3.putExtra("Imfrom", "googlemap");
                intent3.putExtra("MedicalsubCd", MedicalsubCd);
                intent3.putExtra("city_name", city_name);
                intent3.putExtra("gu_name", gu_name);
                startActivity(intent3);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
        map_Btn_search.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), Search_btn.class);
                intent3.putExtra("Imfrom", "googlemap");
                intent3.putExtra("MedicalsubCd", MedicalsubCd);
                intent3.putExtra("city_name", city_name);
                intent3.putExtra("gu_name", gu_name);
                startActivity(intent3);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
        map_Btn_medical_subject.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), SubjectSelectActivity.class);
                if(Integer.parseInt(MedicalsubCd) <= NORMAL_HOSPITAL_RANGE){
                    intent2.putExtra("pagenumber", 0);
                }else if(Integer.parseInt(MedicalsubCd) <= PHARMACY_RANGE){
                    intent2.putExtra("pagenumber", 3);
                }else if(Integer.parseInt(MedicalsubCd) <= DENTIST_RANGE){
                    intent2.putExtra("pagenumber", 1);
                }else{
                    intent2.putExtra("pagenumber", 2);
                }
                intent2.putExtra("Imfrom", "googlemap");
                intent2.putExtra("city_name", city_name);
                intent2.putExtra("gu_name", gu_name);
                intent2.putExtra("search",search);
                startActivity(intent2);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });


        Fb_tolist = findViewById(R.id.fb_tolist);
        Fb_tolist.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Recyclerview_HospitalList.class);
                intent.putExtra("MedicalsubCd", MedicalsubCd);
                intent.putExtra("city_name", city_name);
                intent.putExtra("gu_name", gu_name);
                intent.putExtra("search", search);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        if(MedicalsubCd.equals("01")){
            Tv_con_titles.setText("의원");        // 맵에선 조금 다르게 할 거니까 이따가 일괄 삽입
            map_Btn_medical_subject.setText(" 내과 ");
            subject = "내과";
        }else if(MedicalsubCd.equals("02")){
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText(" 신경과 ");
            subject = "신경과";
        }else if(MedicalsubCd.equals("03")) {
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText(" 정신건강의학과 ");
            subject = "정신건강의학과";
        }else if(MedicalsubCd.equals("04")){
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("외과");
            subject = "외과";
        }else if(MedicalsubCd.equals("05")) {
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("정형외과");
            subject = "정형외과";
        }else if(MedicalsubCd.equals("06")) {
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("신경외과");
            subject = "신경외과";
        }else if(MedicalsubCd.equals("07")) {
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("흉부외과");
            subject = "흉부외과";
        }else if(MedicalsubCd.equals("08")){
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("성형외과");
            subject = "성형외과";
        }else if(MedicalsubCd.equals("09")) {
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("마취통증의학과");
            subject = "마취통증의학과";
        }else if(MedicalsubCd.equals("10")){
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("산부인과");
            subject = "산부인과";
        }else if(MedicalsubCd.equals("11")) {
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("소아청소년과");
            subject = "소아청소년과";
        }else if(MedicalsubCd.equals("12")){
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("안과");
            subject = "안과";
        }else if(MedicalsubCd.equals("13")){
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("이비인후과");
            subject = "이비인후과";
        }else if(MedicalsubCd.equals("14")){
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("피부과");
            subject = "피부과";
        }else if(MedicalsubCd.equals("15")){
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("비뇨기의학과");
            subject = "비뇨기의학과";
        }else if(MedicalsubCd.equals("16")) {
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("영상의학과");
            subject = "영상의학과";
        }else if(MedicalsubCd.equals("17")) {
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("방사선종양학과");
            subject = "방사선종양학과";
        }else if(MedicalsubCd.equals("19")) {
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("진단검사의학과");
            subject = "진단검사의학과";
        }else if(MedicalsubCd.equals("20")) {
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("결핵과");
            subject = "결핵과";
        }else if(MedicalsubCd.equals("21")) {
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("재활의학과");
            subject = "재활의학과";
        }else if(MedicalsubCd.equals("22")) {
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("핵의학과");
            subject = "핵의학과";
        }else if(MedicalsubCd.equals("23")) {
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("가정의학과");
            subject = "가정의학과";
        }else if(MedicalsubCd.equals("24")) {
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("응급의학과");
            subject = "응급의학과";
        }else if(MedicalsubCd.equals("25")) {
            Tv_con_titles.setText("의원");
            map_Btn_medical_subject.setText("직업환경의학과");
            subject = "직업환경의학과";
        }else if(MedicalsubCd.equals("30")) {
            Tv_con_titles.setText("약국");
            map_Btn_medical_subject.setText("약국");
            subject = "약국";
        }else if(MedicalsubCd.equals("50")) {
            Tv_con_titles.setText("치과의원");
            map_Btn_medical_subject.setText("구강악안면외과");
            subject = "구강악안면외과";
        }else if(MedicalsubCd.equals("51")) {
            Tv_con_titles.setText("치과의원");
            map_Btn_medical_subject.setText("치과보철과");
            subject = "치과보철과";
        }else if(MedicalsubCd.equals("52")) {
            Tv_con_titles.setText("치과의원");
            map_Btn_medical_subject.setText("치과교정과");
            subject = "치과교정과";
        }else if(MedicalsubCd.equals("53")) {
            Tv_con_titles.setText("치과의원");
            map_Btn_medical_subject.setText("소아치과");
            subject = "소아치과";
        }else if(MedicalsubCd.equals("54")) {
            Tv_con_titles.setText("치과의원");
            map_Btn_medical_subject.setText("치주과");
            subject = "치주과";
        }else if(MedicalsubCd.equals("55")) {
            Tv_con_titles.setText("치과의원");
            map_Btn_medical_subject.setText("치과보존과");
            subject = "치과보존과";
        }else if(MedicalsubCd.equals("56")) {
            Tv_con_titles.setText("치과의원");
            map_Btn_medical_subject.setText("구강내과");
            subject = "구강내과";
        }else if(MedicalsubCd.equals("57")) {
            Tv_con_titles.setText("치과의원");
            map_Btn_medical_subject.setText("영상치의학과");
            subject = "영상치의학과";
        }else if(MedicalsubCd.equals("58")) {
            Tv_con_titles.setText("치과의원");
            map_Btn_medical_subject.setText("구강병리과");
            subject = "구강병리과";
        }else if(MedicalsubCd.equals("59")) {
            Tv_con_titles.setText("치과의원");
            map_Btn_medical_subject.setText("예방치과");
            subject = "예방치과";
        }else if(MedicalsubCd.equals("61")) {
            Tv_con_titles.setText("치과의원");
            map_Btn_medical_subject.setText("통합치의학과");
            subject = "통합치의학과";
        }else if(MedicalsubCd.equals("80")) {
            Tv_con_titles.setText("한의원");
            map_Btn_medical_subject.setText("한방내과");
            subject = "한방내과";
        }else if(MedicalsubCd.equals("81")) {
            Tv_con_titles.setText("한의원");
            map_Btn_medical_subject.setText("한방부인과");
            subject = "한방부인과";
        }else if(MedicalsubCd.equals("82")) {
            Tv_con_titles.setText("한의원");
            map_Btn_medical_subject.setText("한방소아과");
            subject = "한방소아과";
        }else if(MedicalsubCd.equals("83")) {
            Tv_con_titles.setText("한의원");
            map_Btn_medical_subject.setText("한방안·이비인후·피부과");
            subject = "한방안·이비인후·피부과";
        }else if(MedicalsubCd.equals("84")) {
            Tv_con_titles.setText("한의원");
            map_Btn_medical_subject.setText("한방신경정신과");
            subject = "한방신경정신과";
        }else if(MedicalsubCd.equals("85")) {
            Tv_con_titles.setText("한의원");
            map_Btn_medical_subject.setText("침구과");
            subject = "침구과";
        }else if(MedicalsubCd.equals("86")) {
            Tv_con_titles.setText("한의원");
            map_Btn_medical_subject.setText("한방재활의학과");
            subject = "한방재활의학과";
        }else if(MedicalsubCd.equals("87")) {
            Tv_con_titles.setText("한의원");
            map_Btn_medical_subject.setText("사상체질과");
            subject = "사상체질과";
        }

//        if(MedicalsubCd.equals("01")){
//            map_Btn_medical_subject.setText("내과");
//            subject = "내과";
//        }else if(MedicalsubCd.equals("14")){
//            map_Btn_medical_subject.setText("피부과");
//            subject = "피부과";
//        }else if(MedicalsubCd.equals("12")){
//            map_Btn_medical_subject.setText("안과");
//            subject = "안과";
//        }else if(MedicalsubCd.equals("08")){
//            map_Btn_medical_subject.setText("성형외과");
//            subject = "성형외과";
//        }else if(MedicalsubCd.equals("05")) {
//            map_Btn_medical_subject.setText("정형외과");
//            subject = "정형외과";
//        }else if(MedicalsubCd.equals("13")){
//            map_Btn_medical_subject.setText("이비인후과");
//            subject = "이비인후과";
//        }else if(MedicalsubCd.equals("04")){
//            map_Btn_medical_subject.setText("외과");
//            subject = "외과";
//        }else if(MedicalsubCd.equals("15")){
//            map_Btn_medical_subject.setText("비뇨기의학과");
//            subject = "비뇨기의학과";
//        }else if(MedicalsubCd.equals("10")){
//            map_Btn_medical_subject.setText("산부인과");
//            subject = "산부인과";
//        }

        if(search != null){
            map_Btn_search.setText("검색어 : " + search);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        clusterManager = new ClusterManager<>(this,mMap);

        mRenderer = new MarkerRenderer(getApplicationContext(),mMap,clusterManager);
        clusterManager.setRenderer(mRenderer);


        //clusterManager.setRenderer(new MarkerRenderer(getApplicationContext(),mMap,clusterManager));
        clusterManager.setOnClusterItemClickListener(this);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Log.d("onmarkerclick 드러옴", arr.size() + "");
                return true;
            }
        });

        int permiCheck_loca = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        //앱권한이 없으면 권한 요청
        if(permiCheck_loca == PackageManager.PERMISSION_DENIED){
            Log.d("전화 권한 없는 상태", "");
            ActivityCompat.requestPermissions(MapActivityGooglemap.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        //권한 있다면
        else{
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    Log.d("mylocationbutton 눌러진 상태", "");
                    return false;
                }
            });
            Log.d("전화 권한 있는 상태", "");
        }

        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d("onmapclick 드러옴", arr.size() + "");
            }
        });



        LatLng START = new LatLng(init_ypos, init_xpos);            // 여기서 init 좌표값 못받은 거면 경대로 시작지점 정하면 될 듯

        if(search == null){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(START, 15));
        }else{
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(START, 8));
        }

        Log.d("movecamera", init_ypos + "");

        LatLng mapCenter = mMap.getCameraPosition().target;
        Xpos = mapCenter.longitude;
        Ypos = mapCenter.latitude;

        findHospital();

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                Log.d("초기idleidleidle", arr.size() + "");
                if(markerclicked_check == 1){
                    markerclicked_check = 0;
                }
                else{
                    Log.d("초기idleidleidle", arr.size() + "");

                    LatLng mapCenter = mMap.getCameraPosition().target;
                    Xpos = mapCenter.longitude;
                    Ypos = mapCenter.latitude;
                    int distance = calculateDistanceInKilometer(temp_ypos,temp_xpos,Ypos,Xpos);
                    if(distance >= 10000){
                        Log.d("초기idleidleidle", arr.size() + "");
                        temp_xpos = Xpos;
                        temp_ypos = Ypos;
                        findHospital();
                    }
                    clusterManager.cluster();
                }
            }
        });

        clusterManager.cluster();
    }

    private void updateMapMarkers() {
        //resetMarkerList();

        Log.d("arr_check", arr.size() + "");
        Log.d("location_check", init_xpos + "");
        Log.d("location_check", init_ypos + "");
        if(arr.isEmpty() == false || arr.size() != 0) {
            clusterManager.clearItems();
            for(int i=0;i<arr.size();i++) {

                Cluster_item cluster_item = new Cluster_item(Double.parseDouble(arr.get(i).getYpos()),Double.parseDouble(arr.get(i).getXpos()),arr.get(i).getPercent(),arr.get(i).getHospitalname(),
                        arr.get(i).getSubject(), arr.get(i).getAddr(), arr.get(i).getDistance(), arr.get(i).getPronum(),
                        arr.get(i).getTotalnum(), arr.get(i).getYpos(), arr.get(i).getXpos(), arr.get(i).getTel(),
                        arr.get(i).getPark(), arr.get(i).getUrl(), 0);
                clusterManager.addItem(cluster_item);

            }
        }
    }

    private void findHospital(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                resetarr();
                try {
                    readDataFromCsv();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(arr.isEmpty() == false || arr.size() != 0) {
                            Log.d("list_check", arr.size() + "");

                            updateMapMarkers();
                            //mMap.animateCamera(CameraUpdateFactory.zoomOut());
                            clusterManager.cluster();
                        }
                        else
                            Log.d("arr", arr.size() + "");
                    }
                });
            }
        }).start();
    }

    private void resetMarkerList() {
        for (Marker mLocationMarker: AllMarkers) {
            mLocationMarker.remove();
        }
        AllMarkers.clear();

    }
    private void resetarr(){
        if(arr != null && arr.size() > 0){
            arr.clear();
        }
    }

    public final static double AVERAGE_RADIUS_OF_EARTH_M = 6371000;
    public int calculateDistanceInKilometer(double userLat, double userLng,
                                            double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_M * c));
    }

    public void readDataFromCsv() throws IOException {
        // 진료과목 코드에 따라 어떤 csv파일 읽을지 여기서 스위치문 설정

        InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.medi17));
        if(MedicalsubCd.equals("01")){
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi01));
        }else if(MedicalsubCd.equals("02")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi02));
        }else if(MedicalsubCd.equals("03")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi03));
        }else if(MedicalsubCd.equals("04")){
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi04));
        }else if(MedicalsubCd.equals("05")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi05));
        }else if(MedicalsubCd.equals("06")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi06));
        }else if(MedicalsubCd.equals("07")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi07));
        }else if(MedicalsubCd.equals("08")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi08));
        }else if(MedicalsubCd.equals("09")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi09));
        }else if(MedicalsubCd.equals("10")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi10));
        }else if(MedicalsubCd.equals("11")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi11));
        }else if(MedicalsubCd.equals("12")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi12));
        }else if(MedicalsubCd.equals("13")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi13));
        }else if(MedicalsubCd.equals("14")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi14));
        }else if(MedicalsubCd.equals("15")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi15));
        }else if(MedicalsubCd.equals("16")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi16));
        }else if(MedicalsubCd.equals("17")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi17));
        }else if(MedicalsubCd.equals("19")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi19));
        }else if(MedicalsubCd.equals("20")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi20));
        }else if(MedicalsubCd.equals("21")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi21));
        }else if(MedicalsubCd.equals("22")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi22));
        }else if(MedicalsubCd.equals("23")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi23));
        }else if(MedicalsubCd.equals("24")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi24));
        }else if(MedicalsubCd.equals("25")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.medi25));
        }else if(MedicalsubCd.equals("50")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.dent50));
        }else if(MedicalsubCd.equals("51")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.dent51));
        }else if(MedicalsubCd.equals("52")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.dent52));
        }else if(MedicalsubCd.equals("53")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.dent53));
        }else if(MedicalsubCd.equals("54")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.dent54));
        }else if(MedicalsubCd.equals("55")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.dent55));
        }else if(MedicalsubCd.equals("56")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.dent56));
        }else if(MedicalsubCd.equals("57")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.dent57));
        }else if(MedicalsubCd.equals("58")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.dent58));
        }else if(MedicalsubCd.equals("59")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.dent59));
        }else if(MedicalsubCd.equals("61")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.dent61));
        }else if(MedicalsubCd.equals("80")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.orie80));
        }else if(MedicalsubCd.equals("81")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.orie81));
        }else if(MedicalsubCd.equals("82")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.orie82));
        }else if(MedicalsubCd.equals("83")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.orie83));
        }else if(MedicalsubCd.equals("84")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.orie84));
        }else if(MedicalsubCd.equals("85")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.orie85));
        }else if(MedicalsubCd.equals("86")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.orie86));
        }else if(MedicalsubCd.equals("87")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.orie87));
        }

        //InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.dataneakwa2));

        CSVReader reader = new CSVReader(is); // 1
        List<String[]> list = reader.readAll();

        if(arr.size() != 0){
            arr.clear();
        }
        Log.d("after_clear", arr.size() + "");

        for(String[] str : list){

            HospitalItemForCsv entity = new HospitalItemForCsv();
            entity.setYkiho(str[0]);
            entity.setHospitalname(str[1]);
            entity.setSubject(str[2]);
            entity.setPronum(str[3]);
            entity.setTotalnum(str[4]);
            entity.setPercent(str[5]);
            entity.setXpos(str[6]);
            entity.setYpos(str[7]);
            entity.setSidocode(str[8]);
            entity.setSidoname(str[9]);
            entity.setSigungucode(str[10]);
            entity.setSigunguname(str[11]);
            entity.setAddr(str[12]);
            entity.setTel(str[13]);
            entity.setUrl(str[14]);
            entity.setPark(str[15]);

            double lat = Double.parseDouble(str[7]);
            double lng = Double.parseDouble(str[6]);
            int distance = calculateDistanceInKilometer(init_ypos,init_xpos,lat,lng);
            entity.setDistance(String.format("%d",distance));

            int panjung = calculateDistanceInKilometer(temp_ypos,temp_xpos,lat,lng);

            // 검색어 있으면, 범위 상관없이 다 add, 검색어 없으면 범위 안에 있는 거만
            if(search != null){
                if(entity.getHospitalname().contains(search)){  // 검색어가 포함된 경우
                    arr.add(entity);
                }
                else{   // 검색어가 포함되지 않은 경우

                }
            } else if(panjung <= 10000){
                arr.add(entity);
            }

        }
        Log.d("after_for_clear", arr.size() + "");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d("onmarkerclick 들어왔어",  "");
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.greencross);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 120, 120, false);
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        return true;
    }

    @Override
    public boolean onClusterClick(Cluster<Cluster_item> cluster) { return false; }

    @Override
    public boolean onClusterItemClick(Cluster_item item) {
        LatLng mak = new LatLng(Double.parseDouble(item.getYpos()), Double.parseDouble(item.getXpos()));

        mMap.animateCamera(CameraUpdateFactory.newLatLng(mak),200,null);

        mRenderer.getMarker(item).showInfoWindow();
        if(item.getPercent() != null && item.getPandan() == 0){markerclicked_check = 1;

            final Intent intent = new Intent(getApplicationContext(),MarkerDialogActivity.class);
            intent.putExtra("percent",item.getPercent());
            intent.putExtra("hospitalname",item.getHospitalname());
            intent.putExtra("distance",item.getDistance());
            intent.putExtra("addr",item.getAddr());
            intent.putExtra("sbj",item.getSubject());
            intent.putExtra("pronum",item.getPronum());
            intent.putExtra("totalnum", item.getTotalnum());
            intent.putExtra("tel",item.getTel());
            intent.putExtra("park",item.getPark());
            intent.putExtra("url",item.getUrl());
            intent.putExtra("init_ypos",init_ypos);
            intent.putExtra("init_xpos",init_xpos);
            intent.putExtra("d_ypos",item.getYpos());
            intent.putExtra("d_xpos",item.getXpos());
            //startActivity(intent);

            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    Log.d("후기idleidleidle", arr.size() + "");
                    if(markerclicked_check == 1){
                        startActivity(intent);
                        markerclicked_check = 0;
                    }
                    else{
                        Log.d("후기idleidleidle", arr.size() + "");

                        LatLng mapCenter = mMap.getCameraPosition().target;
                        Xpos = mapCenter.longitude;
                        Ypos = mapCenter.latitude;
                        int distance = calculateDistanceInKilometer(temp_ypos,temp_xpos,Ypos,Xpos);
                        if(distance >= 10000){
                            Log.d("후기idleidleidle", arr.size() + "");
                            temp_xpos = Xpos;
                            temp_ypos = Ypos;
                            findHospital();
                        }
                        clusterManager.cluster();
                    }
                }
            });

            Log.d("onc if문 나가기 직전", arr.size() + "");

        }else if(item.getPercent() != null && item.getPandan() == 1){

//            item.setPandan(0);
//
//            String percent = "0.0";
//            percent = String.format("%.1f", Double.parseDouble(item.getPercent()));
//
//            if(Double.parseDouble(percent) >= 66.6){
//                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.greencross);
//                Bitmap b=bitmapdraw.getBitmap();
//                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 80, 80, false);
//                mRenderer.getMarker(item).setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
//            }else if(Double.parseDouble(percent) >= 33.3){
//                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.yellowcross);
//                Bitmap b=bitmapdraw.getBitmap();
//                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 80, 80, false);
//                mRenderer.getMarker(item).setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
//            }else if(Double.parseDouble(percent) >= 0.1) {          // 전문의가 아예 없으면 지도에 띄우지 않음
//                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.redcross);
//                Bitmap b=bitmapdraw.getBitmap();
//                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 80, 80, false);
//                mRenderer.getMarker(item).setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
//            }
        }


        return true;
    }

    private class MarkerRenderer  extends DefaultClusterRenderer<Cluster_item> {
        public MarkerRenderer(Context context, GoogleMap map, ClusterManager<Cluster_item> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(Cluster_item item, MarkerOptions markerOptions) {
            super.onBeforeClusterItemRendered(item, markerOptions);

            LatLng mak = new LatLng(Double.parseDouble(item.getYpos()), Double.parseDouble(item.getXpos()));
            String percent = "0.0";
            percent = String.format("%.1f", Double.parseDouble(item.getPercent()));

            if(Double.parseDouble(percent) >= 66.6){
                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.greencross);
                Bitmap b=bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 80, 80, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                markerOptions.position(mak).title(item.getHospitalname());
            }else if(Double.parseDouble(percent) >= 33.3){
                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.yellowcross);
                Bitmap b=bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 80, 80, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                markerOptions.position(mak).title(item.getHospitalname());
            }else if(Double.parseDouble(percent) >= 0.1) {          // 전문의가 아예 없으면 지도에 띄우지 않음
                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.redcross);
                Bitmap b=bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 80, 80, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                markerOptions.position(mak).title(item.getHospitalname());
            }

        }

        @Override
        public void setOnClusterItemClickListener(ClusterManager.OnClusterItemClickListener<Cluster_item> listener) {
            super.setOnClusterItemClickListener(listener);
            Log.d("onmarkerclick 여기 들어오나", arr.size() + "");
        }
    }

    private void getpermisson() {

        // 메니패스트에 권한이 있는지 확인
        int permiCheck_loca = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        //앱권한이 없으면 권한 요청
        if(permiCheck_loca == PackageManager.PERMISSION_DENIED){
            Log.d("전화 권한 없는 상태", "");
            ActivityCompat.requestPermissions(MapActivityGooglemap.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        //권한 있다면
        else{
            Log.d("전화 권한 있는 상태", "");
        }
    }

}