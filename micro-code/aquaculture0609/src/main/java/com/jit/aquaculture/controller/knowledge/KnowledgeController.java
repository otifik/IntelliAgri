package com.jit.aquaculture.controller.knowledge;


import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.spider.KnowledgeLine;
import com.jit.aquaculture.spider.KnowledgeProcessor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

@Api(value = "knowledge", description = "爬虫相关")
@ResponseResult
@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {


    @Autowired
    private KnowledgeLine knowledgeLine;


    @ApiOperation(value = "爬取网站数据", notes = "knowledge")

    @RequestMapping(value = "/crawl", method = RequestMethod.POST)
    public void crawInfo() {
//        SpiderKnowledge();
//        SpiderTechnology();
//        SpiderPesticide();
//        SpiçderFertilizer();

    }

    /**
     * 百科知识爬取
     */
    public void SpiderKnowledge() {
        long startTime, endTime;
        System.out.println("【爬虫开始】...");
        startTime = System.currentTimeMillis();

        Spider spider = Spider.create(new KnowledgeProcessor());
        spider.addUrl("http://www.shuichan.cc/article_list-125-134.html");
        spider.addPipeline(knowledgeLine);
        spider.thread(5);
        spider.run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】，耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");

    }
}
