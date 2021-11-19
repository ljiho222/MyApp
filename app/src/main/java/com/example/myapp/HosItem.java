package com.example.myapp;

import java.io.Serializable;

public class HosItem implements Serializable {

    String gugun,address,entId,title,tel;

    public HosItem(String gugun, String address, String entId, String title, String tel) {
        this.gugun = gugun;
        this.address = address;
        this.entId = entId;
        this.title = title;
        this.tel = tel;

    }
    public HosItem() {

    }

    public String getGugun() {
        return gugun;
    }

    public void setGugun(String gugun) {
        this.gugun = gugun;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address;  }

    public String getEntId() { return entId; }

    public void setEntId(String entId) { this.entId = entId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getTel() { return tel; }

    public void setTel(String tel) { this.tel = tel; }
}
