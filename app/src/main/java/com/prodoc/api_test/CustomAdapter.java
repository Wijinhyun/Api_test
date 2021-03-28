package com.prodoc.api_test;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    //private ArrayList<HospitalItem> mList;
    private ArrayList<HospitalItemForCsv> mList; //new ArrayList<>();
    private LayoutInflater mInflate;
    private Context context;
    private String subject;

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView addrtxt,clCdNmtxt,estbDdtxt,gdrCnttxt,hospUrltxt,intnCnttxt,postNotxt,resdntCnttxt,sdrCnttxt,telnotxt,yadmNmtxt,ykihotxt, Tv_list, Code_list;
        private TextView Tv_percent, Tv_sdrdgsCnt, Tv_drTotCnt, Tv_adress, Tv_sbj, Tv_distance, Hos_phar1;
        private View view_item;

        public CustomViewHolder(View view) {
            super(view);
            this.Hos_phar1 = (TextView) view.findViewById(R.id.hos_phar1);
            this.view_item = view.findViewById(R.id.view_item);
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


        if(subject.equals("피부과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("성형외과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("안과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("정형외과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("신경과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("신경외과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("정신건강의학과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("이비인후과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("내과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("외과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("가정의학과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("소아청소년과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("비뇨의학과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("산부인과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("재활의학과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("마취통증의학과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("흉부외과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("영상의학과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("진단검사의학과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("응급의학과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("방사선종양학과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("직업환경의학과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("결핵과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("핵의학과")){
            viewholder.Hos_phar1.setText("의원");
        }else if(subject.equals("치과교정과")){
            viewholder.Hos_phar1.setText("치과의원");
        }else if(subject.equals("치과보철과")){
            viewholder.Hos_phar1.setText("치과의원");
        }else if(subject.equals("구강악안면외과")){
            viewholder.Hos_phar1.setText("치과의원");
        }else if(subject.equals("치주과")){
            viewholder.Hos_phar1.setText("치과의원");
        }else if(subject.equals("소아치과")){
            viewholder.Hos_phar1.setText("치과의원");
        }else if(subject.equals("구강내과")){
            viewholder.Hos_phar1.setText("치과의원");
        }else if(subject.equals("치과보존과")){
            viewholder.Hos_phar1.setText("치과의원");
        }else if(subject.equals("통합치의학과")){
            viewholder.Hos_phar1.setText("치과의원");
        }else if(subject.equals("구강병리과")){
            viewholder.Hos_phar1.setText("치과의원");
        }else if(subject.equals("예방치과")){
            viewholder.Hos_phar1.setText("치과의원");
        }else if(subject.equals("영상치의학과")){
            viewholder.Hos_phar1.setText("치과의원");
        }else if(subject.equals("한방내과")){
            viewholder.Hos_phar1.setText("한의원");
        }else if(subject.equals("침구과")){
            viewholder.Hos_phar1.setText("한의원");
        }else if(subject.equals("한방안·이비인후·피부과")){
            viewholder.Hos_phar1.setText("한의원");
        }else if(subject.equals("한방재활의학과")){
            viewholder.Hos_phar1.setText("한의원");
        }else if(subject.equals("한방소아과")){
            viewholder.Hos_phar1.setText("한의원");
        }else if(subject.equals("한방신경정신과")){
            viewholder.Hos_phar1.setText("한의원");
        }else if(subject.equals("사상체질과")){
            viewholder.Hos_phar1.setText("한의원");
        }else if(subject.equals("한방부인과")){
            viewholder.Hos_phar1.setText("한의원");
        }



        String percent = "0.0";
        percent = String.format("%.1f", Double.parseDouble(mList.get(position).getPercent()));
        viewholder.Tv_percent.setText(percent + "%");

        if(Double.parseDouble(percent) >= 66.6){
            //iv_circle.setImageResource(R.drawable.greencircle);
            viewholder.Hos_phar1.setTextColor(Color.parseColor("#49654C"));
            viewholder.Tv_percent.setTextColor(Color.parseColor("#0A640A"));
            viewholder.view_item.setBackgroundColor(Color.parseColor("#0A640A"));
        }else if(Double.parseDouble(percent) >= 33.3){
            //iv_circle.setImageResource(R.drawable.yellowcircle);
            viewholder.Hos_phar1.setTextColor(Color.parseColor("#49654C"));
            viewholder.Tv_percent.setTextColor(Color.parseColor("#FFCC42"));
            viewholder.view_item.setBackgroundColor(Color.parseColor("#FFCC42"));
        }else if(Double.parseDouble(percent) >= 0.1) {          // 전문의가 아예 없으면 지도에 띄우지 않음
            //iv_circle.setImageResource(R.drawable.redcircle);
            viewholder.Hos_phar1.setTextColor(Color.parseColor("#49654C"));
            viewholder.Tv_percent.setTextColor(Color.parseColor("#C03713"));
            viewholder.view_item.setBackgroundColor(Color.parseColor("#C03713"));
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