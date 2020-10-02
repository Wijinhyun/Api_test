package com.example.api_test;

public class HospitalItem {

    String addr, clCdNm, estbDd, gdrCnt, hospUrl, intnCnt, postNo, resdntCnt, sdrCnt, telno, yadmNm, ykiho;

    public HospitalItem(String addr, String clCdNm, String estbDd, String gdrCnt, String hospUrl, String intnCnt, String postNo, String resdntCnt, String sdrCnt, String telno, String yadmNm, String ykiho) {
        this.addr = addr;
        this.clCdNm = clCdNm;
        this.estbDd = estbDd;
        this.gdrCnt = gdrCnt;
        this.hospUrl = hospUrl;
        this.intnCnt = intnCnt;
        this.postNo = postNo;
        this.resdntCnt = resdntCnt;
        this.sdrCnt = sdrCnt;
        this.telno = telno;
        this.yadmNm = yadmNm;
        this.ykiho = ykiho;
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
