package com.example.api_test;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MapActivity extends AppCompatActivity implements Overlay.OnClickListener, OnMapReadyCallback, NaverMap.OnCameraChangeListener, NaverMap.OnCameraIdleListener, NaverMap.OnMapClickListener {

    private static final int ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 100;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private boolean isCameraAnimated = false;
    private FloatingActionButton Fb_tolist;
    private String city_name;
    private String gu_name;
    private String MedicalsubCd;
    private List<Marker> markerList = new ArrayList<>();
    private InfoWindow infoWindow;
    private String hospitalCode;
    private String temp, temp2;
    private String temp_cnt;
    private String search;
    private String subject;

    ArrayList<HospitalItem> list = null;
    HospitalItem item = null;
    XmlPullParser xpp;
    private String mykey = "%2BHeQuB3%2FCasGAbmRnedYca%2B6ESWu%2FcHnzFBtykDvwHZZLfz0ZTTJ2mANSme5%2Blr1DgBnQ4WJnmLXPwxsatF3Pw%3D%3D";
    private double Xpos;
    private double Ypos;
    private double init_xpos = 128.611553;
    private double init_ypos = 35.887515;
    private double new_xpos;
    private double new_ypos;
    private double mark_xpos;
    private double mark_ypos;
    private Button btn_call;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplayout);

        Intent intent = getIntent();
        city_name = intent.getStringExtra("city_name");
        gu_name = intent.getStringExtra("gu_name");
        MedicalsubCd = intent.getStringExtra("MedicalsubCd");
        search = intent.getStringExtra("search");
        subject = intent.getStringExtra("subject");

        Fb_tolist = findViewById(R.id.fb_tolist);
        Fb_tolist.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Recyclerview_HospitalList.class);
                intent.putExtra("MedicalsubCd", MedicalsubCd);
                intent.putExtra("city_name", city_name);
                intent.putExtra("gu_name", gu_name);
                intent.putExtra("search", search);
                startActivity(intent);
                finish();
            }
        });



        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        locationSource = new FusedLocationSource(this,ACCESS_LOCATION_PERMISSION_REQUEST_CODE);
        naverMap.setLocationSource(locationSource);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        naverMap.addOnCameraChangeListener(this);
        naverMap.addOnCameraIdleListener(this);
        naverMap.setOnMapClickListener(this);

        infoWindow = new InfoWindow();

        infoWindow.setOnClickListener(new InfoWindow.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Toast.makeText(MapActivity.this, "세부정보로 넘어가게 구현", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        infoWindow.setAdapter(new InfoWindow.DefaultViewAdapter(this) {
            @NonNull
            @Override
            protected View getContentView(@NonNull InfoWindow infoWindow) {

                Marker marker = infoWindow.getMarker();
                HospitalItem hospitalItem = (HospitalItem) marker.getTag();
                View view = View.inflate(MapActivity.this,R.layout.view_info_window,null);



                // distance 계산
                new_xpos = Double.parseDouble(hospitalItem.getXpos());
                new_ypos = Double.parseDouble(hospitalItem.getYpos());

                int distance = calculateDistanceInKilometer(init_ypos,init_xpos,new_ypos,new_xpos);

                // percent 계산
                String percent = "0.0";
                percent = String.format("%.1f", Double.parseDouble(hospitalItem.getSdrdgsCnt()) / Double.parseDouble(hospitalItem.getDrTotCnt()) * 100);

                if(Double.parseDouble(percent) >= 66.6){
                    ((ImageView) view.findViewById(R.id.iw_circle)).setImageResource(R.drawable.greencircle);
                }else if(Double.parseDouble(percent) >= 33.3){
                    ((ImageView) view.findViewById(R.id.iw_circle)).setImageResource(R.drawable.yellowcircle);
                }else if(Double.parseDouble(percent) >= 0.1) {          // 전문의가 아예 없으면 지도에 띄우지 않음
                    ((ImageView) view.findViewById(R.id.iw_circle)).setImageResource(R.drawable.redcircle);
                }

                ((TextView) view.findViewById(R.id.iw_name)).setText(hospitalItem.getYadmNm());
                ((TextView) view.findViewById(R.id.iw_distance)).setText(distance + "m");
                ((TextView) view.findViewById(R.id.iw_addr)).setText(hospitalItem.getAddr());
                ((TextView) view.findViewById(R.id.iw_percent)).setText(percent + "%");
                ((TextView) view.findViewById(R.id.iw_sdrdgsCnt)).setText(hospitalItem.getSdrdgsCnt() + "명");
                ((TextView) view.findViewById(R.id.iw_drTotCnt)).setText(hospitalItem.getDrTotCnt());
                ((TextView) view.findViewById(R.id.iw_sbj)).setText(subject + " 전문의 : ");



                return view;
            }
        });


//        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
//            @NonNull
//            @Override
//            public CharSequence getText(@NonNull InfoWindow infoWindow) {
//                Marker marker = infoWindow.getMarker();
//                String store = (String) marker.getTag();
//                return store;
//            }
//        });

        LatLng location = new LatLng(init_ypos, init_xpos);

        CameraPosition cameraPosition = new CameraPosition(location, 14);
        naverMap.setCameraPosition(cameraPosition);

        LatLng mapCenter = naverMap.getCameraPosition().target;

        Xpos = mapCenter.longitude;
        Ypos = mapCenter.latitude;

        findHospital();
    }

    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        if(overlay instanceof Marker){
            Marker marker = (Marker) overlay;
            if(marker.getInfoWindow() != null){
                infoWindow.close();
            }
            else{
                HospitalItem hospitalItem = (HospitalItem) marker.getTag();
                mark_xpos = Double.parseDouble(hospitalItem.getXpos());
                mark_ypos = Double.parseDouble(hospitalItem.getYpos());

                LatLng location = new LatLng(mark_ypos, mark_xpos);

                CameraPosition cameraPosition = new CameraPosition(location, 14);
                naverMap.setCameraPosition(cameraPosition);

                infoWindow.open(marker);
//                btn_call = findViewById(R.id.iw_call);// 통화로 넘어가는 클릭리스너
//                btn_call.setOnClickListener(new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(MapActivity.this, "통화로 넘어가게 구현", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
            return true;
        }
        return false;
    }

    private void updateMapMarkers() {
        resetMarkerList();
        Log.d("list_check", list.size() + "");
        if(list.isEmpty() == false || list.size() != 0) {
            for(int i=0;i<list.size();i++) {
                Marker marker = new Marker();
//                marker.setTag(list.get(i).getYadmNm());
                marker.setTag(list.get(i));
//                marker.setPosition(new LatLng(Double.parseDouble(list.get(i).YPos), Double.parseDouble(list.get(i).XPos)));
                marker.setPosition(new LatLng(Double.parseDouble(list.get(i).getYpos()), Double.parseDouble(list.get(i).getXpos())));

                Log.d("list_getSdrdgs", list.get(i).getSdrCnt() + "");
                Log.d("list_getDrTotCnt", list.get(i).getDrTotCnt() + "");
                String percent = "0.0";
                //if(list.get(i).getSdrdgsCnt() != null && !list.get(i).getDrTotCnt().equals("0")) {
                if(list.get(i).getSdrdgsCnt() != null && list.get(i).getDrTotCnt() != null) {
                    percent = String.format("%.1f", Double.parseDouble(list.get(i).getSdrdgsCnt()) / Double.parseDouble(list.get(i).getDrTotCnt()) * 100);
                }
                else{
                    percent = "0.0";
                }

                if(percent == "0.0"){
                    continue;
                }

                Log.d("percent", percent + "");

                if(Double.parseDouble(percent) >= 66.6){
                    marker.setIcon(OverlayImage.fromResource(R.drawable.greencross));
                    marker.setWidth(140);
                    marker.setHeight(80);
                    marker.setAnchor(new PointF(0.5f,1.0f));
                    marker.setMap(naverMap);
                    marker.setOnClickListener(this);
                    markerList.add(marker);
                }else if(Double.parseDouble(percent) >= 33.3){
                    marker.setIcon(OverlayImage.fromResource(R.drawable.yellowcross));
                    marker.setWidth(140);
                    marker.setHeight(80);
                    marker.setAnchor(new PointF(0.5f,1.0f));
                    marker.setMap(naverMap);
                    marker.setOnClickListener(this);
                    markerList.add(marker);
                }else if(Double.parseDouble(percent) >= 0.1) {          // 전문의가 아예 없으면 지도에 띄우지 않음
                    marker.setIcon(OverlayImage.fromResource(R.drawable.redcross));
                    marker.setWidth(140);
                    marker.setHeight(80);
                    marker.setAnchor(new PointF(0.5f, 1.0f));
                    marker.setMap(naverMap);
                    marker.setOnClickListener(this);
                    markerList.add(marker);
                }


            }
        }
    }

    private void resetMarkerList(){
        if(markerList != null && markerList.size() > 0){
            for(Marker marker : markerList){
                marker.setMap(null);
            }
            markerList.clear();
        }
    }
    private void resetlist(){
        if(list != null && list.size() > 0){
            list.clear();
        }
    }

    private void findHospital(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                resetlist();
                getXmlData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(list.isEmpty() == false || list.size() != 0) {
                            Log.d("list_check", list.size() + "");
                            updateMapMarkers();
                        }
                        else
                            Log.d("list_check", list.size() + "");
                    }
                });
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case ACCESS_LOCATION_PERMISSION_REQUEST_CODE:
                locationSource.onRequestPermissionsResult(requestCode,permissions,grantResults);
                return;
        }
    }

    @Override
    public void onCameraChange(int i, boolean b) {
        isCameraAnimated = b;
    }

    @Override
    public void onCameraIdle() {
        if(isCameraAnimated){
            LatLng mapCenter = naverMap.getCameraPosition().target;
            Xpos = mapCenter.longitude;
            Ypos = mapCenter.latitude;
            findHospital();
        }
    }

    @Override
    public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
        if (infoWindow.getMarker() != null){
            infoWindow.close();
        }
    }

    private void getXmlData() {
        StringBuffer buffer = new StringBuffer();

        String queryUrl = "http://apis.data.go.kr/B551182/hospInfoService/getHospBasisList?" + "ServiceKey=" + mykey + "&clCd=31" + "&numOfRows=50"
                + "&dgsbjtCd=" + MedicalsubCd + "&xPos=" + Xpos + "&yPos=" + Ypos + "&radius=" + 1000;
        Log.d("TAG", queryUrl);  // 일단 test 용으로 피부과 코드 14 넣어서 피부과만 받음, 반경은 1.5km로 설정
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        list = new ArrayList<HospitalItem>();
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//테그 이름 얻어오기
                        String test = xpp.getText();
                        if (tag.equals("item")) {
                            item = new HospitalItem();
                        } else if (tag.equals("addr")) {
                            xpp.next();
                            item.setAddr(xpp.getText());
                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");
                            Log.d("item_check_address", item.getAddr());

                        } else if (tag.equals("clCdNm")) {

                            xpp.next();
                            item.setClCdNm(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");

                        } else if (tag.equals("drTotCnt")) {

                            xpp.next();
                            item.setDrTotCnt(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");

                        } else if (tag.equals("hospUrl")) {

                            xpp.next();
                            item.setHospUrl(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");
                        } else if (tag.equals("sdrCnt")) {
                            xpp.next();
                            item.setSdrCnt(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");
                        } else if (tag.equals("telno")) {
                            xpp.next();
                            item.setTelno(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");

                        } else if (tag.equals("XPos")) {
                            xpp.next();
                            item.setXpos(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");

                        } else if (tag.equals("YPos")) {
                            xpp.next();
                            item.setYpos(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");

                        } else if (tag.equals("yadmNm")) {
                            xpp.next();
                            item.setYadmNm(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");

                        } else if (tag.equals("ykiho")) {
                            xpp.next();
                            item.setYkiho(xpp.getText());
                            hospitalCode=xpp.getText();
                            Log.d("Tag", hospitalCode);
                            getXmlData2();
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");
                        }
                        break;


                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //테그 이름 얻어오기

                        if (tag.equals("item") && item != null) {
                            Log.d("adapter_address_check", item.getAddr());
                            //Trial1
                            list.add(item);

                        }
                        break;
                }

                eventType = xpp.next();
            }
            Log.d("TAG", list.size() + "");
        } catch (Exception e) {
            getXmlData();
            e.printStackTrace();
        }

    }

    private void getXmlData2() {
        //StringBuffer buffer = new StringBuffer();

        String ykihoUrl="http://apis.data.go.kr/B551182/medicInsttDetailInfoService/getMdlrtSbjectInfoList?ykiho="+hospitalCode+"&ServiceKey="+mykey+ "&numOfRows=50";

        try {
            URL url2 = new URL(ykihoUrl);//문자열로 된 요청 url2을 URL 객체로 생성.
            InputStream is2 = url2.openStream(); //url2위치로 입력스트림 연결
            XmlPullParserFactory factory2 = XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp2 = factory2.newPullParser();
            xpp2.setInput(new InputStreamReader(is2, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag2;
            int eventType2 = xpp2.getEventType();
            while (eventType2 != XmlPullParser.END_DOCUMENT) {
                switch (eventType2) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.d("TAG", ykihoUrl);
                        break;

                    case XmlPullParser.START_TAG:
                        tag2 = xpp2.getName();//테그 이름 얻어오기
                        if (tag2.equals("dgsbjtCdNm")) {
                            xpp2.next();
                            temp = xpp2.getText();
                        }else if(tag2.equals("dgsbjtCd")){      // 진료과목 코드
                            xpp2.next();
                            temp2 = xpp2.getText();

                        } else if (tag2.equals("dgsbjtPrSdrCnt")) {     // 의사 수
                            xpp2.next();
                            temp_cnt = xpp2.getText();
                            if(temp2.equals(MedicalsubCd)){             // 해당 진료과목 의사라면
                                item.setSdrdgsCnt(temp_cnt);
                            }
                            if(!temp_cnt.equals("0")) {
                                item.setMedical_list(temp);
                                item.setDgsbjtCdNm(temp);
                                item.setDgsbjtPrSdrCnt(temp_cnt);
                            }
                            Log.d("Tag2", xpp2.getText());
                        }

                        break;


                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag2 = xpp2.getName(); //테그 이름 얻어오기

                        break;
                    default:
                        break;
                }

                eventType2 = xpp2.next();
            }

        } catch (Exception e) {
            getXmlData2();
            //e.printStackTrace();
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

}