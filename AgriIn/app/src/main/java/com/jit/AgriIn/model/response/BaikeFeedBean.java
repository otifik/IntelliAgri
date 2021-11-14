package com.jit.AgriIn.model.response;

/**
 * @author crazyZhangxl on 2018/10/26.
 * Describe:
 */
public class BaikeFeedBean {

    /**
     * id : 4450
     * category :  栽培技术
     * name : 蘑菇、莲藕、蒜苗保鲜技术
     * content :
     一、蘑菇的贮藏保鲜
     　　1．激动素保鲜法。用0.01%的6-氨基嘌呤溶液浸泡鲜菇10―15分钟，取出沥干后贮存。
     　　2．焦亚硫酸钠处理法。先用0.01%焦亚硫酸钠水溶液漂洗蘑菇3―5分钟然后用0.1%的焦亚硫酸钠水浸泡半小时，捞出沥干，装进塑料袋内贮藏，在室温10―15℃条件下保鲜效果较好。
     　　二、莲藕的贮藏方法
     　　1．薄膜帐藏法：用塑料薄膜贮藏帐将莲藕盖好，但不宜密封，且要隔天透一次帐。
     　　2．泥土埋藏法：先用砖砌或用木板等围成埋藏坑，然后一层莲藕一层土，堆5―6层，再在上面覆盖10厘米的细土。贮藏用土，应以细软带潮手捏不成团为宜，贮藏时，应将藕按顺序排放好，以免折断。如在水泥地库房埋藏，坑底应先用木板或竹架垫起10厘米，底部用药物消毒，然后铺一层厚约10厘米的细土，贮藏方法同上。如在室外露地埋藏，应选地势高而背阳避光的地方，将土、藕相间层层堆成斜坡形或宝塔形，再用土将藕全部盖严，周围挖好排水沟。
     　　三、蒜苗的贮藏保鲜
     　　1．冷藏法。将蒜苗充分预冷后，装入筐或板条箱内，或直接堆垛在贮藏的货架上，使库温控制在1℃左右。
     　　2．气调法。
     　　①快速降氧法：将蒜苗堆放好并用塑料帐密封后，连续抽氧灌氮3―4次，将帐内含的氧量降到1―3%，在贮藏过程中使帐内含氧量控制在3%，二氧化碳在10%左右为好。
     　　②自然降氧法：蒜苗进帐后，立即将塑料帐密封，帐内的氧气由蒜苗自行吸收，使氧分压逐渐下降到2―4%；以后每天测定调节，使氧分压保持在所需要的范围内。
     　　③充二氧化碳法：在蒜苗堆放好并用塑料帐密封后，先吸出帐内少量气体，使帐内氧气与二氧化碳含量基本相同。随着帐内含氧量的下降，应用消石灰吸收二氧化碳，使其相应下降。以后含氧量宜控制在1―3%，二氧化碳10%，温度14―16℃。
     * image : null
     * source : 中华粮网
     * publish_time : 2019-04-26 14:28:16
     */

    private int id;
    private String category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
