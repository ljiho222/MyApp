package com.example.myapp;

import java.util.ArrayList;
import java.util.List;

public class Article {

    private String articleID;
    private String userID;
    private String userName;
    private String content;
    //private List<String> lovers;
    //private List<String> reporters;
    private String image;
    //private String endDate;
    //private int temp;

    Article() {
        userName="";
        userID = "";
        content = "";
        image = "";
        //lovers = new ArrayList<>();
        //reporters = new ArrayList<>();
    }

    Article(String articleID, String userID, String userName,String content, String image) {
        this.articleID = articleID;
        this.userID = userID;
        this.userName=userName;
        this.content = content;
        this.image = image;
       // this.temp = temp;
        //lovers = new ArrayList<>();
        //reporters = new ArrayList<>();
        //this.endDate = endDate;
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

    public void setContent(String content) {
        this.content = content;
    }

   /* public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }*/
/*public List<String> getLovers() {
        return lovers;
    }

    public void setLovers(List<String> lovers) {
        this.lovers = lovers;
    }

    public List<String> getReporters() {
        return reporters;
    }

    public void setReporters(List<String> reporters) {
        this.reporters = reporters;
    }*/

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

   /* public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean addLover(String loverID) {
        if (lovers.remove(loverID) == false) {
            lovers.add(loverID);
            return true;
        }
        return false;
    }

    public boolean addReporter(String reporterID) {
        if (reporters.contains(reporterID) == false) {
            reporters.add(reporterID);
            return true;
        }
        return false;
    }*/
}
