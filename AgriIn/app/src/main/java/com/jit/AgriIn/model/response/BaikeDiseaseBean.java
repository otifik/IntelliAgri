package com.jit.AgriIn.model.response;

/**
 * @author crazyZhangxl on 2018/10/26.
 * Describe:
 */
public class BaikeDiseaseBean {

    /**
     * id : 23
     * big_category : 茄果类
     * small_category : 番茄
     * diseaseName : 番茄缺镁病的防治
     * symptom : 第二穗果的叶片叶脉间开始褪绿变黄，并向周围蔓延，开始时主茎仍为绿色，最后全株变黄，直至干枯死亡。
     * treatment : 要适当增加含镁的肥料，像钾镁肥、氧化镁、硫酸镁，这些肥料可溶于水极易被吸收。当然，你也可以在西红柿生长的过程种喷施含镁的叶面肥。
     * image : http://img8.agronet.com.cn/Users/100/581/426/201711171003508414.jpg
     * publishTime : 2019-04-11 14:21:18
     * source : 中国农业网
     */

    private int id;
    private String big_category;
    private String small_category;
    private String diseaseName;
    private String symptom;
    private String treatment;
    private String image;
    private String publishTime;
    private String source;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBig_category() {
        return big_category;
    }

    public void setBig_category(String big_category) {
        this.big_category = big_category;
    }

    public String getSmall_category() {
        return small_category;
    }

    public void setSmall_category(String small_category) {
        this.small_category = small_category;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
