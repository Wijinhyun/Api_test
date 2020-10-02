package com.example.api_test;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.api_test.R;
import com.example.api_test.Region_listview;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button Btn_region;
    private Button Btn_search;

    private String city_name;
    private String gu_name;

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private LinearLayoutManager manager;

    ArrayList<HospitalItem> list = null;
    HospitalItem item = null;
    XmlPullParser xpp;
    private TextView Tv_result;
    private String mykey = "%2BHeQuB3%2FCasGAbmRnedYca%2B6ESWu%2FcHnzFBtykDvwHZZLfz0ZTTJ2mANSme5%2Blr1DgBnQ4WJnmLXPwxsatF3Pw%3D%3D";
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);



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

                        getXmlData();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if(list.isEmpty() == false || list.size() != 0) {
                                    Log.d("list_check", list.size() + "");
                                    adapter = new CustomAdapter(getApplicationContext(), list);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
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

        Btn_region.setOnClickListener(this);
        Btn_search.setOnClickListener(this);



    }

    private void getXmlData(){
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
        String queryUrl="http://apis.data.go.kr/B551182/hospInfoService/getHospBasisList?" + "ServiceKey=" + mykey + "&clCd=31" + "&numOfRows=20";
        if(sidoCd != null){
            queryUrl += "&sidoCd=" + sidoCd;
        }

        Log.d("TAG",queryUrl);
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

                        } else if (tag.equals("estbDd")) {
                            xpp.next();
                            item.setEstbDd(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");
                        } else if (tag.equals("gdrCnt")) {
                            xpp.next();
                            item.setGdrCnt(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");
                        } else if (tag.equals("hospUrl")) {

                            xpp.next();
                            item.setHospUrl(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");
                        } else if (tag.equals("intnCnt")) {

                            xpp.next();
                            item.setIntnCnt(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");
                        } else if (tag.equals("postNo")) {

                            xpp.next();
                            item.setPostNo(xpp.getText());
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");
                        } else if (tag.equals("resdntCnt")) {

                            xpp.next();
                            item.setResdntCnt(xpp.getText());
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

                        } else if (tag.equals("yadmNm")) {
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

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}