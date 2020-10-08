package com.example.api_test;

public class HospitalItemDetail {
    String dgsbjtCdNm, dgsbjtPrSdrCnt;

    public HospitalItemDetail(String dgsbjtCdNm, String dgsbjtPrSdrCnt) {
        this.dgsbjtCdNm = dgsbjtCdNm; //진료과목 코드
        this.dgsbjtPrSdrCnt = dgsbjtPrSdrCnt; //의사 수
    }

    public HospitalItemDetail() {

    }

    public String getDgsbjtCdNm() {
        return dgsbjtCdNm;
    }

    public void setDgsbjtCdNm(String dgsbjtCdNm) { this.dgsbjtCdNm = dgsbjtCdNm;  }

    public String getDgsbjtPrSdrCnt() { return dgsbjtPrSdrCnt; }

    public void setDgsbjtPrSdrCnt(String clCdNm) {
        this.dgsbjtPrSdrCnt = dgsbjtPrSdrCnt;
    }

}
