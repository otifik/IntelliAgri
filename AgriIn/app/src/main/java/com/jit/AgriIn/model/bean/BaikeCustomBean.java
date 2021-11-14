package com.jit.AgriIn.model.bean;

/**
 * @author crazyZhangxl on 2018/10/12.
 * Describe:
 */
public class BaikeCustomBean {
    /**
     * description : string
     * id : 0
     * image : string
     * kind : 0
     * name : string
     * subKind : string
     */

    private String description;
    private int id;
    private String image;
    private int kind;
    private String title;
    private String subKind;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getTitle() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public String getSubKind() {
        return subKind;
    }

    public void setSubKind(String subKind) {
        this.subKind = subKind;
    }
}
