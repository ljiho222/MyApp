package com.example.myapp;


public class Pain {

    private String content;
    private String image;
    private String name;
    private String id;

    Pain(){
        content="";
        image="";
        name="";
        id="";
    }

    Pain(String content, String image, String name, String id) {

        this.content = content;
        this.image = image;
        this.name=name;
        this.id=id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
