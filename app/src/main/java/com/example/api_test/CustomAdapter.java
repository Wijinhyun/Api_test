package com.example.api_test;

import android.content.Context;
import android.graphics.Color;
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


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView addrtxt,clCdNmtxt,estbDdtxt,gdrCnttxt,hospUrltxt,intnCnttxt,postNotxt,resdntCnttxt,sdrCnttxt,telnotxt,yadmNmtxt,ykihotxt, Tv_list, Code_list;
        private TextView Tv_percent, Tv_sdrdgsCnt, Tv_drTotCnt;

        public CustomViewHolder(View view) {
            super(view);

            //this.estbDdtxt = (TextView) view.findViewById(R.id.estbDd);
            this.yadmNmtxt = (TextView) view.findViewById(R.id.yadmNm);
            this.Tv_list = (TextView) view.findViewById(R.id.tv_list);
            this.Tv_percent = (TextView) view.findViewById(R.id.tv_percent);
            this.Tv_sdrdgsCnt = (TextView) view.findViewById(R.id.tv_sdrdgsCnt);
            this.Tv_drTotCnt = (TextView) view.findViewById(R.id.tv_drTotCnt);
        }
    }


    public CustomAdapter(Context context, ArrayList<HospitalItem> items) {
        this.mList = items;
        this.mInflate = LayoutInflater.from(context);
        this.context = context;
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
        if(mList.get(position).getMedical_list() != null) {
            viewholder.Tv_list.setText(mList.get(position).getMedical_list());
        }
        String percent = "0.0";
        viewholder.Tv_sdrdgsCnt.setText(mList.get(position).getSdrdgsCnt());
        viewholder.Tv_drTotCnt.setText(mList.get(position).getDrTotCnt());
        if(mList.get(position).getSdrdgsCnt() != null && !mList.get(position).getDrTotCnt().equals("0")) {
            percent = String.format("%.1f", Double.parseDouble(mList.get(position).getSdrdgsCnt()) / Double.parseDouble(mList.get(position).getDrTotCnt()) * 100);
        }
        viewholder.Tv_percent.setText(percent + "%");
        if(Double.parseDouble(percent) >= 66.6){
            viewholder.Tv_percent.setBackgroundColor(Color.parseColor("#0A640A"));
        }else if(Double.parseDouble(percent) >= 33.3){
            viewholder.Tv_percent.setBackgroundColor(Color.parseColor("#FFCC42"));
        }else{
            viewholder.Tv_percent.setBackgroundColor(Color.parseColor("#BF3813"));
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }




}