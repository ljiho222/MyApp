package com.example.myapp;

public class Comment {
    private String commentID;
    private String userName;
    private String content;
    private String endDate;

    Comment() {
        userName = "";
        content = "";
        endDate="";
    }


    Comment(String commentID, String userName, String content, String endDate) {
        this.commentID = commentID;
        this.userName = userName;
        this.content = content;
        this.endDate = endDate;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName){ this.userName = userName;}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


}
