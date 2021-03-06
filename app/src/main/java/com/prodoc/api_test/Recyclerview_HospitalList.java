package com.prodoc.api_test;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Recyclerview_HospitalList extends AppCompatActivity implements View.OnClickListener {

    private static final int NORMAL_HOSPITAL_RANGE = 25;
    private static final int PHARMACY_RANGE = 30;
    private static final int DENTIST_RANGE = 61;
    private static final int ORIENTAL_HOSPITAL_RANGE = 87;

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private LinearLayoutManager manager;

    private ArrayList<HospitalItem> list = null;
    private HospitalItem item = null;

    private String city_name;
    private String city_name_for_text;
    private String gu_name;
    ArrayList<String> jagosipda = null;

    private String MedicalsubCd;
    private String search;
    private String subject;

    private double latitude;
    private double longitude;

    private FloatingActionButton Fb_tomap, Fb_totop;
    private Button Btn_region_in_list, Btn_medical_subject, Btn_search;

    private ImageView Btn_back, Recycler_filter, Filter_ratio_iv, Filter_total_iv, Filter_distance_iv;
    private View Hide_filter_view;
    private Toolbar Hide_filter_toolbar;
    private LinearLayout Filter_ratio, Filter_total, Filter_distance;
    private int filtering_num = 0;

    private TextView Tv_cnt, Tv_con_titles;
    private LinearLayout base_progressBar;

    ArrayList<HospitalItemForCsv> arr = null;
    String sidoCd = null;
    String sgguCd = null;

    private GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview__hospital_list);

        init();

        Intent intent = getIntent();
        city_name = intent.getStringExtra("city_name");
        gu_name = intent.getStringExtra("gu_name");
        MedicalsubCd = intent.getStringExtra("MedicalsubCd");
        search = intent.getStringExtra("search");

        gps = new GPSTracker(Recyclerview_HospitalList.this);

        if(gps.canGetLocation()) {

            getpermisson();

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            if(latitude == 0 || longitude == 0){
                Toast.makeText(getApplicationContext(), "GPS 활용 거부로 인해 초기위치값이 경북대로 설정되었습니다", Toast.LENGTH_LONG).show();
                latitude = 35.887515;
                longitude = 128.611553;
            }

        } else {
            gps.showSettingsAlert();
        }

        if(Integer.parseInt(MedicalsubCd) <= NORMAL_HOSPITAL_RANGE) {
            Tv_con_titles.setText("의원");
            switch (MedicalsubCd) {
                case "01":
                    Btn_medical_subject.setText(" 내과 ");
                    subject = "내과";
                    break;
                case "02":
                    Btn_medical_subject.setText(" 신경과 ");
                    subject = "신경과";
                    break;
                case "03":
                    Btn_medical_subject.setText(" 정신건강의학과 ");
                    subject = "정신건강의학과";
                    break;
                case "04":
                    Btn_medical_subject.setText("외과");
                    subject = "외과";
                    break;
                case "05":
                    Btn_medical_subject.setText("정형외과");
                    subject = "정형외과";
                    break;
                case "06":
                    Btn_medical_subject.setText("신경외과");
                    subject = "신경외과";
                    break;
                case "07":
                    Btn_medical_subject.setText("흉부외과");
                    subject = "흉부외과";
                    break;
                case "08":
                    Btn_medical_subject.setText("성형외과");
                    subject = "성형외과";
                    break;
                case "09":
                    Btn_medical_subject.setText("마취통증의학과");
                    subject = "마취통증의학과";
                    break;
                case "10":
                    Btn_medical_subject.setText("산부인과");
                    subject = "산부인과";
                    break;
                case "11":
                    Btn_medical_subject.setText("소아청소년과");
                    subject = "소아청소년과";
                    break;
                case "12":
                    Btn_medical_subject.setText("안과");
                    subject = "안과";
                    break;
                case "13":
                    Btn_medical_subject.setText("이비인후과");
                    subject = "이비인후과";
                    break;
                case "14":
                    Btn_medical_subject.setText("피부과");
                    subject = "피부과";
                    break;
                case "15":
                    Btn_medical_subject.setText("비뇨기의학과");
                    subject = "비뇨기의학과";
                    break;
                case "16":
                    Btn_medical_subject.setText("영상의학과");
                    subject = "영상의학과";
                    break;
                case "17":
                    Btn_medical_subject.setText("방사선종양학과");
                    subject = "방사선종양학과";
                    break;
                case "19":
                    Btn_medical_subject.setText("진단검사의학과");
                    subject = "진단검사의학과";
                    break;
                case "20":
                    Btn_medical_subject.setText("결핵과");
                    subject = "결핵과";
                    break;
                case "21":
                    Btn_medical_subject.setText("재활의학과");
                    subject = "재활의학과";
                    break;
                case "22":
                    Btn_medical_subject.setText("핵의학과");
                    subject = "핵의학과";
                    break;
                case "23":
                    Btn_medical_subject.setText("가정의학과");
                    subject = "가정의학과";
                    break;
                case "24":
                    Btn_medical_subject.setText("응급의학과");
                    subject = "응급의학과";
                    break;
                case "25":
                    Btn_medical_subject.setText("직업환경의학과");
                    subject = "직업환경의학과";
                    break;
            }
        }else if(Integer.parseInt(MedicalsubCd) <= PHARMACY_RANGE){
            Tv_con_titles.setText("약국");
            if(MedicalsubCd.equals("30")) {
                Btn_medical_subject.setText("약국");
                subject = "약국";
            }
        }else if(Integer.parseInt(MedicalsubCd) <= DENTIST_RANGE){
            Tv_con_titles.setText("치과의원");
            switch (MedicalsubCd) {
                case "50":
                    Btn_medical_subject.setText("구강악안면외과");
                    subject = "구강악안면외과";
                    break;
                case "51":
                    Btn_medical_subject.setText("치과보철과");
                    subject = "치과보철과";
                    break;
                case "52":
                    Btn_medical_subject.setText("치과교정과");
                    subject = "치과교정과";
                    break;
                case "53":
                    Btn_medical_subject.setText("소아치과");
                    subject = "소아치과";
                    break;
                case "54":
                    Btn_medical_subject.setText("치주과");
                    subject = "치주과";
                    break;
                case "55":
                    Btn_medical_subject.setText("치과보존과");
                    subject = "치과보존과";
                    break;
                case "56":
                    Btn_medical_subject.setText("구강내과");
                    subject = "구강내과";
                    break;
                case "57":
                    Btn_medical_subject.setText("영상치의학과");
                    subject = "영상치의학과";
                    break;
                case "58":
                    Btn_medical_subject.setText("구강병리과");
                    subject = "구강병리과";
                    break;
                case "59":
                    Btn_medical_subject.setText("예방치과");
                    subject = "예방치과";
                    break;
                case "61":
                    Btn_medical_subject.setText("통합치의학과");
                    subject = "통합치의학과";
                    break;
            }
        }else if(Integer.parseInt(MedicalsubCd) <= ORIENTAL_HOSPITAL_RANGE) {
            Tv_con_titles.setText("한의원");
            switch (MedicalsubCd) {
                case "80":
                    Btn_medical_subject.setText("한방내과");
                    subject = "한방내과";
                    break;
                case "81":
                    Btn_medical_subject.setText("한방부인과");
                    subject = "한방부인과";
                    break;
                case "82":
                    Btn_medical_subject.setText("한방소아과");
                    subject = "한방소아과";
                    break;
                case "83":
                    Btn_medical_subject.setText("한방안·이비인후·피부과");
                    subject = "한방안·이비인후·피부과";
                    break;
                case "84":
                    Btn_medical_subject.setText("한방신경정신과");
                    subject = "한방신경정신과";
                    break;
                case "85":
                    Btn_medical_subject.setText("침구과");
                    subject = "침구과";
                    break;
                case "86":
                    Btn_medical_subject.setText("한방재활의학과");
                    subject = "한방재활의학과";
                    break;
                case "87":
                    Btn_medical_subject.setText("사상체질과");
                    subject = "사상체질과";
                    break;
            }
        }

        if(city_name == null || gu_name == null){
            city_name = "대구광역시";
            gu_name = "북구";
        }

        if(city_name.length() == 4){
            city_name_for_text = "" + city_name.charAt(0) + city_name.charAt(2);
        }else{
            city_name_for_text = city_name.substring(0,2);
        }

        if (city_name != null && gu_name != null) {
            Btn_region_in_list.setText(" " + city_name_for_text + " - " + gu_name + " ");
        }
        if(search != null){
            Btn_search.setText(" 검색어 : " + search + " ");
        }

        renewlist();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItem = manager.findFirstVisibleItemPosition();

                if (firstVisibleItem > 1) {
                    Fb_totop.setVisibility(View.VISIBLE);
                }
                else{
                    Fb_totop.setVisibility(View.GONE);
                }
            }
        });


        recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getApplicationContext(), recyclerView,
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        final Intent intent = new Intent(getApplicationContext(),ListDialogActivity.class);
                        intent.putExtra("percent",arr.get(position).getPercent());
                        intent.putExtra("hospitalname",arr.get(position).getHospitalname());
                        intent.putExtra("distance",arr.get(position).getDistance());
                        intent.putExtra("addr",arr.get(position).getAddr());
                        intent.putExtra("sbj",arr.get(position).getSubject());
                        intent.putExtra("pronum",arr.get(position).getPronum());
                        intent.putExtra("totalnum", arr.get(position).getTotalnum());
                        intent.putExtra("tel",arr.get(position).getTel());
                        intent.putExtra("park",arr.get(position).getPark());
                        intent.putExtra("url",arr.get(position).getUrl());
                        intent.putExtra("init_ypos",latitude);
                        intent.putExtra("init_xpos",longitude);
                        intent.putExtra("d_ypos",arr.get(position).getYpos());
                        intent.putExtra("d_xpos",arr.get(position).getXpos());
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }

                    @Override
                    public void onItemLongClick(View v, int position) {

                    }
                }));

    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerview);
        manager = new LinearLayoutManager(Recyclerview_HospitalList.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        base_progressBar = findViewById(R.id.base_progressBar);

        Btn_back = findViewById(R.id.btn_back);
        Btn_back.setOnClickListener(this);
        Recycler_filter = findViewById(R.id.recycler_filter);
        Recycler_filter.setOnClickListener(this);
        Hide_filter_view = findViewById(R.id.hide_filter_view);
        Hide_filter_toolbar = findViewById(R.id.hide_filter_toolbar);
        Filter_ratio = findViewById(R.id.filter_ratio);
        Filter_ratio.setOnClickListener(this);
        Filter_total = findViewById(R.id.filter_total);
        Filter_total.setOnClickListener(this);
        Filter_distance = findViewById(R.id.filter_distance);
        Filter_distance.setOnClickListener(this);
        Filter_ratio_iv = findViewById(R.id.filter_ratio_iv);
        Filter_total_iv = findViewById(R.id.filter_total_iv);
        Filter_distance_iv = findViewById(R.id.filter_distance_iv);

        Btn_search = findViewById(R.id.btn_search);
        Btn_search.setOnClickListener(this);

        Tv_con_titles = findViewById(R.id.con_titles_forlist);
        Tv_cnt = findViewById(R.id.tv_cnt);
        Fb_tomap = findViewById(R.id.fb_tomap);
        Fb_totop = findViewById(R.id.fb_totop);
        Btn_region_in_list = findViewById(R.id.btn_region_in_list);
        Btn_medical_subject = findViewById(R.id.btn_medical_subject);

        Fb_tomap.setOnClickListener(this);
        Fb_totop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(manager.findFirstVisibleItemPosition() > 10) {
                    recyclerView.scrollToPosition(10);
                }
                recyclerView.smoothScrollToPosition(0);
            }
        });
        Btn_region_in_list.setOnClickListener(this);
        Btn_medical_subject.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_tomap:
                Intent intent1 = new Intent(getApplicationContext(), MapActivityGooglemap.class);
                intent1.putExtra("MedicalsubCd", MedicalsubCd);
                intent1.putExtra("city_name", city_name);
                intent1.putExtra("gu_name", gu_name);
                intent1.putExtra("search", search);
                intent1.putExtra("subject", subject);
                startActivity(intent1);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.btn_region_in_list:
                Intent intent = new Intent(getApplicationContext(), Region_listview.class);
                intent.putExtra("Imfrom", "recyclerview");
                intent.putExtra("MedicalsubCd", MedicalsubCd);
                intent.putExtra("search",search);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.btn_back:
                onBackPressed();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.recycler_filter:
                if(Hide_filter_toolbar.getVisibility() == View.GONE){
                    Hide_filter_view.setVisibility(View.VISIBLE);
                    Hide_filter_toolbar.setVisibility(View.VISIBLE);
                }else{
                    Hide_filter_view.setVisibility(View.GONE);
                    Hide_filter_toolbar.setVisibility(View.GONE);
                }
                break;
            case R.id.filter_ratio:
                filtering_num = 1;
                Filter_ratio_iv.setImageDrawable(getResources().getDrawable(R.drawable.icon_check));
                Filter_total_iv.setImageDrawable(getResources().getDrawable(R.drawable.icon_uncheck));
                Filter_distance_iv.setImageDrawable(getResources().getDrawable(R.drawable.icon_uncheck));
                Log.d("filter_ratio", "");
                Collections.sort(arr,new Filtering_for_ratio());
                Tv_cnt.setText(arr.size()+ "개의 의원을 전문의 비율순으로 나열했어요");
                adapter.notifyDataSetChanged();
                Hide_filter_view.setVisibility(View.GONE);
                Hide_filter_toolbar.setVisibility(View.GONE);
                break;
            case R.id.filter_total:
                filtering_num = 2;
                Filter_ratio_iv.setImageDrawable(getResources().getDrawable(R.drawable.icon_uncheck));
                Filter_total_iv.setImageDrawable(getResources().getDrawable(R.drawable.icon_check));
                Filter_distance_iv.setImageDrawable(getResources().getDrawable(R.drawable.icon_uncheck));
                Log.d("filter_total", "");
                Collections.sort(arr,new Filtering_for_total());
                Tv_cnt.setText(arr.size()+ "개의 의원을 전문의 인원순으로 나열했어요");
                adapter.notifyDataSetChanged();
                Hide_filter_view.setVisibility(View.GONE);
                Hide_filter_toolbar.setVisibility(View.GONE);
                break;
            case R.id.filter_distance:
                filtering_num = 3;
                Filter_ratio_iv.setImageDrawable(getResources().getDrawable(R.drawable.icon_uncheck));
                Filter_total_iv.setImageDrawable(getResources().getDrawable(R.drawable.icon_uncheck));
                Filter_distance_iv.setImageDrawable(getResources().getDrawable(R.drawable.icon_check));
                Log.d("filter_distance", "");
                Collections.sort(arr,new Filtering_for_distance());
                Tv_cnt.setText(arr.size()+ "개의 의원을 가까운 거리순으로 나열했어요");
                adapter.notifyDataSetChanged();
                Hide_filter_view.setVisibility(View.GONE);
                Hide_filter_toolbar.setVisibility(View.GONE);
                break;
            case R.id.btn_medical_subject:
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
                intent2.putExtra("Imfrom", "recyclerview");
                intent2.putExtra("city_name", city_name);
                intent2.putExtra("gu_name", gu_name);
                intent2.putExtra("search",search);
                startActivity(intent2);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.btn_search:
                Intent intent3 = new Intent(getApplicationContext(), Search_btn.class);
                intent3.putExtra("Imfrom", "recyclerview");
                intent3.putExtra("MedicalsubCd", MedicalsubCd);
                intent3.putExtra("city_name", city_name);
                intent3.putExtra("gu_name", gu_name);
                startActivity(intent3);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            default:

                break;
        }
    }

    private void renewlist(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                arr = new ArrayList<>();
                getcode();
                readDataFromCsv();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(arr.isEmpty() == false || arr.size() != 0){
                            Collections.sort(arr,new Filtering_for_ganada());
                            Tv_cnt.setText(arr.size()+ "개의 의원을 나열했어요");
                            adapter = new CustomAdapter(getApplicationContext(), arr, subject);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else if(arr.size() == 0){
                            Tv_cnt.setText("검색 결과가 없어요.");
                        }
                        base_progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    public void readDataFromCsv() {
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


        CSVReader reader = new CSVReader(is);
        List<String[]> list = null;
        try {
            list = reader.readAll();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
            int distance = calculateDistanceInKilometer(latitude,longitude,lat,lng);
            entity.setDistance(String.format("%d",distance));


            if(jagosipda.isEmpty() == false || jagosipda.size() != 0){  // 경기 수원시 같은 사례의 경우 모든 구를 포함해야 하는데, 이런 경우 자고십다가 활성화됨
                for(int i=0;i<jagosipda.size();i++){
                    if(jagosipda.get(i).equals(entity.getSigungucode())){
                        if(search != null){
                            if(entity.getHospitalname().contains(search)){  // 검색어가 포함된 경우
                                arr.add(entity);
                            }
                            else{   // 검색어가 포함되지 않은 경우

                            }
                        } else{     // 검색어가 없는 경우
                            arr.add(entity);
                        }
                    }
                }
            }else if(city_name == null && gu_name == null){ // 지역 설정 안한 상태라면
                if(search != null){
                    if(entity.getHospitalname().contains(search)){  // 검색어가 포함된 경우
                        arr.add(entity);
                    }
                    else{   // 검색어가 포함되지 않은 경우
                    }
                } else{     // 검색어가 없는 경우
                    arr.add(entity);
                }
            }else if(gu_name.equals("전체")){
                if(city_name != null && sidoCd.equals(entity.getSidocode())){
                    if(search != null){
                        if(entity.getHospitalname().contains(search)){  // 검색어가 포함된 경우
                            arr.add(entity);
                        }
                        else{   // 검색어가 포함되지 않은 경우

                        }
                    } else{     // 검색어가 없는 경우
                        arr.add(entity);
                    }
                }
            }else if(gu_name != null){
                Log.d("뭐가 문제일까",  "gu_name = " + gu_name);
                Log.d("뭐가 문제일까", "sgguCd = " + sgguCd + " entity.get = " + entity.getSigungucode());
                if(sgguCd.equals(entity.getSigungucode())){
                    if(search != null){
                        if(entity.getHospitalname().contains(search)){  // 검색어가 포함된 경우
                            arr.add(entity);
                        }
                        else {   // 검색어가 포함되지 않은 경우

                        }
                    } else {     // 검색어가 없는 경우
                        arr.add(entity);
                    }
                }
                // 인천부평구, 대구중구, 경기수원영통구 같은 사례들 걸러줘야함
            }

//            if(panjung <= 10000){
//                arr.add(entity);
//            }

        }
        Log.d("after_for_clear", arr.size() + "");
    }

    private void getcode(){

        jagosipda = new ArrayList<>();

        if (city_name != null) {
            if (city_name.equals("서울특별시")) {
                sidoCd = "110000";
                if (gu_name.equals("강남구")) {
                    sgguCd = "110001";
                } else if (gu_name.equals("강동구")) {
                    sgguCd = "110002";
                } else if (gu_name.equals("강서구")) {
                    sgguCd = "110003";
                } else if (gu_name.equals("관악구")) {
                    sgguCd = "110004";
                } else if (gu_name.equals("구로구")) {
                    sgguCd = "110005";
                } else if (gu_name.equals("도봉구")) {
                    sgguCd = "110006";
                } else if (gu_name.equals("동대문구")) {
                    sgguCd = "110007";
                } else if (gu_name.equals("동작구")) {
                    sgguCd = "110008";
                } else if (gu_name.equals("마포구")) {
                    sgguCd = "110009";
                } else if (gu_name.equals("서대문구")) {
                    sgguCd = "110010";
                } else if (gu_name.equals("성동구")) {
                    sgguCd = "110011";
                } else if (gu_name.equals("성북구")) {
                    sgguCd = "110012";
                } else if (gu_name.equals("영등포구")) {
                    sgguCd = "110013";
                } else if (gu_name.equals("용산구")) {
                    sgguCd = "110014";
                } else if (gu_name.equals("은평구")) {
                    sgguCd = "110015";
                } else if (gu_name.equals("종로구")) {
                    sgguCd = "110016";
                } else if (gu_name.equals("중구")) {
                    sgguCd = "110017";
                } else if (gu_name.equals("송파구")) {
                    sgguCd = "110018";
                } else if (gu_name.equals("중랑구")) {
                    sgguCd = "110019";
                } else if (gu_name.equals("양천구")) {
                    sgguCd = "110020";
                } else if (gu_name.equals("서초구")) {
                    sgguCd = "110021";
                } else if (gu_name.equals("노원구")) {
                    sgguCd = "110022";
                } else if (gu_name.equals("광진구")) {
                    sgguCd = "110023";
                } else if (gu_name.equals("강북구")) {
                    sgguCd = "110024";
                } else if (gu_name.equals("금천구")) {
                    sgguCd = "110025";
                }

            } else if (city_name.equals("경기도")) {
                sidoCd = "310000";
                if (gu_name.equals("가평군")) {
                    sgguCd = "310001";
                } else if (gu_name.equals("고양시")) {
                    sgguCd = "311901";
//                    queryUrl += "&sgguCd=311902";
//                    queryUrl += "&sgguCd=311903";
                    jagosipda.add("311901");
                    jagosipda.add("311902");
                    jagosipda.add("311903");
                } else if (gu_name.equals("과천시")) {
                    sgguCd = "310900";
                } else if (gu_name.equals("광명시")) {
                    sgguCd = "310100";
                } else if (gu_name.equals("광주시")) {
                    sgguCd = "312600";
                } else if (gu_name.equals("구리시")) {
                    sgguCd = "311000";
                } else if (gu_name.equals("군포시")) {
                    sgguCd = "311400";
                } else if (gu_name.equals("김포시")) {
                    sgguCd = "312300";
                } else if (gu_name.equals("남양주시")) {
                    sgguCd = "311500";
                } else if (gu_name.equals("동두천시")) {
                    sgguCd = "310200";
                } else if (gu_name.equals("부천시")) {
                    sgguCd = "310300";
//                    queryUrl += "&sgguCd=310301";
//                    queryUrl += "&sgguCd=310302";
                    jagosipda.add("310300");
                    jagosipda.add("310301");
                    jagosipda.add("310302");
                } else if (gu_name.equals("성남시")) {
                    sgguCd = "310401";
//                    queryUrl += "&sgguCd=310402";
//                    queryUrl += "&sgguCd=310403";
                    jagosipda.add("310401");
                    jagosipda.add("310402");
                    jagosipda.add("310403");
                } else if (gu_name.equals("수원시")) {
                    sgguCd = "310601";
//                    queryUrl += "&sgguCd=310602";
//                    queryUrl += "&sgguCd=310603";
//                    queryUrl += "&sgguCd=310604";
                    jagosipda.add("310601");
                    jagosipda.add("310602");
                    jagosipda.add("310603");
                    jagosipda.add("310604");
                } else if (gu_name.equals("시흥시")) {
                    sgguCd = "311700";
                } else if (gu_name.equals("안산시")) {
                    sgguCd = "311101";
//                    queryUrl += "&sgguCd=311102";
                    jagosipda.add("311101");
                    jagosipda.add("311102");
                } else if (gu_name.equals("안성시")) {
                    sgguCd = "312400";
                } else if (gu_name.equals("안양시")) {
                    sgguCd = "310701";
//                    queryUrl += "&sgguCd=310702";
                    jagosipda.add("310701");
                    jagosipda.add("310702");
                } else if (gu_name.equals("양주시")) {
                    sgguCd = "312700";
                } else if (gu_name.equals("양평군")) {
                    sgguCd = "310009";
                } else if (gu_name.equals("여주시")) {
                    sgguCd = "312900";
                } else if (gu_name.equals("연천군")) {
                    sgguCd = "310011";
                } else if (gu_name.equals("오산시")) {
                    sgguCd = "311800";
                } else if (gu_name.equals("용인시")) {
                    sgguCd = "312001";
//                    queryUrl += "&sgguCd=312002";
//                    queryUrl += "&sgguCd=312003";
                    jagosipda.add("312001");
                    jagosipda.add("312002");
                    jagosipda.add("312003");
                } else if (gu_name.equals("의왕시")) {
                    sgguCd = "311600";
                } else if (gu_name.equals("의정부시")) {
                    sgguCd = "310800";
                } else if (gu_name.equals("이천시")) {
                    sgguCd = "312100";
                } else if (gu_name.equals("파주시")) {
                    sgguCd = "312200";
                } else if (gu_name.equals("평택시")) {
                    sgguCd = "311200";
                } else if (gu_name.equals("포천시")) {
                    sgguCd = "312800";
                } else if (gu_name.equals("하남시")) {
                    sgguCd = "311300";
                } else if (gu_name.equals("화성시")) {
                    sgguCd = "312500";
                }
            } else if (city_name.equals("부산광역시")) {
                sidoCd = "210000";
                if (gu_name.equals("강서구")) {
                    sgguCd = "210012";
                } else if (gu_name.equals("금정구")) {
                    sgguCd = "210011";
                } else if (gu_name.equals("기장군")) {
                    sgguCd = "210100";
                } else if (gu_name.equals("남구")) {
                    sgguCd = "210001";
                } else if (gu_name.equals("동구")) {
                    sgguCd = "210002";
                } else if (gu_name.equals("동래구")) {
                    sgguCd = "210003";
                } else if (gu_name.equals("부산진구")) {
                    sgguCd = "210004";
                } else if (gu_name.equals("북구")) {
                    sgguCd = "210005";
                } else if (gu_name.equals("시상구")) {
                    sgguCd = "210015";
                } else if (gu_name.equals("시하구")) {
                    sgguCd = "210010";
                } else if (gu_name.equals("서구")) {
                    sgguCd = "210006";
                } else if (gu_name.equals("수영구")) {
                    sgguCd = "210014";
                } else if (gu_name.equals("연제구")) {
                    sgguCd = "210013";
                } else if (gu_name.equals("중구")) {
                    sgguCd = "210008";
                } else if (gu_name.equals("해운대구")) {
                    sgguCd = "210009";
                }
            } else if (city_name.equals("인천광역시")) {
                sidoCd = "220000";
                if (gu_name.equals("강화군")) {
                    sgguCd = "220100";
                } else if (gu_name.equals("계양구")) {
                    sgguCd = "220008";
                } else if (gu_name.equals("미추홀구")) {
                    sgguCd = "220001";
                } else if (gu_name.equals("남동구")) {
                    sgguCd = "220006";
                } else if (gu_name.equals("동구")) {
                    sgguCd = "220002";
                } else if (gu_name.equals("부평구")) {
                    sgguCd = "220003";
                } else if (gu_name.equals("서구")) {
                    sgguCd = "220005";
                } else if (gu_name.equals("연수구")) {
                    sgguCd = "220007";
                } else if (gu_name.equals("옹진군")) {
                    sgguCd = "220200";
                } else if (gu_name.equals("중구")) {
                    sgguCd = "220004";
                }
            } else if (city_name.equals("대구광역시")) {
                sidoCd = "230000";
                if (gu_name.equals("남구")) {
                    sgguCd = "230001";
                } else if (gu_name.equals("달서구")) {
                    sgguCd = "230007";
                } else if (gu_name.equals("달성군")) {
                    sgguCd = "230100";
                } else if (gu_name.equals("동구")) {
                    sgguCd = "230002";
                } else if (gu_name.equals("북구")) {
                    sgguCd = "230003";
                } else if (gu_name.equals("서구")) {
                    sgguCd = "230004";
                } else if (gu_name.equals("수성구")) {
                    sgguCd = "230005";
                } else if (gu_name.equals("중구")) {
                    sgguCd = "230006";
                }
            } else if (city_name.equals("대전광역시")) {
                sidoCd = "250000";
                if (gu_name.equals("대덕구")) {
                    sgguCd = "250002";
                } else if (gu_name.equals("동구")) {
                    sgguCd = "250004";
                } else if (gu_name.equals("서구")) {
                    sgguCd = "250003";
                } else if (gu_name.equals("유성구")) {
                    sgguCd = "250001";
                } else if (gu_name.equals("중구")) {
                    sgguCd = "250005";
                }
            } else if (city_name.equals("광주광역시")) {
                sidoCd = "240000";
                if (gu_name.equals("광산구")) {
                    sgguCd = "240004";
                } else if (gu_name.equals("남구")) {
                    sgguCd = "240005";
                } else if (gu_name.equals("동구")) {
                    sgguCd = "240001";
                } else if (gu_name.equals("북구")) {
                    sgguCd = "240002";
                } else if (gu_name.equals("서구")) {
                    sgguCd = "240003";
                }
            } else if (city_name.equals("울산광역시")) {
                sidoCd = "260000";
                if (gu_name.equals("중구")) {
                    sgguCd = "260003";
                } else if (gu_name.equals("남구")) {
                    sgguCd = "260001";
                } else if (gu_name.equals("동구")) {
                    sgguCd = "260002";
                } else if (gu_name.equals("북구")) {
                    sgguCd = "260004";
                } else if (gu_name.equals("울주군")) {
                    sgguCd = "260100";
                }
            } else if (city_name.equals("경상남도")) {
                sidoCd = "380000";
                if (gu_name.equals("거제시")) {
                    sgguCd = "381000";
                } else if (gu_name.equals("거창군")) {
                    sgguCd = "380002";
                } else if (gu_name.equals("고성군")) {
                    sgguCd = "380003";
                } else if (gu_name.equals("김해시")) {
                    sgguCd = "380100";
                } else if (gu_name.equals("남해군")) {
                    sgguCd = "380701";
                } else if (gu_name.equals("마산시")) {
                    sgguCd = "380701";
//                    queryUrl += "&sgguCd=380702";
                    jagosipda.add("380701");
                    jagosipda.add("380702");
                } else if (gu_name.equals("밀양시")) {
                    sgguCd = "380900";
                } else if (gu_name.equals("사천시")) {
                    sgguCd = "380300";
                } else if (gu_name.equals("산청군")) {
                    sgguCd = "380008";
                } else if (gu_name.equals("양산시")) {
                    sgguCd = "381100";
                } else if (gu_name.equals("의령군")) {
                    sgguCd = "380011";
                } else if (gu_name.equals("진주시")) {
                    sgguCd = "380500";
                } else if (gu_name.equals("진해시")) {
                    sgguCd = "380703";
                } else if (gu_name.equals("창녕군")) {
                    sgguCd = "380014";
                } else if (gu_name.equals("창원시")) {
                    sgguCd = "380701";
//                    queryUrl += "&sgguCd=380702";
//                    queryUrl += "&sgguCd=380703";
//                    queryUrl += "&sgguCd=380704";
//                    queryUrl += "&sgguCd=380705";
                    jagosipda.add("380701");
                    jagosipda.add("380702");
                    jagosipda.add("380703");
                    jagosipda.add("380704");
                    jagosipda.add("380705");
                } else if (gu_name.equals("통영시")) {
                    sgguCd = "380800";
                } else if (gu_name.equals("하동군")) {
                    sgguCd = "380016";
                } else if (gu_name.equals("함안군")) {
                    sgguCd = "380017";
                } else if (gu_name.equals("함양군")) {
                    sgguCd = "380018";
                } else if (gu_name.equals("합천군")) {
                    sgguCd = "380019";
                }
            } else if (city_name.equals("경상북도")) {
                sidoCd = "370000";
                if (gu_name.equals("경산시")) {
                    sgguCd = "371000";
                } else if (gu_name.equals("경주시")) {
                    sgguCd = "370100";
                } else if (gu_name.equals("고령군")) {
                    sgguCd = "370002";
                } else if (gu_name.equals("구미시")) {
                    sgguCd = "370200";
                } else if (gu_name.equals("군위군")) {
                    sgguCd = "370003";
                } else if (gu_name.equals("김천시")) {
                    sgguCd = "370300";
                } else if (gu_name.equals("문경시")) {
                    sgguCd = "370800";
                } else if (gu_name.equals("봉화군")) {
                    sgguCd = "370007";
                } else if (gu_name.equals("상주시")) {
                    sgguCd = "370900";
                } else if (gu_name.equals("성주군")) {
                    sgguCd = "370010";
                } else if (gu_name.equals("안동시")) {
                    sgguCd = "370400";
                } else if (gu_name.equals("영덕군")) {
                    sgguCd = "370012";
                } else if (gu_name.equals("영양군")) {
                    sgguCd = "370013";
                } else if (gu_name.equals("영주시")) {
                    sgguCd = "370500";
                } else if (gu_name.equals("영천시")) {
                    sgguCd = "370600";
                } else if (gu_name.equals("예천군")) {
                    sgguCd = "370017";
                } else if (gu_name.equals("울릉군")) {
                    sgguCd = "370018";
                } else if (gu_name.equals("울진군")) {
                    sgguCd = "370019";
                } else if (gu_name.equals("의성군")) {
                    sgguCd = "370021";
                } else if (gu_name.equals("청도군")) {
                    sgguCd = "370022";
                } else if (gu_name.equals("청송군")) {
                    sgguCd = "370023";
                } else if (gu_name.equals("칠곡군")) {
                    sgguCd = "370024";
                } else if (gu_name.equals("포항시")) {
                    sgguCd = "370701";
//                    queryUrl += "&sgguCd=370702";
                    jagosipda.add("370701");
                    jagosipda.add("370702");
                }
            } else if (city_name.equals("전라남도")) {
                sidoCd = "360000";
                if (gu_name.equals("강진군")) {
                    sgguCd = "360001";
                } else if (gu_name.equals("고흥군")) {
                    sgguCd = "360002";
                } else if (gu_name.equals("곡성군")) {
                    sgguCd = "360003";
                } else if (gu_name.equals("광양시")) {
                    sgguCd = "360700";
                } else if (gu_name.equals("구례군")) {
                    sgguCd = "360006";
                } else if (gu_name.equals("나주시")) {
                    sgguCd = "360200";
                } else if (gu_name.equals("담양군")) {
                    sgguCd = "360008";
                } else if (gu_name.equals("목포시")) {
                    sgguCd = "360300";
                } else if (gu_name.equals("무안군")) {
                    sgguCd = "360009";
                } else if (gu_name.equals("보성군")) {
                    sgguCd = "360010";
                } else if (gu_name.equals("순천시")) {
                    sgguCd = "360400";
                } else if (gu_name.equals("신안군")) {
                    sgguCd = "360012";
                } else if (gu_name.equals("여수시")) {
                    sgguCd = "360500";
                } else if (gu_name.equals("영광군")) {
                    sgguCd = "360014";
                } else if (gu_name.equals("영암군")) {
                    sgguCd = "360015";
                } else if (gu_name.equals("완도군")) {
                    sgguCd = "360016";
                } else if (gu_name.equals("장성군")) {
                    sgguCd = "360017";
                } else if (gu_name.equals("장흥군")) {
                    sgguCd = "360018";
                } else if (gu_name.equals("진도군")) {
                    sgguCd = "360019";
                } else if (gu_name.equals("함평군")) {
                    sgguCd = "360020";
                } else if (gu_name.equals("해남군")) {
                    sgguCd = "360021";
                } else if (gu_name.equals("화순군")) {
                    sgguCd = "360022";
                }
            } else if (city_name.equals("전라북도")) {
                sidoCd = "350000";
                if (gu_name.equals("고창군")) {
                    sgguCd = "350001";
                } else if (gu_name.equals("군산시")) {
                    sgguCd = "350100";
                } else if (gu_name.equals("김제시")) {
                    sgguCd = "350600";
                } else if (gu_name.equals("남원시")) {
                    sgguCd = "350200";
                } else if (gu_name.equals("무주군")) {
                    sgguCd = "350004";
                } else if (gu_name.equals("부안군")) {
                    sgguCd = "350005";
                } else if (gu_name.equals("순창군")) {
                    sgguCd = "350006";
                } else if (gu_name.equals("완주군")) {
                    sgguCd = "350008";
                } else if (gu_name.equals("익산시")) {
                    sgguCd = "350300";
                } else if (gu_name.equals("임실군")) {
                    sgguCd = "350010";
                } else if (gu_name.equals("장수군")) {
                    sgguCd = "350011";
                } else if (gu_name.equals("전주시")) {
                    sgguCd = "350401";
//                    queryUrl += "&sgguCd=350402";
                    jagosipda.add("350401");
                    jagosipda.add("350402");
                } else if (gu_name.equals("정읍시")) {
                    sgguCd = "350500";
                } else if (gu_name.equals("진안군")) {
                    sgguCd = "350013";
                }
            } else if (city_name.equals("충청남도")) {
                sidoCd = "340000";
                if (gu_name.equals("공주시")) {
                    sgguCd = "340300";
                } else if (gu_name.equals("금산군")) {
                    sgguCd = "340002";
                } else if (gu_name.equals("논산시")) {
                    sgguCd = "340700";
                } else if (gu_name.equals("당진시")) {
                    sgguCd = "340900";
                } else if (gu_name.equals("보령시")) {
                    sgguCd = "340400";
                } else if (gu_name.equals("부여군")) {
                    sgguCd = "340007";
                } else if (gu_name.equals("서산시")) {
                    sgguCd = "340600";
                } else if (gu_name.equals("서천군")) {
                    sgguCd = "340009";
                } else if (gu_name.equals("아산시")) {
                    sgguCd = "340500";
                } else if (gu_name.equals("예산군")) {
                    sgguCd = "340012";
                } else if (gu_name.equals("천안시")) {
                    sgguCd = "340201";
//                    queryUrl += "&sgguCd=340202";
                    jagosipda.add("340201");
                    jagosipda.add("340202");
                } else if (gu_name.equals("청양군")) {
                    sgguCd = "340014";
                } else if (gu_name.equals("태안군")) {
                    sgguCd = "340016";
                } else if (gu_name.equals("홍성군")) {
                    sgguCd = "340015";
                } else if (gu_name.equals("계룡시")) {
                    sgguCd = "340800";
                }
            } else if (city_name.equals("충청북도")) {
                sidoCd = "330000";
                if (gu_name.equals("괴산군")) {
                    sgguCd = "330001";
                } else if (gu_name.equals("단양군")) {
                    sgguCd = "330002";
                } else if (gu_name.equals("보은군")) {
                    sgguCd = "330003";
                } else if (gu_name.equals("영동군")) {
                    sgguCd = "330004";
                } else if (gu_name.equals("옥천군")) {
                    sgguCd = "330005";
                } else if (gu_name.equals("음성군")) {
                    sgguCd = "330006";
                } else if (gu_name.equals("제천시")) {
                    sgguCd = "330300";
                } else if (gu_name.equals("진천군")) {
                    sgguCd = "330009";
                } else if (gu_name.equals("청주시")) {
                    sgguCd = "330101";
//                    queryUrl += "&sgguCd=330102";
//                    queryUrl += "&sgguCd=330103";
//                    queryUrl += "&sgguCd=330104";
                    jagosipda.add("330101");
                    jagosipda.add("330102");
                    jagosipda.add("330103");
                    jagosipda.add("330104");
                } else if (gu_name.equals("충주시")) {
                    sgguCd = "330200";
                } else if (gu_name.equals("증평군")) {
                    sgguCd = "330011";
                }
            } else if (city_name.equals("강원도")) {
                sidoCd = "320000";
                if (gu_name.equals("강릉시")) {
                    sgguCd = "320100";
                } else if (gu_name.equals("고성군")) {
                    sgguCd = "320001";
                } else if (gu_name.equals("동해시")) {
                    sgguCd = "320200";
                } else if (gu_name.equals("삼척시")) {
                    sgguCd = "320700";
                } else if (gu_name.equals("속초시")) {
                    sgguCd = "320300";
                } else if (gu_name.equals("양구군")) {
                    sgguCd = "320004";
                } else if (gu_name.equals("양양군")) {
                    sgguCd = "320005";
                } else if (gu_name.equals("영월군")) {
                    sgguCd = "320006";
                } else if (gu_name.equals("원주시")) {
                    sgguCd = "320400";
                } else if (gu_name.equals("인제군")) {
                    sgguCd = "320008";
                } else if (gu_name.equals("정선군")) {
                    sgguCd = "320009";
                } else if (gu_name.equals("철원군")) {
                    sgguCd = "320010";
                } else if (gu_name.equals("춘천시")) {
                    sgguCd = "320500";
                } else if (gu_name.equals("태백시")) {
                    sgguCd = "320600";
                } else if (gu_name.equals("평창군")) {
                    sgguCd = "320012";
                } else if (gu_name.equals("홍천군")) {
                    sgguCd = "320013";
                } else if (gu_name.equals("화천군")) {
                    sgguCd = "320014";
                } else if (gu_name.equals("횡성군")) {
                    sgguCd = "320015";
                }
            } else if (city_name.equals("세종특별자치시")) {
                sidoCd = "410000";
                if(gu_name.equals("세종특별자치시")){
                    sgguCd = "410000";
                }
            } else {
                sidoCd = "390000";
                if (gu_name.equals("서귀포시")) {
                    sgguCd = "390100";
                } else if (gu_name.equals("제주시")) {
                    sgguCd = "390200";
                }
            }
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

    private void getpermisson() {

        // 메니패스트에 권한이 있는지 확인
        int permiCheck_loca = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        //앱권한이 없으면 권한 요청
        if(permiCheck_loca == PackageManager.PERMISSION_DENIED){
            Log.d("전화 권한 없는 상태", "");
            ActivityCompat.requestPermissions(Recyclerview_HospitalList.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        //권한 있다면
        else{
            Log.d("전화 권한 있는 상태", "");
        }
    }

    class Filtering_for_ratio implements Comparator<HospitalItemForCsv> {
        public int compare(HospitalItemForCsv a, HospitalItemForCsv b){

            double ratio_a = Double.parseDouble(a.getPercent());
            double ratio_b = Double.parseDouble(b.getPercent());
            if(ratio_a == ratio_b){
                return 0;
            }else if(ratio_b > ratio_a){
                return 1;
            }else{
                return -1;
            }

        }
    }

    class Filtering_for_total implements Comparator<HospitalItemForCsv> {
        public int compare(HospitalItemForCsv a, HospitalItemForCsv b){

            double total_a = Double.parseDouble(a.getPronum());
            double total_b = Double.parseDouble(b.getPronum());
            if(total_a == total_b){
                return 0;
            }else if(total_b > total_a){
                return 1;
            }else{
                return -1;
            }

        }
    }

    class Filtering_for_distance implements Comparator<HospitalItemForCsv> {
        public int compare(HospitalItemForCsv a, HospitalItemForCsv b){

            double distance_a = Double.parseDouble(a.getDistance());
            double distance_b = Double.parseDouble(b.getDistance());
            if(distance_b == distance_a){
                return 0;
            }else if(distance_a > distance_b){
                return 1;
            }else{
                return -1;
            }

        }
    }

    class Filtering_for_ganada implements Comparator<HospitalItemForCsv> {
        public int compare(HospitalItemForCsv a, HospitalItemForCsv b){
            return a.getHospitalname().compareTo(b.getHospitalname());
        }
    }
}