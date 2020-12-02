package com.example.api_test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    //private ArrayList<HospitalItem> mList;
    private ArrayList<HospitalItemForCsv> mList; //new ArrayList<>();
    private LayoutInflater mInflate;
    private Context context;
    private String subject;

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView addrtxt,clCdNmtxt,estbDdtxt,gdrCnttxt,hospUrltxt,intnCnttxt,postNotxt,resdntCnttxt,sdrCnttxt,telnotxt,yadmNmtxt,ykihotxt, Tv_list, Code_list;
        private TextView Tv_percent, Tv_sdrdgsCnt, Tv_drTotCnt, Tv_adress, Tv_sbj, Tv_distance;

        public CustomViewHolder(View view) {
            super(view);
            this.Tv_sbj = view.findViewById(R.id.tv_sbj1);
            //this.estbDdtxt = (TextView) view.findViewById(R.id.estbDd);
            this.yadmNmtxt = (TextView) view.findViewById(R.id.tv_hospitalname1);
            //this.Tv_list = (TextView) view.findViewById(R.id.tv_list);
            this.Tv_percent = (TextView) view.findViewById(R.id.tv_percent1);
            this.Tv_sdrdgsCnt = (TextView) view.findViewById(R.id.tv_pronum1);
            this.Tv_drTotCnt = (TextView) view.findViewById(R.id.tv_totalnum1);
            this.Tv_adress = view.findViewById(R.id.tv_addr1);
            this.Tv_distance = view.findViewById(R.id.tv_distance1);
        }
    }


    public CustomAdapter(Context context, ArrayList<HospitalItemForCsv> items, String subject) {
        this.mList = items;
        this.mInflate = LayoutInflater.from(context);
        this.context = context;
        this.subject = subject;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = mInflate.inflate(R.layout.hospital_infor_layout, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {


        //viewholder.estbDdtxt.setText(mList.get(position).getEstbDd());

        viewholder.yadmNmtxt.setText(mList.get(position).getHospitalname());
       /* if(mList.get(position).getMedical_list() != null) {
            viewholder.Tv_list.setText(mList.get(position).getMedical_list());
        }*/
        //String percent = "0.0";
        viewholder.Tv_sbj.setText(subject + " 전문의");
        viewholder.Tv_sdrdgsCnt.setText(mList.get(position).getPronum() + "명");
        viewholder.Tv_drTotCnt.setText(mList.get(position).getTotalnum() + "명");
        viewholder.Tv_adress.setText(mList.get(position).getAddr());
//        if(mList.get(position).getPronum() != null && !mList.get(position).getTotalnum().equals("0")) {
//            percent = String.format("%.1f", Double.parseDouble(mList.get(position).getPronum()) / Double.parseDouble(mList.get(position).getTotalnum()) * 100);
//        }
        //viewholder.Tv_percent.setText(mList.get(position).getPercent() + "%");

        String percent = "0.0";
        percent = String.format("%.1f", Double.parseDouble(mList.get(position).getPercent()));
        viewholder.Tv_percent.setText(percent + "%");

        if(Double.parseDouble(percent) >= 66.6){
            //iv_circle.setImageResource(R.drawable.greencircle);
            viewholder.Tv_percent.setTextColor(Color.parseColor("#0A640A"));
        }else if(Double.parseDouble(percent) >= 33.3){
            //iv_circle.setImageResource(R.drawable.yellowcircle);
            viewholder.Tv_percent.setTextColor(Color.parseColor("#FFCC42"));
        }else if(Double.parseDouble(percent) >= 0.1) {          // 전문의가 아예 없으면 지도에 띄우지 않음
            //iv_circle.setImageResource(R.drawable.redcircle);
            viewholder.Tv_percent.setTextColor(Color.parseColor("#C03713"));
        }

        double temp = Double.parseDouble(mList.get(position).getDistance());
        String temp2;
        if(temp >= 1000){
            temp2 = String.format("%.1f", temp / 1000);
            viewholder.Tv_distance.setText(temp2 + "km | ");
        }else {
            viewholder.Tv_distance.setText(temp + "m | ");
        }
        //GradientDrawable shape = new GradientDrawable();
        //shape.setCornerRadius( 8 );
        //View view = (TextView) findViewById( R.id.tv_percent );
//        if(Double.parseDouble(percent) >= 66.6){
//            viewholder.Tv_percent.setBackgroundResource(R.drawable.round_percent2);
//        }else if(Double.parseDouble(percent) >= 33.3){
//            viewholder.Tv_percent.setBackgroundResource(R.drawable.round_percent3);
//        }else{
//            viewholder.Tv_percent.setBackgroundResource(R.drawable.round_percent);
//        }



    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }




}