package com.example.api_test;

import android.content.Context;
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

        public CustomViewHolder(View view) {
            super(view);
            this.addrtxt = (TextView) view.findViewById(R.id.addr);
            this.clCdNmtxt = (TextView) view.findViewById(R.id.clCdNm);
            this.estbDdtxt = (TextView) view.findViewById(R.id.estbDd);
            this.gdrCnttxt = (TextView) view.findViewById(R.id.gdrCnt);
            this.hospUrltxt = (TextView) view.findViewById(R.id.hospUrl);
            this.intnCnttxt = (TextView) view.findViewById(R.id.intnCnt);
            this.postNotxt = (TextView) view.findViewById(R.id.postNo);
            this.resdntCnttxt = (TextView) view.findViewById(R.id.resdntCnt);
            this.sdrCnttxt = (TextView) view.findViewById(R.id.sdrCnt);
            this.telnotxt = (TextView) view.findViewById(R.id.telno);
            this.yadmNmtxt = (TextView) view.findViewById(R.id.yadmNm);
            this.ykihotxt = (TextView) view.findViewById(R.id.ykiho);
            this.Tv_list = (TextView) view.findViewById(R.id.tv_list);

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

        viewholder.addrtxt.setText(mList.get(position).getAddr());
        viewholder.clCdNmtxt.setText(mList.get(position).getClCdNm());
        viewholder.estbDdtxt.setText(mList.get(position).getEstbDd());
        viewholder.gdrCnttxt.setText(mList.get(position).getGdrCnt());
        viewholder.hospUrltxt.setText(mList.get(position).getHospUrl());
        viewholder.intnCnttxt.setText(mList.get(position).getIntnCnt());
        viewholder.postNotxt.setText(mList.get(position).getPostNo());
        viewholder.resdntCnttxt.setText(mList.get(position).getResdntCnt());
        viewholder.sdrCnttxt.setText(mList.get(position).getSdrCnt());
        viewholder.telnotxt.setText(mList.get(position).getTelno());
        viewholder.yadmNmtxt.setText(mList.get(position).getYadmNm());
        viewholder.ykihotxt.setText(mList.get(position).getYkiho());
        viewholder.Tv_list.setText(mList.get(position).getMedical_list());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }




}