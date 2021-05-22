package com.ace.ace_iitp.DataItems;

public class ImageItems {

    //use string to store url if downloaded from firebase
    private String img;
    private String order;

    public ImageItems() {
    }

    public ImageItems(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public String getOrder() {
        return order;
    }
}
