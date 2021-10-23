package com.example.myapp;

import java.io.Serializable;

public class User implements Serializable {
    public String userName;
    public String uid;

    public User(){
        userName="";
        this.uid =new String();
    }

    public User(String userName,String  uid)
    {
        this.userName = userName;
        this.uid = uid;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        uid = uid;
    }
}
