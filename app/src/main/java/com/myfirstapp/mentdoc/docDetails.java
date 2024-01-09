package com.myfirstapp.mentdoc;

public class docDetails {
    String name,post,timing,image;

    public docDetails(String name, String post, String timing, String image) {
        this.name = name;
        this.post = post;
        this.timing = timing;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
