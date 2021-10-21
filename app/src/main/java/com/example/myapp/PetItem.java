package com.example.myapp;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.Serializable;

public class PetItem implements Serializable {

    String happenPlace,kindCd,sexCd,popfile;

    public PetItem(String happenPlace, String kindCd, String sexCd, String popfile) {
        this.happenPlace = happenPlace;
        this.kindCd = kindCd;
        this.sexCd = sexCd;
        this.popfile = popfile;
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

}
