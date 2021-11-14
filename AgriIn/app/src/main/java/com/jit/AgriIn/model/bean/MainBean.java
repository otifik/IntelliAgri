package com.jit.AgriIn.model.bean;

/**
 * @author zxl on 2018/9/3.
 *         discription: 主界面logBean类
 */

public class MainBean {
    private int imageSource;
    private String imageDes;

    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }

    public String getImageDes() {
        return imageDes;
    }

    public void setImageDes(String imageDes) {
        this.imageDes = imageDes;
    }

    public MainBean(int imageSource, String imageDes) {
        this.imageSource = imageSource;
        this.imageDes = imageDes;
    }
}
