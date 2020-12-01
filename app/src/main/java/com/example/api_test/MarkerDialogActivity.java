package com.example.api_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class MarkerDialogActivity extends Activity implements View.OnClickListener {

    private ImageView iv_circle;
    private TextView tv_percent, tv_hospitalname, tv_distance, tv_addr, tv_sbj, tv_pronum, tv_totalnum;
    private Button btn_call, btn_route;
    private CardView cardView;
    private String tvpercent, tvhospitalname, tvdistance, tvaddr, tvsbj, tvpronum, tvtotalnum, tvtel, tvpark, tvurl;
    private String callnum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //WindowManager.LayoutParams  layoutParams = new WindowManager.LayoutParams();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setGravity(Gravity.BOTTOM);

        setContentView(R.layout.activity_dialog);
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

        callnum = "tel: "+tvtel;

        tv_hospitalname.setText(tvhospitalname);
        tv_totalnum.setText(tvtotalnum + "명");
        tv_pronum.setText(tvpronum + "명 ");
        tv_sbj.setText(tvsbj + " ");
        tv_addr.setText(" " + tvaddr);
        //tv_distance.setText(item.getDistance());
        //tv_percent.setText(tvpercent);

        String percent = "0.0";
        percent = String.format("%.1f", Double.parseDouble(tvpercent));
        tv_percent.setText(percent + "%");

        if(Double.parseDouble(percent) >= 66.6){
            //iv_circle.setImageResource(R.drawable.greencircle);
            tv_percent.setTextColor(Color.parseColor("#0A640A"));
        }else if(Double.parseDouble(percent) >= 33.3){
            //iv_circle.setImageResource(R.drawable.yellowcircle);
            tv_percent.setTextColor(Color.parseColor("#FFCC42"));
        }else if(Double.parseDouble(percent) >= 0.1) {          // 전문의가 아예 없으면 지도에 띄우지 않음
            //iv_circle.setImageResource(R.drawable.redcircle);
            tv_percent.setTextColor(Color.parseColor("#C03713"));
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
        Toast.makeText(MarkerDialogActivity.this, "세부정보로 넘어가게 구현", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    private void setContent(){
        cardView = (CardView) findViewById(R.id.view_card);
        //iv_circle = (ImageView) findViewById(R.id.iv_circle);
        tv_percent = (TextView) findViewById(R.id.tv_percent);
        tv_hospitalname = (TextView) findViewById(R.id.tv_hospitalname);
        tv_distance = (TextView) findViewById(R.id.tv_distance);
        tv_addr = (TextView) findViewById(R.id.tv_addr);
        tv_sbj = (TextView) findViewById(R.id.tv_sbj);
        tv_pronum = (TextView) findViewById(R.id.tv_pronum);
        tv_totalnum = (TextView) findViewById(R.id.tv_totalnum);
        btn_call = (Button) findViewById(R.id.btn_call);
        btn_route = (Button) findViewById(R.id.btn_route);

        cardView.setOnClickListener(this);
        btn_call.setOnClickListener(this);
        btn_route.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_card:
                Toast.makeText(MarkerDialogActivity.this, "세부정보로 넘어가게 구현", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_call:
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(callnum)));
                //Toast.makeText(MarkerDialogActivity.this, "전화로 넘어가게 구현", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_route:
                Toast.makeText(MarkerDialogActivity.this, "네이버 지도 길찾기로 넘어가게 구현", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }
}
