package com.example.myapp;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.Serializable;

public class PetItem implements Serializable {

    String happenPlace, kindCd, sexCd, popfile, processState, noticeSdt, specialMark, noticeNo;

    public PetItem(String happenPlace, String kindCd, String sexCd, String popfile, String processState, String noticeSdt, String specialMark, String noticeNo) {
        this.happenPlace = happenPlace;
        this.kindCd = kindCd;
        this.sexCd = sexCd;
        this.popfile = popfile;
        this.processState = processState;
        this.noticeSdt = noticeSdt;
        this.specialMark = specialMark;
        this.noticeNo = noticeNo;
    }

    public PetItem() {

    }

    public String getHappenPlace() {
        return happenPlace;
    }

    public void setHappenPlace(String happenPlace) {
        this.happenPlace = happenPlace;
    }

    public String getKindCd() {
        return kindCd;
    }

    public void setKindCd(String kindCd) {
        this.kindCd = kindCd;
    }

    public String getSexCd() {
        return sexCd;
    }

    public void setSexCd(String sexCd) {
        this.sexCd = sexCd;
    }

    public String getPopfile() { return popfile; }

    public void setPopfile(String popfile) { this.popfile = popfile; }

    public String getProcessState() { return processState; }

    public void setProcessState(String processState) { this.processState = processState; }

    public String getNoticeSdt() { return noticeSdt; }

    public void setNoticeSdt(String noticeSdt) { this.noticeSdt = noticeSdt; }

    public  String getSpecialMark() { return specialMark; }

    public void setSpecialMark(String specialMark) { this.specialMark = specialMark; }

    public String getNoticeNo() { return noticeNo; }

    public  void setNoticeNo(String noticeNo) { this.noticeNo = noticeNo;}


}
