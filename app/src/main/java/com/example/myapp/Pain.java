package com.example.myapp;

public class Pain {

    private String content;
    private String image;

    Pain(){
        content="";
        image="";
    }

    Pain(String content, String image) {

        this.content = content;
        this.image = image;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

}
