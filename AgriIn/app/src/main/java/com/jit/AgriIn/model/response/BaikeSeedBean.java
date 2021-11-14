package com.jit.AgriIn.model.response;

/**
 * @author crazyZhangxl on 2018/10/15.
 * Describe:
 */
public class BaikeSeedBean {
    /**
     * id : 1631
     * name : null
     * content : null
     * image : http://223.2.197.246:8088/planting/knowledge/a0f79d89-dcb3-4f5e-8c92-08f11c2acbc4.png
     * source : null
     * publish_time : 2019-05-07 16:00:13
     */

    private int id;
    private String name;
    private String content;
    private String image;
    private String source;
    private String publish_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }
}
