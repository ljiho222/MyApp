package com.example.myapp;

import java.io.Serializable;

public class HosItem implements Serializable {

    String gugun, apiNewAddress, apiDongName, apiTel;

    //public HosItem(String gugun, String address, String entId, String title, String tel) {
    //this.gugun = gugun;
    public HosItem(String apiNewAddress, String apiDongName, String apiTel) {
        this.apiNewAddress = apiNewAddress;
        this.apiDongName = apiDongName;
        this.apiTel = apiTel;

    }

    public HosItem() {

    }

    //public String getGugun() { return gugun; }

    //public void setGugun(String gugun) { this.gugun = gugun; }

    public String getApiNewAddress() {
        return apiNewAddress;
    }

    public void setApiNewAddress(String apiNewAddress) {
        this.apiNewAddress = apiNewAddress;
    }

    public String getApiDongName() {
        return apiDongName;
    }

    public void setApiDongName(String apiDongName) {
        this.apiDongName = apiDongName;
    }

    public String getApiTel() {
        return apiTel;
    }

    public void setApiTel(String apiTel) {
        this.apiTel = apiTel;
    }
}
