package com.example.api_test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, NaverMap.OnCameraChangeListener, NaverMap.OnCameraIdleListener {

    private static final int ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 100;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private boolean isCameraAnimated = false;
    private Button Btn_tolist;
    private List<Marker> markerList = new ArrayList<>();

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

        Btn_tolist = (Button) findViewById(R.id.show_list);
        Btn_tolist.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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

        LatLng mapCenter = naverMap.getCameraPosition().target;

//        Xpos = mapCenter.longitude;
//        Ypos = mapCenter.latitude;

        Xpos = mapCenter.longitude;
        Ypos = mapCenter.latitude;

//        Xpos = 128.6121750;
//        Ypos = 35.8906490;

//        Marker marker = new Marker();
//        marker.setPosition(new LatLng(Ypos,Xpos));
//        marker.setMap(naverMap);

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
//
//        getXmlData();
//
//        Log.d("TAG", list.size() + "");
//        if(list.isEmpty() == false || list.size() != 0) {
//            Log.d("list_check", list.size() + "");
//            updateMapMarkers();
//        }


//        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {
//            @Override
//            public void onCameraChange(int i, boolean b) {
//                freeActiveMarkers();
//                // 정의된 마커위치들중 가시거리 내에있는것들만 마커 생성
//                LatLng currentPosition = getCurrentPosition(naverMap);
//                for (LatLng markerPosition: markersPosition) {
//                    if (!withinSightMarker(currentPosition, markerPosition))
//                        continue;
//                    Marker marker = new Marker();
//                    marker.setPosition(markerPosition);
//                    marker.setMap(naverMap);
//                    activeMarkers.add(marker);
//                }
//            }
//        });

    }

    private void updateMapMarkers() {
        resetMarkerList();
        Log.d("list_check", list.size() + "");
        if(list.isEmpty() == false || list.size() != 0) {
            for(int i=0;i<list.size();i++) {
                Marker marker = new Marker();

//                marker.setPosition(new LatLng(Double.parseDouble(list.get(i).YPos), Double.parseDouble(list.get(i).XPos)));
                marker.setPosition(new LatLng(Double.parseDouble(list.get(i).getYpos()), Double.parseDouble(list.get(i).getXpos())));
                marker.setMap(naverMap);
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
            //updateMapMarkers();
        }
    }

//    // 현재 카메라가 보고있는 위치
//    public LatLng getCurrentPosition(NaverMap naverMap) {
//        CameraPosition cameraPosition = naverMap.getCameraPosition();
//        return new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
//    }
//
//    public final static double REFERANCE_LAT = 1 / 109.958489129649955;
//    public final static double REFERANCE_LNG = 1 / 88.74;
//    public final static double REFERANCE_LAT_X3 = 3 / 109.958489129649955;
//    public final static double REFERANCE_LNG_X3 = 3 / 88.74;
//    public boolean withinSightMarker(LatLng currentPosition, LatLng markerPosition) {
//        boolean withinSightMarkerLat = Math.abs(currentPosition.latitude - markerPosition.latitude) <= REFERANCE_LAT_X3;
//        boolean withinSightMarkerLng = Math.abs(currentPosition.longitude - markerPosition.longitude) <= REFERANCE_LNG_X3;
//        return withinSightMarkerLat && withinSightMarkerLng;
//    }
//
//    private void freeActiveMarkers() {
//        if (activeMarkers == null) {
//            activeMarkers = new Vector<Marker>();
//            return;
//        }
//        for (Marker activeMarker: activeMarkers) {
//            activeMarker.setMap(null);
//        }
//        activeMarkers = new Vector<Marker>();
//    }

    private void getXmlData(){
        StringBuffer buffer=new StringBuffer();

        String queryUrl="http://apis.data.go.kr/B551182/hospInfoService/getHospBasisList?" + "ServiceKey=" + mykey + "&clCd=31" + "&numOfRows=30"
                + "&dgsbjtCd=14" + "&xPos=" + Xpos + "&yPos=" + Ypos + "&radius=" + 1000;
        Log.d("TAG",queryUrl);  // 일단 test 용으로 피부과 코드 14 넣어서 피부과만 받음, 반경은 1.5km로 설정
        try{
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){

                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        list = new ArrayList<HospitalItem>();
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기
                        String test = xpp.getText();
                        if (tag.equals("item")) {
                            item = new HospitalItem();
                        }
                        else if (tag.equals("addr")) {
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

                        }  else if (tag.equals("yadmNm")) {
                            xpp.next();
                            item.setYadmNm(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");

                        } else if (tag.equals("ykiho")) {

                            xpp.next();
                            item.setYkiho(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");
                        }
                        break;


                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("item") && item != null){
                            Log.d("adapter_address_check", item.getAddr());
                            //Trial1
                            list.add(item);

                        }
                        break;
                }

                eventType= xpp.next();
            }
            Log.d("TAG", list.size() + "");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
