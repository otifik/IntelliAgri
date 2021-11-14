package com.jit.AgriIn.uinew.third;

/**
 * Created by yuanyuan on 2016/12/29.
 */

public class TypeChangeEvent {
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentPos() {
        return contentPos;
    }

    public void setContentPos(int contentPos) {
        this.contentPos = contentPos;
    }

    String contentType;

    public TypeChangeEvent(String contentType, int contentPos) {
        this.contentType = contentType;
        this.contentPos = contentPos;
    }

    int contentPos;




}
