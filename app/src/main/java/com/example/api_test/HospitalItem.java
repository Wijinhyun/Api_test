package com.example.api_test;

import java.io.Serializable;
import java.util.ArrayList;

public class HospitalItem implements Serializable {

    private String addr, clCdNm, estbDd, gdrCnt, hospUrl, intnCnt, postNo, resdntCnt, sdrCnt, telno, yadmNm, ykiho, XPos, YPos, drTotCnt, medical_list;
    private ArrayList<String> dgsbjtCdNm = new ArrayList<String>(), dgsbjtPrSdrCnt = new ArrayList<String>(), dgsbjtCd = new ArrayList<String>();
    private String sdrdgsCnt;
    private int distance;

    public HospitalItem(String addr, String clCdNm, String estbDd, String gdrCnt, String hospUrl, String intnCnt, String postNo, String resdntCnt, String sdrCnt, String telno, String yadmNm, String ykiho, ArrayList<String> dgsbjtCdNm, ArrayList<String> dgsbjtPrSdrCnt, String medical_list, ArrayList<String> dgsbjtCd, String sdrdgsCnt, int distance, String drTotCnt, String XPos, String YPos) {
        this.addr = addr; //주소
        this.clCdNm = clCdNm; //종별코드명
        this.estbDd = estbDd; //개설일자
        this.gdrCnt = gdrCnt; //선택진료 의사수
        this.hospUrl = hospUrl; //홈페이지
        this.intnCnt = intnCnt; //인턴 수
        this.postNo = postNo; //우편번호
        this.resdntCnt = resdntCnt; //레지던트 의사수
        this.sdrCnt = sdrCnt; //전문의 수
        this.telno = telno; //전화번호
        this.yadmNm = yadmNm; //병원명
        this.ykiho = ykiho;
        this.dgsbjtCdNm = dgsbjtCdNm; //진료과목 번호
        this.dgsbjtPrSdrCnt = dgsbjtPrSdrCnt; //의사수
        this.distance = distance;   // 거리
        this.drTotCnt = drTotCnt;   // 전체 의사 수
        this.XPos = XPos;   // x좌표
        this.YPos = YPos;   // y좌표
        this.medical_list = medical_list;
        this.dgsbjtCd=dgsbjtCd;
        this.sdrdgsCnt=sdrdgsCnt;
    }
    public String getSdrdgsCnt() {
        return sdrdgsCnt;
    }

    public void setSdrdgsCnt(String sdrdgsCnt) {
        this.sdrdgsCnt = sdrdgsCnt;
    }

    public ArrayList<String> getDgsbjtCd() {
        return dgsbjtCd;
    }

    public void setDgsbjtCd(ArrayList<String> dgsbjtCd) {
        this.dgsbjtCd = dgsbjtCd;
    }

    public String getMedical_list() {
        return medical_list;
    }

    public void setMedical_list(String medical_list) {
        if(this.medical_list == null)
            this.medical_list = medical_list;
        else
            this.medical_list = this.medical_list + ", " + medical_list;
    }

    public HospitalItem() {

    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setDgsbjtCdNm(ArrayList<String> dgsbjtCdNm) {
        this.dgsbjtCdNm = dgsbjtCdNm;
    }

    public void setDgsbjtPrSdrCnt(ArrayList<String> dgsbjtPrSdrCnt) {
        this.dgsbjtPrSdrCnt = dgsbjtPrSdrCnt;
    }

    public String getClCdNm() {
        return clCdNm;
    }

    public void setClCdNm(String clCdNm) {
        this.clCdNm = clCdNm;
    }

    public String getEstbDd() {
        return estbDd;
    }

    public void setEstbDd(String estbDd) {
        this.estbDd = estbDd;
    }

    public String getGdrCnt() {
        return gdrCnt;
    }

    public void setGdrCnt(String gdrCnt) {
        this.gdrCnt = gdrCnt;
    }

    public String getHospUrl() {
        return hospUrl;
    }

    public void setHospUrl(String hospUrl) {
        this.hospUrl = hospUrl;
    }

    public String getIntnCnt() {
        return intnCnt;
    }

    public void setIntnCnt(String intnCnt) {
        this.intnCnt = intnCnt;
    }

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }

    public String getResdntCnt() {
        return resdntCnt;
    }

    public void setResdntCnt(String resdntCnt) {
        this.resdntCnt = resdntCnt;
    }

    public String getSdrCnt() {
        return sdrCnt;
    }

    public void setSdrCnt(String sdrCnt) {
        this.sdrCnt = sdrCnt;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getYadmNm() {
        return yadmNm;
    }

    public void setYadmNm(String yadmNm) {
        this.yadmNm = yadmNm;
    }

    public String getYkiho() {
        return ykiho;
    }

    public void setYkiho(String ykiho) {
        this.ykiho = ykiho;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getDrTotCnt() {
        return drTotCnt;
    }

    public void setDrTotCnt(String drTotCnt) {
        this.drTotCnt = drTotCnt;
    }

    public String getXpos() {
        return XPos;
    }

    public void setXpos(String XPos) {
        this.XPos = XPos;
    }

    public String getYpos() {
        return YPos;
    }

    public void setYpos(String YPos) {
        this.YPos = YPos;
    }

    public ArrayList<String> getDgsbjtCdNm() {
        return dgsbjtCdNm;
    }

    public void setDgsbjtCdNm(String temp) {
        this.dgsbjtCdNm.add(temp);
    }

    public ArrayList<String> getDgsbjtPrSdrCnt() {
        return dgsbjtPrSdrCnt;
    }

    public void setDgsbjtPrSdrCnt(String temp) {
        this.dgsbjtPrSdrCnt.add(temp);
    }
}
