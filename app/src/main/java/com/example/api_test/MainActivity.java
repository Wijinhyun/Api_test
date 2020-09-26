package com.example.api_test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button Btn_region;
    private Button Btn_search;

    private String city_name;
    private String gu_name;


    XmlPullParser xpp;
    private TextView Tv_result;
    private String mykey = "%2BHeQuB3%2FCasGAbmRnedYca%2B6ESWu%2FcHnzFBtykDvwHZZLfz0ZTTJ2mANSme5%2Blr1DgBnQ4WJnmLXPwxsatF3Pw%3D%3D";
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        Intent intent = getIntent();
        city_name = intent.getStringExtra("city_name");
        gu_name = intent.getStringExtra("gu_name");
        if(city_name != null && gu_name != null){
            Btn_region.setText(city_name + " - " + gu_name);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_region:
                startActivity(new Intent(MainActivity.this, Region_listview.class));
                break;

            case R.id.btn_search:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data=getXmlData();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Tv_result.setText(data);
                            }
                        });
                    }
                }).start();
                break;
            default:
                break;
        }

    }


    private void init(){
        Btn_region = (Button) findViewById(R.id.btn_region);
        Btn_search = (Button) findViewById(R.id.btn_search);
        Tv_result = (TextView) findViewById(R.id.tv_result);

        Btn_region.setOnClickListener(this);
        Btn_search.setOnClickListener(this);



    }

    String getXmlData(){
        StringBuffer buffer=new StringBuffer();

        String sidoCd = null;

        if(city_name != null) {
            if (city_name.equals("서울")) {
                sidoCd = "110000";
            } else if (city_name.equals("경기")) {
                sidoCd = "310000";
            } else if (city_name.equals("부산")) {
                sidoCd = "210000";
            } else if (city_name.equals("인천")) {
                sidoCd = "220000";
            } else if (city_name.equals("대구")) {
                sidoCd = "230000";
            } else if (city_name.equals("대전")) {
                sidoCd = "250000";
            } else if (city_name.equals("광주")) {
                sidoCd = "240000";
            } else if (city_name.equals("울산")) {
                sidoCd = "260000";
            } else if (city_name.equals("경남")) {
                sidoCd = "380000";
            } else if (city_name.equals("경북")) {
                sidoCd = "370000";
            } else if (city_name.equals("전남")) {
                sidoCd = "360000";
            } else if (city_name.equals("전북")) {
                sidoCd = "350000";
            } else if (city_name.equals("충남")) {
                sidoCd = "340000";
            } else if (city_name.equals("충북")) {
                sidoCd = "330000";
            } else if (city_name.equals("강원")) {
                sidoCd = "320000";
            } else if (city_name.equals("세종")) {
                sidoCd = "410000";
            } else {
                sidoCd = "390000";
            }
        }
        String queryUrl="http://apis.data.go.kr/B551182/hospInfoService/getHospBasisList?" + "ServiceKey=" + mykey;
        if(sidoCd != null){
            queryUrl += "&sidoCd=" + sidoCd;
        }

        try{
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("addr")){
                            buffer.append("주소 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("clCdNm")){
                            buffer.append("병원종류 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("estbDd")){
                            buffer.append("설립일 :");
                            xpp.next();
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("gdrCnt")){
                            buffer.append("일반의 수 :");
                            xpp.next();
                            buffer.append(xpp.getText());//telephone 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("hospUrl")){
                            buffer.append("홈페이지 :");
                            xpp.next();
                            buffer.append(xpp.getText());//address 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("intnCnt")){
                            buffer.append("인턴 수 :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapx 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("postNo")){
                            buffer.append("우편번호 :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("resdntCnt")){
                            buffer.append("레지턴트 수 :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("sdrCnt")){
                            buffer.append("전문의 수 :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("telno")){
                            buffer.append("전화번호 :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("yadmNm")){
                            buffer.append("병원 이름 :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("ykiho")){
                            buffer.append("병원암호코드 :");
                            xpp.next();
                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        buffer.append("파싱 끝\n");
        return buffer.toString();//StringBuffer 문자열 객체 반환

    }
}