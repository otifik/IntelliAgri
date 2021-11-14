package com.jit.AgriIn.model.response;

/**
 * @author crazyZhangxl on 2018/10/26.
 * Describe:
 */
public class BaikeDrugBean {

    /**
     * id : 1287
     * type : 杀菌剂
     * name : 吡嘧磷
     * content :
     内吸性有机磷杀菌又名定菌磷，对高等动物低毒。对兔眼睛和皮肤无刺激作用。中等毒性。对真菌中的白粉菌有特效，喷雾，土壤施药或拌种，0．01~0．03%浓度的药液喷雾，可防治果树、蔬菜、花卉及其他经济作物的白粉病，土施或拌种，药剂能被植物根部吸收，向茎叶传导以防治病害。
     * fromSource : 农博网
     * publishTime : 2019-04-26 15:33:17
     */

    private int id;
    private String type;
    private String name;
    private String content;
    private String fromSource;
    private String publishTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getFromSource() {
        return fromSource;
    }

    public void setFromSource(String fromSource) {
        this.fromSource = fromSource;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
