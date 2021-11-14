package com.jit.AgriIn.model.response;

/**
 * @author crazyZhangxl on 2018/10/26.
 * Describe:
 */
public class BaikeProductBean {

    /**
     * id : 286
     * name : 海藻小麦肥18-10-12
     * company : 青岛海大生物集团有限公司
     * type : 生物肥
     * content : 　　【技术指标】N-P2O5-K2O≧16-16-8、18-10-12，海藻提取物≧10% 　　【主要功能】 　　1、缓释加长效。 　　2、生根还壮苗。 　　3、抗寒又抗旱。 　　4、粒饱秃尖少。 　　【产品规格】40kg/袋   　　40-60公斤/亩。因各地土壤状况及种植作物差异较大，具体使用量请酌情增减。   　　小麦、玉米、花生、棉花、水稻等经济作物及大田作物。
     * crop_use : 　　小麦、玉米、花生、棉花、水稻等经济作物及大田作物。
     */

    private int id;
    private String name;
    private String company;
    private String type;
    private String content;
    private String crop_use;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCrop_use() {
        return crop_use;
    }

    public void setCrop_use(String crop_use) {
        this.crop_use = crop_use;
    }
}
