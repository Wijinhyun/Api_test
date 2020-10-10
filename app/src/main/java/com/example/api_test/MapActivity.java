package com.example.api_test;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
    private String MedicalsubCd;
    private List<Marker> markerList = new ArrayList<>();
    private InfoWindow infoWindow;
    private String hospitalCode;
    private String temp, temp2;
    private String temp_cnt;

    ArrayList<HospitalItem> list = null;
    HospitalItem item = null;
    XmlPullParser xpp;
    private String mykey = "%2BHeQuB3%2FCasGAbmRnedYca%2B6ESWu%2FcHnzFBtykDvwHZZLfz0ZTTJ2mANSme5%2Blr1DgBnQ4WJnmLXPwxsatF3Pw%3D%3D";
    private double Xpos;
    private double Ypos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplayout);

        Intent intent = getIntent();
        MedicalsubCd = intent.getStringExtra("MedicalsubCd");

        Fb_tolist = findViewById(R.id.fb_tolist);
        Fb_tolist.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Recyclerview_HospitalList.class);
                startActivity(intent);
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
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                Marker marker = infoWindow.getMarker();
                String store = (String) marker.getTag();
                return store;
            }
        });

        LatLng mapCenter = naverMap.getCameraPosition().target;

        Xpos = mapCenter.longitude;
        Ypos = mapCenter.latitude;

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
    public boolean onClick(@NonNull Overlay overlay) {
        if(overlay instanceof Marker){
            Marker marker = (Marker) overlay;
            if(marker.getInfoWindow() != null){
                infoWindow.close();
            }
            else{
                infoWindow.open(marker);
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
                marker.setTag(list.get(i).getYadmNm());
//                marker.setPosition(new LatLng(Double.parseDouble(list.get(i).YPos), Double.parseDouble(list.get(i).XPos)));
                marker.setPosition(new LatLng(Double.parseDouble(list.get(i).getYpos()), Double.parseDouble(list.get(i).getXpos())));

//                String percent = "0.0";
//                if(list.get(i).getSdrdgsCnt() != null && !list.get(i).getDrTotCnt().equals("0")) {
//                    percent = String.format("%.1f", Double.parseDouble(list.get(i).getSdrdgsCnt()) / Double.parseDouble(list.get(i).getDrTotCnt()) * 100);
//                }
//                else{
//                    percent = "0.0";
//                }
//                if(Double.parseDouble(percent) >= 66.6){
//                    marker.setIcon(OverlayImage.fromResource(R.drawable.green));
//                }else if(Double.parseDouble(percent) >= 33.3){
//                    marker.setIcon(OverlayImage.fromResource(R.drawable.yellow));
//                }else if(Double.parseDouble(percent) >= 0.1){          // 전문의가 아예 없으면 지도에 띄우지 않음
//                    marker.setIcon(OverlayImage.fromResource(R.drawable.red));
//                }else{
//                    marker.setIcon(OverlayImage.fromResource(R.drawable.purple));
//                }

                marker.setAnchor(new PointF(0.5f,1.0f));
                marker.setMap(naverMap);
                marker.setOnClickListener(this);
                markerList.add(marker);
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
    }

    @Override
    public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
        if (infoWindow.getMarker() != null){
            infoWindow.close();
        }
    }

    private void getXmlData() {
        StringBuffer buffer = new StringBuffer();

        String queryUrl = "http://apis.data.go.kr/B551182/hospInfoService/getHospBasisList?" + "ServiceKey=" + mykey + "&clCd=31" + "&numOfRows=30"
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
            e.printStackTrace();
        }

    }
}
