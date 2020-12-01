package com.example.api_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Cluster_item implements ClusterItem {

    private final LatLng mPosition;

    public LatLng getmPosition() {
        return mPosition;
    }

    public String getYkiho() {
        return ykiho;
    }

    public void setYkiho(String ykiho) {
        this.ykiho = ykiho;
    }

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

    public String getPronum() {
        return pronum;
    }

    public void setPronum(String pronum) {
        this.pronum = pronum;
    }

    public String getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(String totalnum) {
        this.totalnum = totalnum;
    }

    public String getPercent() {
        return percent;
    }

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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    private String ykiho, hospitalname, subject, pronum, totalnum, percent, xpos, ypos, sidocode,
            sidoname, sigungucode, sigunguname, addr, tel, url, park, distance;

    private int pandan;

    public int getPandan() {
        return pandan;
    }

    public void setPandan(int pandan) {
        this.pandan = pandan;
    }

    public Cluster_item (double lat, double lng, String percent, String hospitalname, String subject, String addr, String distance,
                         String pronum, String totalnum, String ypos, String xpos, String tel, String park, String url, int pandan) {
        mPosition = new LatLng(lat, lng);
        this.percent = percent;
        this.hospitalname = hospitalname;
        this.subject = subject;
        this.addr = addr;
        this.distance = distance;
        this.pronum = pronum;
        this.totalnum = totalnum;
        this.ypos = ypos;
        this.xpos = xpos;
        this.tel = tel;
        this.park = park;
        this.url = url;
        this.pandan = pandan;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Nullable
    @Override
    public String getTitle() {
        return null;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return null;
    }
}