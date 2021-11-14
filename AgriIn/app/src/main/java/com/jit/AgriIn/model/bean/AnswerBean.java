package com.jit.AgriIn.model.bean;

public class AnswerBean {

    /**
     * content : string
     * id : 0
     * publishTime : 2019-05-31T09:15:33.325Z
     * question_id : 0
     * username : string
     */

    private String content;
    private int id;
    private String publishTime;
    private int question_id;
    private String username;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
