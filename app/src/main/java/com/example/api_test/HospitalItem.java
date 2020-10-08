package com.example.api_test;

import java.util.ArrayList;

public class HospitalItem {

    private String addr, clCdNm, estbDd, gdrCnt, hospUrl, intnCnt, postNo, resdntCnt, sdrCnt, telno, yadmNm, ykiho;
    private ArrayList<String> dgsbjtCdNm = new ArrayList<String>(), dgsbjtPrSdrCnt = new ArrayList<String>();


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

    public HospitalItem(String addr, String clCdNm, String estbDd, String gdrCnt, String hospUrl, String intnCnt, String postNo, String resdntCnt, String sdrCnt, String telno, String yadmNm, String ykiho, ArrayList<String> dgsbjtCdNm, ArrayList<String> dgsbjtPrSdrCnt) {
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
        this.dgsbjtCdNm = dgsbjtCdNm;
        this.dgsbjtPrSdrCnt = dgsbjtPrSdrCnt;
    }

    public HospitalItem() {

    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
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
}
