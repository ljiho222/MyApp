package com.example.myapp;

import java.util.ArrayList;
import java.util.List;

public class Article {

    private String articleID;
    private String userID;
    private String userName;
    private String content;
    private String age;
    private String title;
    private String name;
    private String sex;

    //private List<String> lovers;
    private List<String> reporters;
    private String image;
    private String endDate;
    //private int temp;

    Article() {
        userName="";
        userID = "";
        content = "";
        image = "";
        age="";
        title="";
        name="";
        sex="";
        endDate="";
        //lovers = new ArrayList<>();
        reporters = new ArrayList<>();
    }

    Article(String articleID, String userID, String userName,String content, String age, String title, String name, String sex, String image,String endDate) {
        this.articleID = articleID;
        this.userID = userID;
        this.userName=userName;
        this.content = content;
        this.age=age;
        this.title=title;
        this.name=name;
        this.sex=sex;
        this.image = image;
        this.endDate=endDate;

    }

    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() { return userName;  }

    public void setUserName(String userName) { this.userName = userName; }

    public String getContent() {
        return content;
    }

    public void setContent(String content) { this.content = content; }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEndDate(){return endDate;}

    public void setEndDate(String endDate) { this.endDate = endDate;  }
}
