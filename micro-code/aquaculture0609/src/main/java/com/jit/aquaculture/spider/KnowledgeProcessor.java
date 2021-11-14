package com.jit.aquaculture.spider;


import com.jit.aquaculture.domain.knowledge.Knowledge;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.Date;
import java.util.List;


public class KnowledgeProcessor implements PageProcessor {

    //农业图谱
    //http://tupu.zgny.com.cn/zhongyetp_js_sucaizz/List.shtml
    public static final String URL_LIST = "http://www\\.shuichan\\.cc/article_list-125-134\\.html";
    public static final String URL_POST = " http://www\\.shuichan\\.cc/article_view-\\d{5}\\.html";

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setSleepTime(100)
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");

    @Override
    public void process(Page page) {
        //获取肥料详情的url地址//
        List<Selectable> list = page.getHtml().xpath("/html/body/center/table[2]/tbody/tr/td[1]/table[1]/tbody/tr[2]/td/table[1]/tbody/tr").nodes();
//        List<Selectable> list = page.getHtml().xpath("/html/body/center/table[4]/tbody/tr/td[1]/table[1]/tbody/tr[1]/td/h1").nodes();
        if (list.size() == 0) {
            //如果为空，表示这是肥料详情页，获取肥料相关信息，保存数据
            this.saveKnowledge(page);
        } else {
            //如果不为空，表示是列表页，解析出详情url地址，放到任务队列中
            for (Selectable selectable : list) {
                String detailUrl = selectable.links().toString();
                System.out.println("detailUrl " + detailUrl);
                page.addTargetRequest(detailUrl);
            }
        }
    }

    private void saveKnowledge(Page page) {
        Knowledge knowledge = Knowledge.of();

        knowledge.setName(page.getHtml().xpath("//body/center/table[4]/tbody//tbody/tr/td[@class='news']/h1/text()").toString());
        String content = null;
        String contentString = page.getHtml().xpath("//*[@id='font_word']").toString();
        if (contentString != null) {
            contentString.trim();

            content = contentString.substring(183, contentString.length() - 1);

            //content = contentString.replace("<fontid=\"font_word\"class=\"htd\"style=\"font-size:14px;font-family:宋体,Verdana,Arial,Helvetica,sans-serif;\">","");
            //
            // content = content.replaceAll("type\":\"CONTENT\",\"action\":\"SHOW\"} -->","");

        }

//        String img =  page.getHtml().xpath("//div[@class='conLeft']/div[@class='wenZi_02']").regex("<img src=\"/eweb/uploadfile/\\w+\\.\\w+\" border=\"0\">").toString();

//
//        if (content!=null) {
//            content = content.replace("<p>", "");
//            content = content.replace("</p>", "");
//            content = content.replace("<strong>", "");
//            content = content.replace("</strong>", "");
//            content = content.replace("</div>", "");
//            content = content.replace("<div class=\"wenZi_02\">", "");
//            if (img!=null){
//                content = content.replace(img,"");
//            }
//
//        }
        knowledge.setContent(content);
//        String image_src = page.getHtml().xpath("//div[@class='conLeft']//div[@class='wenZi_02']//img/@src").toString();
        String image = "";
//        if (image_src!=null){
//            image = "http://www.zgny.com.cn"+image_src;//图片源
//        }
        knowledge.setImage(image);
        String source = page.getHtml().xpath("/html/body/center/table[4]/tbody/tr/td[1]/table[1]/tbody/tr[2]/td/text()").toString();

        //只保留文章来源
        if (source != null) {
            System.out.println("============:" + source.length());
            source = source.substring(0, source.length() - 25);
        }

        knowledge.setSource(source);
        System.out.println(knowledge.getName() + " ----- " + knowledge.getContent() + "-----" + knowledge.getImage());
        knowledge.setPublish_time(new Date());
        page.putField("knowledge", knowledge);
    }

///html/body/center/table[4]/tbody/tr/td[1]/table[1]/tbody/tr[1]/td


    @Override
    public Site getSite() {
        return site;
    }

}
