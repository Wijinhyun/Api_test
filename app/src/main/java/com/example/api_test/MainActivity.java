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
        String sgguCd = null;
        String queryUrl="http://apis.data.go.kr/B551182/hospInfoService/getHospBasisList?" + "ServiceKey=" + mykey + "&clCd=31" + "&numOfRows=20";
        if(city_name != null) {
            if (city_name.equals("서울")) {
                sidoCd = "110000";
                if(gu_name.equals("강남구")){
                    sgguCd = "110001";
                } else if(gu_name.equals("강동구")){
                    sgguCd = "110002";
                }else if(gu_name.equals("강서구")){
                    sgguCd = "110003";
                }else if(gu_name.equals("관악구")){
                    sgguCd = "110004";
                }else if(gu_name.equals("구로구")){
                    sgguCd = "110005";
                }else if(gu_name.equals("도봉구")){
                    sgguCd = "110006";
                }else if(gu_name.equals("동대문구")){
                    sgguCd = "110007";
                }else if(gu_name.equals("동작구")){
                    sgguCd = "110008";
                }else if(gu_name.equals("마포구")){
                    sgguCd = "110009";
                }else if(gu_name.equals("서대문구")){
                    sgguCd = "110010";
                }else if(gu_name.equals("성동구")){
                    sgguCd = "110011";
                }else if(gu_name.equals("성북구")){
                    sgguCd = "110012";
                }else if(gu_name.equals("영등포구")){
                    sgguCd = "110013";
                }else if(gu_name.equals("용산구")){
                    sgguCd = "110014";
                }else if(gu_name.equals("은평구")){
                    sgguCd = "110015";
                }else if(gu_name.equals("종로구")){
                    sgguCd = "110016";
                }else if(gu_name.equals("중구")){
                    sgguCd = "110017";
                }else if(gu_name.equals("송파구")){
                    sgguCd = "110018";
                }else if(gu_name.equals("중랑구")){
                    sgguCd = "110019";
                }else if(gu_name.equals("양천구")){
                    sgguCd = "110020";
                }else if(gu_name.equals("서초구")){
                    sgguCd = "110021";
                }else if(gu_name.equals("노원구")){
                    sgguCd = "110022";
                }else if(gu_name.equals("광진구")){
                    sgguCd = "110023";
                }else if(gu_name.equals("강북구")){
                    sgguCd = "110024";
                }else if(gu_name.equals("금천구")){
                    sgguCd = "110025";
                }

            } else if (city_name.equals("경기")) {
                sidoCd = "310000";
                if(gu_name.equals("가평군")){
                    sgguCd = "310001";
                } else if(gu_name.equals("고양시")){
                    sgguCd = "311901";
                    queryUrl += "&sgguCd=311902";
                    queryUrl += "&sgguCd=311903";
                }else if(gu_name.equals("과천시")){
                    sgguCd = "310900";
                }else if(gu_name.equals("광명시")){
                    sgguCd = "310100";
                }else if(gu_name.equals("광주시")){
                    sgguCd = "312600";
                }else if(gu_name.equals("구리시")){
                    sgguCd = "311000";
                }else if(gu_name.equals("군포시")){
                    sgguCd = "311400";
                }else if(gu_name.equals("김포시")){
                    sgguCd = "312300";
                }else if(gu_name.equals("남양주시")){
                    sgguCd = "311500";
                }else if(gu_name.equals("동두천시")){
                    sgguCd = "310200";
                }else if(gu_name.equals("부천시")){
                    sgguCd = "310300";
                    queryUrl += "&sgguCd=310301";
                    queryUrl += "&sgguCd=310302";
                }else if(gu_name.equals("성남시")){
                    sgguCd = "310401";
                    queryUrl += "&sgguCd=310402";
                    queryUrl += "&sgguCd=310403";
                }else if(gu_name.equals("수원시")){
                    sgguCd = "310601";
                    queryUrl += "&sgguCd=310602";
                    queryUrl += "&sgguCd=310603";
                    queryUrl += "&sgguCd=310604";
                }else if(gu_name.equals("시흥시")){
                    sgguCd = "311700";
                }else if(gu_name.equals("안산시")){
                    sgguCd = "311101";
                    queryUrl += "&sgguCd=311102";
                }else if(gu_name.equals("안성시")){
                    sgguCd = "312400";
                }else if(gu_name.equals("안양시")){
                    sgguCd = "310701";
                    queryUrl += "&sgguCd=310702";
                }else if(gu_name.equals("양주시")){
                    sgguCd = "312700";
                }else if(gu_name.equals("양평군")){
                    sgguCd = "310009";
                }else if(gu_name.equals("여주시")){
                    sgguCd = "312900";
                }else if(gu_name.equals("연천군")){
                    sgguCd = "310011";
                }else if(gu_name.equals("오산시")){
                    sgguCd = "311800";
                }else if(gu_name.equals("용인시")){
                    sgguCd = "312001";
                    queryUrl += "&sgguCd=312002";
                    queryUrl += "&sgguCd=312003";
                }else if(gu_name.equals("의왕시")){
                    sgguCd = "311600";
                }else if(gu_name.equals("의정부시")){
                    sgguCd = "310800";
                }else if(gu_name.equals("이천시")){
                    sgguCd = "312100";
                }else if(gu_name.equals("파주시")){
                    sgguCd = "312200";
                }else if(gu_name.equals("평택시")){
                    sgguCd = "311200";
                }else if(gu_name.equals("포천시")){
                    sgguCd = "312800";
                }else if(gu_name.equals("하남시")){
                    sgguCd = "311300";
                }else if(gu_name.equals("화성시")){
                    sgguCd = "312500";
                }
            } else if (city_name.equals("부산")) {
                sidoCd = "210000";
                if(gu_name.equals("강서구")){
                    sgguCd = "210012";
                } else if(gu_name.equals("금정구")){
                    sgguCd = "210011";
                }else if(gu_name.equals("기장군")){
                    sgguCd = "210100";
                }else if(gu_name.equals("남구")){
                    sgguCd = "210001";
                }else if(gu_name.equals("동구")){
                    sgguCd = "210002";
                }else if(gu_name.equals("동래구")){
                    sgguCd = "210003";
                }else if(gu_name.equals("부산진구")){
                    sgguCd = "210004";
                }else if(gu_name.equals("북구")){
                    sgguCd = "210005";
                }else if(gu_name.equals("시상구")){
                    sgguCd = "210015";
                }else if(gu_name.equals("시하구")){
                    sgguCd = "210010";
                }else if(gu_name.equals("서구")){
                    sgguCd = "210006";
                }else if(gu_name.equals("수영구")){
                    sgguCd = "210014";
                }else if(gu_name.equals("연제구")){
                    sgguCd = "210013";
                }else if(gu_name.equals("중구")){
                    sgguCd = "210008";
                }else if(gu_name.equals("해운대구")){
                    sgguCd = "210009";
                }
            } else if (city_name.equals("인천")) {
                sidoCd = "220000";
                if(gu_name.equals("강화군")){
                    sgguCd = "220100";
                } else if(gu_name.equals("계양구")){
                    sgguCd = "220008";
                }else if(gu_name.equals("미추홀구")){
                    sgguCd = "220001";
                }else if(gu_name.equals("남동구")){
                    sgguCd = "220006";
                }else if(gu_name.equals("동구")){
                    sgguCd = "220002";
                }else if(gu_name.equals("부평구")){
                    sgguCd = "220003";
                }else if(gu_name.equals("서구")){
                    sgguCd = "220005";
                }else if(gu_name.equals("연수구")){
                    sgguCd = "220007";
                }else if(gu_name.equals("옹진군")){
                    sgguCd = "220200";
                }else if(gu_name.equals("중구")){
                    sgguCd = "220004";
                }
            } else if (city_name.equals("대구")) {
                sidoCd = "230000";
                if(gu_name.equals("남구")){
                    sgguCd = "230001";
                } else if(gu_name.equals("달서구")){
                    sgguCd = "230007";
                }else if(gu_name.equals("달성군")){
                    sgguCd = "230100";
                }else if(gu_name.equals("동구")){
                    sgguCd = "230002";
                }else if(gu_name.equals("북구")){
                    sgguCd = "230003";
                }else if(gu_name.equals("서구")){
                    sgguCd = "230004";
                }else if(gu_name.equals("수성구")){
                    sgguCd = "230005";
                }else if(gu_name.equals("중구")){
                    sgguCd = "230006";
                }
            } else if (city_name.equals("대전")) {
                sidoCd = "250000";
                if(gu_name.equals("대덕구")){
                    sgguCd = "250002";
                } else if(gu_name.equals("동구")){
                    sgguCd = "250004";
                }else if(gu_name.equals("서구")){
                    sgguCd = "250003";
                }else if(gu_name.equals("유성구")){
                    sgguCd = "250001";
                }else if(gu_name.equals("중구")){
                    sgguCd = "250005";
                }
            } else if (city_name.equals("광주")) {
                sidoCd = "240000";
                if(gu_name.equals("광산구")){
                    sgguCd = "240004";
                } else if(gu_name.equals("남구")){
                    sgguCd = "240005";
                }else if(gu_name.equals("동구")){
                    sgguCd = "240001";
                }else if(gu_name.equals("북구")){
                    sgguCd = "240002";
                }else if(gu_name.equals("서구")){
                    sgguCd = "240003";
                }
            } else if (city_name.equals("울산")) {
                sidoCd = "260000";
                if(gu_name.equals("중구")){
                    sgguCd = "260003";
                } else if(gu_name.equals("남구")){
                    sgguCd = "260001";
                }else if(gu_name.equals("동구")){
                    sgguCd = "260002";
                }else if(gu_name.equals("북구")){
                    sgguCd = "260004";
                }else if(gu_name.equals("울주군")){
                    sgguCd = "260100";
                }
            } else if (city_name.equals("경남")) {
                sidoCd = "380000";
                if(gu_name.equals("거제시")){
                    sgguCd = "381000";
                } else if(gu_name.equals("거창군")){
                    sgguCd = "380002";
                }else if(gu_name.equals("고성군")){
                    sgguCd = "380003";
                }else if(gu_name.equals("김해시")){
                    sgguCd = "380100";
                }else if(gu_name.equals("남해군")){
                    sgguCd = "380701";
                }else if(gu_name.equals("마산시")){
                    sgguCd = "260100";
                    queryUrl += "&sgguCd=380702";
                }else if(gu_name.equals("밀양시")){
                    sgguCd = "380900";
                }else if(gu_name.equals("사천시")){
                    sgguCd = "380300";
                }else if(gu_name.equals("산청군")){
                    sgguCd = "380008";
                }else if(gu_name.equals("양산시")){
                    sgguCd = "381100";
                }else if(gu_name.equals("의령군")){
                    sgguCd = "380011";
                }else if(gu_name.equals("진주시")){
                    sgguCd = "380500";
                }else if(gu_name.equals("진해시")){
                    sgguCd = "380703";
                }else if(gu_name.equals("창녕군")){
                    sgguCd = "380014";
                }else if(gu_name.equals("창원시")){
                    sgguCd = "380701";
                    queryUrl += "&sgguCd=380702";
                    queryUrl += "&sgguCd=380703";
                    queryUrl += "&sgguCd=380704";
                    queryUrl += "&sgguCd=380705";
                }else if(gu_name.equals("통영시")){
                    sgguCd = "380800";
                }else if(gu_name.equals("하동군")){
                    sgguCd = "380016";
                }else if(gu_name.equals("함안군")){
                    sgguCd = "380017";
                }else if(gu_name.equals("함양군")){
                    sgguCd = "380018";
                }else if(gu_name.equals("합천군")){
                    sgguCd = "380019";
                }
            } else if (city_name.equals("경북")) {
                sidoCd = "370000";
                if(gu_name.equals("경산시")){
                    sgguCd = "371000";
                } else if(gu_name.equals("경주시")){
                    sgguCd = "370100";
                }else if(gu_name.equals("고령군")){
                    sgguCd = "370002";
                }else if(gu_name.equals("구미시")){
                    sgguCd = "370200";
                }else if(gu_name.equals("군위군")){
                    sgguCd = "370003";
                }else if(gu_name.equals("김천시")){
                    sgguCd = "370300";
                }else if(gu_name.equals("문경시")){
                    sgguCd = "370800";
                }else if(gu_name.equals("봉화군")){
                    sgguCd = "370007";
                }else if(gu_name.equals("상주시")){
                    sgguCd = "370900";
                }else if(gu_name.equals("성주군")){
                    sgguCd = "370010";
                }else if(gu_name.equals("안동시")){
                    sgguCd = "370400";
                }else if(gu_name.equals("영덕군")){
                    sgguCd = "370012";
                }else if(gu_name.equals("영양군")){
                    sgguCd = "370013";
                }else if(gu_name.equals("영주시")){
                    sgguCd = "370500";
                }else if(gu_name.equals("영천시")){
                    sgguCd = "370600";
                }else if(gu_name.equals("예천군")){
                    sgguCd = "370017";
                }else if(gu_name.equals("울릉군")){
                    sgguCd = "370018";
                }else if(gu_name.equals("울진군")){
                    sgguCd = "370019";
                }else if(gu_name.equals("의성군")){
                    sgguCd = "370021";
                }else if(gu_name.equals("청도군")){
                    sgguCd = "370022";
                }else if(gu_name.equals("청송군")){
                    sgguCd = "370023";
                }else if(gu_name.equals("칠곡군")){
                    sgguCd = "370024";
                }else if(gu_name.equals("포항시")){
                    sgguCd = "370701";
                    queryUrl += "&sgguCd=370702";
                }
            } else if (city_name.equals("전남")) {
                sidoCd = "360000";
                if(gu_name.equals("강진군")){
                    sgguCd = "360001";
                } else if(gu_name.equals("고흥군")){
                    sgguCd = "360002";
                }else if(gu_name.equals("곡성군")){
                    sgguCd = "360003";
                }else if(gu_name.equals("광양시")){
                    sgguCd = "360700";
                }else if(gu_name.equals("구례군")){
                    sgguCd = "360006";
                }else if(gu_name.equals("나주시")){
                    sgguCd = "360200";
                }else if(gu_name.equals("담양군")){
                    sgguCd = "360008";
                }else if(gu_name.equals("목포시")){
                    sgguCd = "360300";
                }else if(gu_name.equals("무안군")){
                    sgguCd = "360009";
                }else if(gu_name.equals("보성군")){
                    sgguCd = "360010";
                }else if(gu_name.equals("순천시")){
                    sgguCd = "360400";
                }else if(gu_name.equals("신안군")){
                    sgguCd = "360012";
                }else if(gu_name.equals("여수시")){
                    sgguCd = "360500";
                }else if(gu_name.equals("영광군")){
                    sgguCd = "360014";
                }else if(gu_name.equals("영암군")){
                    sgguCd = "360015";
                }else if(gu_name.equals("완도군")){
                    sgguCd = "360016";
                }else if(gu_name.equals("장성군")){
                    sgguCd = "360017";
                }else if(gu_name.equals("장흥군")){
                    sgguCd = "360018";
                }else if(gu_name.equals("진도군")){
                    sgguCd = "360019";
                }else if(gu_name.equals("함평군")){
                    sgguCd = "360020";
                }else if(gu_name.equals("해남군")){
                    sgguCd = "360021";
                }else if(gu_name.equals("화순군")) {
                    sgguCd = "360022";
                }
            } else if (city_name.equals("전북")) {
                sidoCd = "350000";
                if(gu_name.equals("고창군")){
                    sgguCd = "350001";
                } else if(gu_name.equals("군산시")){
                    sgguCd = "350100";
                }else if(gu_name.equals("김제시")){
                    sgguCd = "350600";
                }else if(gu_name.equals("남원시")){
                    sgguCd = "350200";
                }else if(gu_name.equals("무주군")){
                    sgguCd = "350004";
                }else if(gu_name.equals("부안군")){
                    sgguCd = "350005";
                }else if(gu_name.equals("순창군")){
                    sgguCd = "350006";
                }else if(gu_name.equals("완주군")){
                    sgguCd = "350008";
                }else if(gu_name.equals("익산시")){
                    sgguCd = "350300";
                }else if(gu_name.equals("임실군")){
                    sgguCd = "350010";
                }else if(gu_name.equals("장수군")){
                    sgguCd = "350011";
                }else if(gu_name.equals("전주시")){
                    sgguCd = "350401";
                    queryUrl += "&sgguCd=350402";
                }else if(gu_name.equals("정읍시")){
                    sgguCd = "350500";
                }else if(gu_name.equals("진안군")){
                    sgguCd = "350013";
                }
            } else if (city_name.equals("충남")) {
                sidoCd = "340000";
                if(gu_name.equals("공주시")){
                    sgguCd = "340300";
                } else if(gu_name.equals("금산군")){
                    sgguCd = "340002";
                }else if(gu_name.equals("논산시")){
                    sgguCd = "340700";
                }else if(gu_name.equals("당진시")){
                    sgguCd = "340900";
                }else if(gu_name.equals("보령시")){
                    sgguCd = "340400";
                }else if(gu_name.equals("부여군")){
                    sgguCd = "340007";
                }else if(gu_name.equals("서산시")){
                    sgguCd = "340600";
                }else if(gu_name.equals("서천군")){
                    sgguCd = "340009";
                }else if(gu_name.equals("아산시")){
                    sgguCd = "340500";
                }else if(gu_name.equals("예산군")){
                    sgguCd = "340012";
                }else if(gu_name.equals("천안시")){
                    sgguCd = "340201";
                    queryUrl += "&sgguCd=340202";
                }else if(gu_name.equals("청양군")){
                    sgguCd = "340014";
                }else if(gu_name.equals("태안군")){
                    sgguCd = "340016";
                }else if(gu_name.equals("홍성군")){
                    sgguCd = "340015";
                }else if(gu_name.equals("계룡시")){
                    sgguCd = "340800";
                }
            } else if (city_name.equals("충북")) {
                sidoCd = "330000";
                if(gu_name.equals("괴산군")){
                    sgguCd = "330001";
                } else if(gu_name.equals("단양군")){
                    sgguCd = "330002";
                }else if(gu_name.equals("보은군")){
                    sgguCd = "330003";
                }else if(gu_name.equals("영동군")){
                    sgguCd = "330004";
                }else if(gu_name.equals("옥천군")){
                    sgguCd = "330005";
                }else if(gu_name.equals("음성군")){
                    sgguCd = "330006";
                }else if(gu_name.equals("제천시")){
                    sgguCd = "330300";
                }else if(gu_name.equals("진천군")){
                    sgguCd = "330009";
                }else if(gu_name.equals("청주시")){
                    sgguCd = "330101";
                    queryUrl += "&sgguCd=330102";
                    queryUrl += "&sgguCd=330103";
                    queryUrl += "&sgguCd=330104";
                }else if(gu_name.equals("충주시")){
                    sgguCd = "330200";
                }else if(gu_name.equals("증평군")){
                    sgguCd = "330011";
                }
            } else if (city_name.equals("강원")) {
                sidoCd = "320000";
                if(gu_name.equals("강릉시")){
                    sgguCd = "320100";
                } else if(gu_name.equals("고성군")){
                    sgguCd = "320001";
                }else if(gu_name.equals("동해시")){
                    sgguCd = "320200";
                }else if(gu_name.equals("삼척시")){
                    sgguCd = "320700";
                }else if(gu_name.equals("속초시")){
                    sgguCd = "320300";
                }else if(gu_name.equals("양구군")){
                    sgguCd = "320004";
                }else if(gu_name.equals("양양군")){
                    sgguCd = "320005";
                }else if(gu_name.equals("영월군")){
                    sgguCd = "320006";
                }else if(gu_name.equals("원주시")){
                    sgguCd = "320400";
                }else if(gu_name.equals("인제군")){
                    sgguCd = "320008";
                }else if(gu_name.equals("정선군")){
                    sgguCd = "320009";
                }else if(gu_name.equals("철원군")){
                    sgguCd = "320010";
                }else if(gu_name.equals("춘천시")){
                    sgguCd = "320500";
                }else if(gu_name.equals("태백시")){
                    sgguCd = "320600";
                }else if(gu_name.equals("평창군")){
                    sgguCd = "320012";
                }else if(gu_name.equals("홍천군")){
                    sgguCd = "320013";
                }else if(gu_name.equals("화천군")){
                    sgguCd = "320014";
                }else if(gu_name.equals("횡성군")){
                    sgguCd = "320015";
                }
            } else if (city_name.equals("세종")) {
                sidoCd = "410000";
            } else {
                sidoCd = "390000";
                if(gu_name.equals("서귀포시")){
                    sgguCd = "390100";
                } else if(gu_name.equals("제주시")){
                    sgguCd = "390200";
                }
            }
        }

        if(sidoCd != null){
            queryUrl += "&sidoCd=" + sidoCd;
        }
        if(sgguCd != null){
            queryUrl += "&sgguCd=" + sgguCd;
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
            Log.d("TAG", list.size() + "");
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}