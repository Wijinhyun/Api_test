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
            }
        });
        map_Btn_medical_subject.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), SubjectSelectActivity.class);
                intent2.putExtra("pagenumber", 0);  // 의원 - 0, 치과의원 - 1, 한의원 - 2, 약국 - 3
                intent2.putExtra("Imfrom", "googlemap");
                intent2.putExtra("city_name", city_name);
                intent2.putExtra("gu_name", gu_name);
                intent2.putExtra("search",search);
                startActivity(intent2);
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
                finish();
                startActivity(intent);
            }
        });

        if(MedicalsubCd.equals("01")){
            map_Btn_medical_subject.setText("내과");
            subject = "내과";
        }else if(MedicalsubCd.equals("14")){
            map_Btn_medical_subject.setText("피부과");
            subject = "피부과";
        }else if(MedicalsubCd.equals("12")){
            map_Btn_medical_subject.setText("안과");
            subject = "안과";
        }else if(MedicalsubCd.equals("08")){
            map_Btn_medical_subject.setText("성형외과");
            subject = "성형외과";
        }else if(MedicalsubCd.equals("05")) {
            map_Btn_medical_subject.setText("정형외과");
            subject = "정형외과";
        }else if(MedicalsubCd.equals("13")){
            map_Btn_medical_subject.setText("이비인후과");
            subject = "이비인후과";
        }else if(MedicalsubCd.equals("04")){
            map_Btn_medical_subject.setText("외과");
            subject = "외과";
        }else if(MedicalsubCd.equals("15")){
            map_Btn_medical_subject.setText("비뇨기의학과");
            subject = "비뇨기의학과";
        }else if(MedicalsubCd.equals("10")){
            map_Btn_medical_subject.setText("산부인과");
            subject = "산부인과";
        }

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

        InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.plastic));
        if(MedicalsubCd.equals("01")){
            is = new InputStreamReader(getResources().openRawResource(R.raw.inside));
        }else if(MedicalsubCd.equals("14")){
            is = new InputStreamReader(getResources().openRawResource(R.raw.pbu));
        }else if(MedicalsubCd.equals("12")){
            is = new InputStreamReader(getResources().openRawResource(R.raw.eye));
        }else if(MedicalsubCd.equals("08")){
            is = new InputStreamReader(getResources().openRawResource(R.raw.plastic));
        }else if(MedicalsubCd.equals("05")) {
            is = new InputStreamReader(getResources().openRawResource(R.raw.jeonghyeong));
        }else if(MedicalsubCd.equals("13")){
            is = new InputStreamReader(getResources().openRawResource(R.raw.ebinhu));
        }else if(MedicalsubCd.equals("04")){
            is = new InputStreamReader(getResources().openRawResource(R.raw.outside));
        }else if(MedicalsubCd.equals("15")){
            is = new InputStreamReader(getResources().openRawResource(R.raw.benyo));
        }else if(MedicalsubCd.equals("10")){
            is = new InputStreamReader(getResources().openRawResource(R.raw.sanbu));
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