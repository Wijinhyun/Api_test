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

    private ArrayList<HospitalItem> mList;
    private LayoutInflater mInflate;
    private Context context;
    private String subject;

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView addrtxt,clCdNmtxt,estbDdtxt,gdrCnttxt,hospUrltxt,intnCnttxt,postNotxt,resdntCnttxt,sdrCnttxt,telnotxt,yadmNmtxt,ykihotxt, Tv_list, Code_list;
        private TextView Tv_percent, Tv_sdrdgsCnt, Tv_drTotCnt, Tv_adress, Tv_sbj, Tv_distance;

        public CustomViewHolder(View view) {
            super(view);
            this.Tv_sbj = view.findViewById(R.id.tv_sbj);
            //this.estbDdtxt = (TextView) view.findViewById(R.id.estbDd);
            this.yadmNmtxt = (TextView) view.findViewById(R.id.yadmNm);
            //this.Tv_list = (TextView) view.findViewById(R.id.tv_list);
            this.Tv_percent = (TextView) view.findViewById(R.id.tv_percent);
            this.Tv_sdrdgsCnt = (TextView) view.findViewById(R.id.tv_sdrdgsCnt);
            this.Tv_drTotCnt = (TextView) view.findViewById(R.id.tv_drTotCnt);
            this.Tv_adress = view.findViewById(R.id.tv_adress);
            this.Tv_distance = view.findViewById(R.id.tv_distance);
        }
    }


    public CustomAdapter(Context context, ArrayList<HospitalItem> items, String subject) {
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

        viewholder.yadmNmtxt.setText(mList.get(position).getYadmNm());
       /* if(mList.get(position).getMedical_list() != null) {
            viewholder.Tv_list.setText(mList.get(position).getMedical_list());
        }*/
        String percent = "0.0";
        viewholder.Tv_sbj.setText(subject + " 전문의 : ");
        viewholder.Tv_sdrdgsCnt.setText(mList.get(position).getSdrdgsCnt());
        viewholder.Tv_drTotCnt.setText(mList.get(position).getDrTotCnt());
        viewholder.Tv_adress.setText(mList.get(position).getAddr());
        if(mList.get(position).getSdrdgsCnt() != null && !mList.get(position).getDrTotCnt().equals("0")) {
            percent = String.format("%.1f", Double.parseDouble(mList.get(position).getSdrdgsCnt()) / Double.parseDouble(mList.get(position).getDrTotCnt()) * 100);
        }
        viewholder.Tv_percent.setText(percent + "%");
        int temp = mList.get(position).getDistance();
        String temp2;
        if(temp >= 1000){
            temp2 = String.format("%.1f", (double) temp / 1000);
            viewholder.Tv_distance.setText(temp2 + "km | ");
        }else {
            viewholder.Tv_distance.setText(temp + "m | ");
        }
        //GradientDrawable shape = new GradientDrawable();
        //shape.setCornerRadius( 8 );
        //View view = (TextView) findViewById( R.id.tv_percent );
        if(Double.parseDouble(percent) >= 66.6){
            viewholder.Tv_percent.setBackgroundResource(R.drawable.round_percent2);
        }else if(Double.parseDouble(percent) >= 33.3){
            viewholder.Tv_percent.setBackgroundResource(R.drawable.round_percent3);
        }else{
            viewholder.Tv_percent.setBackgroundResource(R.drawable.round_percent);
        }



    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }




}