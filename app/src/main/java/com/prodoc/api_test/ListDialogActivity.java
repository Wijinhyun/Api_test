package com.prodoc.api_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ListDialogActivity extends Activity implements View.OnClickListener {
    private ImageView iv_static;
    private TextView tv_percent, tv_hospitalname, tv_distance, tv_addr, tv_sbj, tv_pronum, tv_totalnum, tv_Hos_pharforlist;
    private Button btn_call, btn_route;
    private CardView cardView;
    private String tvpercent, tvhospitalname, tvdistance, tvaddr, tvsbj, tvpronum, tvtotalnum, tvtel, tvpark, tvurl;
    private String callnum;
    private View view_item1;

    private double init_xpos;
    private double init_ypos;
    private String d_ypos, d_xpos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.d("chk_img", "생섬됨");
        //WindowManager.LayoutParams  layoutParams = new WindowManager.LayoutParams();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setGravity(Gravity.CENTER);

        setContentView(R.layout.activity_dialog_list);
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 1.0); //Display 사이즈의 70%
        //int height = (int) (display.getHeight() * 0.2);  //Display 사이즈의 90%
        getWindow().getAttributes().width = width;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //getWindow().getAttributes().height = height;

//        getWindow().getAttributes().width   = (int)(display.getInstance().getDisplayMetrics().widthPixels * 0.9);
//        getWindow().getAttributes().height  = (int)(cGameManager.getInstance().getDisplayMetrics().heightPixels * 0.4);

        setContent();

        Intent intent = getIntent();
        tvhospitalname = intent.getStringExtra("hospitalname");
        tvtotalnum = intent.getStringExtra("totalnum");
        tvpronum = intent.getStringExtra("pronum");
        tvsbj = intent.getStringExtra("sbj");
        tvaddr = intent.getStringExtra("addr");
        tvpercent = intent.getStringExtra("percent");
        tvdistance = intent.getStringExtra("distance");
        tvtel = intent.getStringExtra("tel");
        tvpark = intent.getStringExtra("park");
        tvurl = intent.getStringExtra("url");
        d_ypos = intent.getStringExtra("d_ypos");
        d_xpos = intent.getStringExtra("d_xpos");
        init_ypos = intent.getDoubleExtra("init_ypos",0);
        init_xpos = intent.getDoubleExtra("init_xpos",0);

        callnum = "tel: "+tvtel;

        String uurl = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?" +
                "w=450&h=300" +
                "&center=" + d_xpos + "," + d_ypos +
                "&level=16" +
                "&markers=type:d|size:mid|pos:" + d_xpos + "%20" + d_ypos +
                "&X-NCP-APIGW-API-KEY-ID=u1aee7mhqb" +
                "&X-NCP-APIGW-API-KEY=2ctTFzgE3LR8q9CyqEbtEk3yLQu3L0IkRAymNpmy";


        //Picasso.get().load("https://naveropenapi.apigw.ntruss.com/map-static/v2/raster-cors?w=300&h=300&center=127.1054221,37.3591614&level=16&X-NCP-APIGW-API-KEY-ID=u1aee7mhqb").into(iv_static);
        Picasso.get().load(uurl).into(iv_static);

        tv_hospitalname.setText(tvhospitalname);
        tv_totalnum.setText(tvtotalnum + "명");
        tv_pronum.setText(tvpronum + "명 ");
        tv_sbj.setText(tvsbj + " 전문의");
        tv_addr.setText(" " + tvaddr);
        //tv_distance.setText(item.getDistance());
        //tv_percent.setText(tvpercent);


        if(tvsbj.equals("피부과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("성형외과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("안과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("정형외과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("신경과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("신경외과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("정신건강의학과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("이비인후과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("내과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("외과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("가정의학과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("소아청소년과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("비뇨의학과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("산부인과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("재활의학과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("마취통증의학과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("흉부외과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("영상의학과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("진단검사의학과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("응급의학과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("방사선종양학과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("직업환경의학과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("결핵과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("핵의학과")){
            tv_Hos_pharforlist.setText("의원");
        }else if(tvsbj.equals("치과교정과")){
            tv_Hos_pharforlist.setText("치과의원");
        }else if(tvsbj.equals("치과보철과")){
            tv_Hos_pharforlist.setText("치과의원");
        }else if(tvsbj.equals("구강악안면외과")){
            tv_Hos_pharforlist.setText("치과의원");
        }else if(tvsbj.equals("치주과")){
            tv_Hos_pharforlist.setText("치과의원");
        }else if(tvsbj.equals("소아치과")){
            tv_Hos_pharforlist.setText("치과의원");
        }else if(tvsbj.equals("구강내과")){
            tv_Hos_pharforlist.setText("치과의원");
        }else if(tvsbj.equals("치과보존과")){
            tv_Hos_pharforlist.setText("치과의원");
        }else if(tvsbj.equals("통합치의학과")){
            tv_Hos_pharforlist.setText("치과의원");
        }else if(tvsbj.equals("구강병리과")){
            tv_Hos_pharforlist.setText("치과의원");
        }else if(tvsbj.equals("예방치과")){
            tv_Hos_pharforlist.setText("치과의원");
        }else if(tvsbj.equals("영상치의학과")){
            tv_Hos_pharforlist.setText("치과의원");
        }else if(tvsbj.equals("한방내과")){
            tv_Hos_pharforlist.setText("한의원");
        }else if(tvsbj.equals("침구과")){
            tv_Hos_pharforlist.setText("한의원");
        }else if(tvsbj.equals("한방안·이비인후·피부과")){
            tv_Hos_pharforlist.setText("한의원");
        }else if(tvsbj.equals("한방재활의학과")){
            tv_Hos_pharforlist.setText("한의원");
        }else if(tvsbj.equals("한방소아과")){
            tv_Hos_pharforlist.setText("한의원");
        }else if(tvsbj.equals("한방신경정신과")){
            tv_Hos_pharforlist.setText("한의원");
        }else if(tvsbj.equals("사상체질과")){
            tv_Hos_pharforlist.setText("한의원");
        }else if(tvsbj.equals("한방부인과")){
            tv_Hos_pharforlist.setText("한의원");
        }



        String percent = "0.0";
        percent = String.format("%.1f", Double.parseDouble(tvpercent));
        tv_percent.setText(percent + "%");

        if(Double.parseDouble(percent) >= 66.6){
            //iv_circle.setImageResource(R.drawable.greencircle);
            tv_percent.setTextColor(Color.parseColor("#0A640A"));
            view_item1.setBackgroundColor(Color.parseColor("#0A640A"));
        }else if(Double.parseDouble(percent) >= 33.3){
            //iv_circle.setImageResource(R.drawable.yellowcircle);
            tv_percent.setTextColor(Color.parseColor("#FFCC42"));
            view_item1.setBackgroundColor(Color.parseColor("#FFCC42"));
        }else if(Double.parseDouble(percent) >= 0.1) {          // 전문의가 아예 없으면 지도에 띄우지 않음
            //iv_circle.setImageResource(R.drawable.redcircle);
            tv_percent.setTextColor(Color.parseColor("#C03713"));
            view_item1.setBackgroundColor(Color.parseColor("#C03713"));
        }

        String temp2 = "0.0";
        temp2 = String.format("%.1f", Double.parseDouble(tvdistance));
        if(Double.parseDouble(temp2) >= 1000){
            tv_distance.setText(String.format("%.1f", Double.parseDouble(tvdistance) / 1000) + "km ");
        }else {
            tv_distance.setText(tvdistance + "m ");
        }

    }

    @Override
    public void onBackPressed() {
        //Toast.makeText(ListDialogActivity.this, "세부정보로 넘어가게 구현", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    private void setContent(){
        cardView = (CardView) findViewById(R.id.view_cardforlist);
        iv_static = (ImageView) findViewById(R.id.iv_static);
        tv_percent = (TextView) findViewById(R.id.tv_percentforlist);
        tv_hospitalname = (TextView) findViewById(R.id.tv_hospitalnameforlist);
        tv_distance = (TextView) findViewById(R.id.tv_distanceforlist);
        tv_addr = (TextView) findViewById(R.id.tv_addrforlist);
        tv_sbj = (TextView) findViewById(R.id.tv_sbjforlist);
        tv_pronum = (TextView) findViewById(R.id.tv_pronumforlist);
        tv_totalnum = (TextView) findViewById(R.id.tv_totalnumforlist);
        btn_call = (Button) findViewById(R.id.btn_callforlist);
        btn_route = (Button) findViewById(R.id.btn_routeforlist);
        view_item1 = findViewById(R.id.view_item1);

        tv_Hos_pharforlist = (TextView) findViewById(R.id.hos_pharforlist);

        cardView.setOnClickListener(this);
        btn_call.setOnClickListener(this);
        btn_route.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_cardforlist:
                //Toast.makeText(ListDialogActivity.this, "세부정보로 넘어가게 구현", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_callforlist:
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(callnum)));
                //Toast.makeText(MarkerDialogActivity.this, "전화로 넘어가게 구현", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_routeforlist:

                String sname = null;
                try {
                    sname = URLEncoder.encode("내 위치", "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String dname = null;
                try {
                    dname = URLEncoder.encode(tvhospitalname, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //String url = "http://stackoverflow.com/search?q=" + query;

                String url = "nmap://route/public?slat="+init_ypos+"&slng="+init_xpos+"&sname="+sname+"&dlat="+d_ypos+"&dlng="+d_xpos+"&dname="+dname+"&appname=com.example.api_test";
                //"nmap://route/public?slat=37.4640070&slng=126.9522394&sname=&dlat=37.5209436&dlng=127.1230074&dname=&appname=com.example.api_test";
                Log.d("init, d 값",init_ypos + "  " + init_xpos + "  " + d_ypos + "  " + d_xpos);
                Log.d("init, d 값",sname + "  " + dname);


                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent2.addCategory(Intent.CATEGORY_BROWSABLE);

                List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent2, PackageManager.MATCH_DEFAULT_ONLY);
                //List<ResolveInfo> list = getPackageManager().getInstalledPackages(0);


                if (list == null || list.isEmpty()) {
                    Log.d("list 비었다", list + "  ");
                    //this.startActivity(intent2);
                    this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.nmap")));
                } else {
                    Log.d("list 안 비었다",list + "  ");
                    this.startActivity(intent2);
                }

                //Toast.makeText(MarkerDialogActivity.this, "네이버 지도 길찾기로 넘어가게 구현", Toast.LENGTH_SHORT).show();

            default:
                break;
        }
    }
}