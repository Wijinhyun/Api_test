package com.prodoc.api_test;

import java.io.Serializable;

public class HospitalItemForCsv implements Serializable {

    private String ykiho, hospitalname, subject, pronum, totalnum, percent, xpos, ypos, sidocode,
            sidoname, sigungucode, sigunguname, addr, tel, url, park, distance;

    public String getDistance() { return distance; }

    public void setDistance(String distance) { this.distance = distance; }

    public String getYkiho() {
        return ykiho;
    }

    public void setYkiho(String ykiho) { this.ykiho = ykiho; }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPronum() { return pronum; }

    public void setPronum(String pronum) { this.pronum = pronum; }

    public String getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(String totalnum) {
        this.totalnum = totalnum;
    }

    public String getPercent() { return percent; }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getXpos() {
        return xpos;
    }

    public void setXpos(String xpos) {
        this.xpos = xpos;
    }

    public String getYpos() {
        return ypos;
    }

    public void setYpos(String ypos) {
        this.ypos = ypos;
    }

    public String getSidocode() {
        return sidocode;
    }

    public void setSidocode(String sidocode) {
        this.sidocode = sidocode;
    }

    public String getSidoname() {
        return sidoname;
    }

    public void setSidoname(String sidoname) {
        this.sidoname = sidoname;
    }

    public String getSigungucode() {
        return sigungucode;
    }

    public void setSigungucode(String sigungucode) {
        this.sigungucode = sigungucode;
    }

    public String getSigunguname() {
        return sigunguname;
    }

    public void setSigunguname(String sigunguname) {
        this.sigunguname = sigunguname;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPark() {
        return park;
    }

    public void setPark(String park) {
        this.park = park;
    }

}
