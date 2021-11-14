package com.jit.aquaculture.serviceimpl.FeedTemplateServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.aquaculture.domain.FeedTemplate.FeedTemplate;
import com.jit.aquaculture.domain.FishInput.FishInput;
import com.jit.aquaculture.dto.FeedTemplateDto.FeedTemplateDto;
import com.jit.aquaculture.dto.FeedTemplateDto.FeedTemplatePageDto;
import com.jit.aquaculture.dto.FishInputDto.FishInputDto;
import com.jit.aquaculture.dto.FishInputDto.FishInputPageDto;
import com.jit.aquaculture.mapper.FeedTemplate.FeedTemplateMapper;
import com.jit.aquaculture.serviceinterface.FeedTemplate.FeedTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedTemplateServiceImpl implements FeedTemplateService {
    @Autowired
    private FeedTemplateMapper feedTemplateMapper;

    @Override
    public FeedTemplateDto<?> selectAllFeedTemplate(String username) {
        FeedTemplateDto feedTemplateDto=new FeedTemplateDto();
        List<FeedTemplate> feedTemplates = new ArrayList<>();
        feedTemplates= feedTemplateMapper.selectFeedTemplate(username);
        feedTemplateDto.setData(feedTemplates);
        feedTemplateDto.setMsg("查看成功！");
        feedTemplateDto.setStatus(HttpStatus.OK.value());
        feedTemplateDto.setTimestamp(System.currentTimeMillis());
        return feedTemplateDto;
    }

    @Override
    public FeedTemplateDto<?> selectFeedTemplateByName(String name,String username) {
        FeedTemplateDto feedTemplateDto=new FeedTemplateDto();
        FeedTemplate feedTemplate = feedTemplateMapper.selectFeedTemplateByName(name);


        feedTemplateDto.setData(feedTemplate);
        feedTemplateDto.setMsg("查看成功！");
        feedTemplateDto.setStatus(HttpStatus.OK.value());
        feedTemplateDto.setTimestamp(System.currentTimeMillis());

        return feedTemplateDto;
    }

    @Override
    public FeedTemplateDto<?> selectFeedTemplate(Integer pageNum, Integer pageSize,String username) {
   /*     FeedTemplateDto feedTemplateDto=new FeedTemplateDto();
        FeedTemplatePageDto feedTemplatePageDto = new FeedTemplatePageDto();
        System.out.println("original size: " + feedTemplateMapper.selectFeedTemplate().size());
        PageHelper.startPage(pageNum, pageSize);
        List<FeedTemplate> feedTemplates = feedTemplateMapper.selectFeedTemplate();
        System.out.println("pageNum:" + pageNum);
        System.out.println("pageSize:" + pageSize);
        System.out.println(feedTemplates.size());
        System.out.println(feedTemplates);
        PageInfo<FeedTemplate> pageInfo = new PageInfo<>(feedTemplates);
        feedTemplatePageDto.setFeedTemplates(feedTemplates);
        feedTemplatePageDto.setPageNum(pageNum);
        feedTemplatePageDto.setPageSize(pageSize);
        feedTemplatePageDto.setPages(pageInfo.getPages());
        feedTemplatePageDto.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        feedTemplateDto.setData(feedTemplatePageDto);
        feedTemplateDto.setMsg("查看成功！");
        feedTemplateDto.setStatus(HttpStatus.OK.value());
        feedTemplateDto.setTimestamp(System.currentTimeMillis());
        return feedTemplateDto;*/
        FeedTemplateDto feedTemplateDto=new FeedTemplateDto();
        FeedTemplatePageDto feedTemplatePageDto = new FeedTemplatePageDto();
        PageHelper.startPage(pageNum, pageSize);
        List<FeedTemplate> feedTemplates= feedTemplateMapper.selectFeedTemplate(username);
        PageInfo<FeedTemplate> pageInfo = new PageInfo<>(feedTemplates);
        feedTemplatePageDto.setFeedTemplates(feedTemplates);
        feedTemplatePageDto.setPageNum(pageNum);
        feedTemplatePageDto.setPageSize(pageSize);
        feedTemplatePageDto.setPages(pageInfo.getPages());
        feedTemplatePageDto.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        feedTemplateDto.setData(feedTemplatePageDto);
        feedTemplateDto.setMsg("查看成功！");
        feedTemplateDto.setStatus(HttpStatus.OK.value());
        feedTemplateDto.setTimestamp(System.currentTimeMillis());
        return feedTemplateDto;
    }

    @Override
    public FeedTemplateDto<?> insert(String name, String batchNumber, List<String> pond, List<String> food, Double amount, String unit, List<String> time, String remarks, String username) {
        System.out.println("enter insert");
        FeedTemplateDto feedTemplateDto = new FeedTemplateDto();
        FeedTemplate feedTemplate = new FeedTemplate();
        StringBuilder sbpond = new StringBuilder();
        for (String i : pond) {
            sbpond.append(i + ";");
        }
        sbpond.deleteCharAt(sbpond.length()-1);

        StringBuilder sbfood=new StringBuilder();
        for (String i : food) {
            sbfood.append(i + ";");
        }
        sbfood.deleteCharAt(sbfood.length()-1);

        StringBuilder sbtime =new StringBuilder();
        for (String S : time) {
            sbtime.append(S + ";");
        }
        sbtime.deleteCharAt(sbtime.length()-1);


        System.out.println("after sb");
        String pondStr = sbpond.toString();
        String foodStr=sbfood.toString();
        String timeStr=sbtime.toString();
       feedTemplate.setAmount(amount);
       feedTemplate.setBatchNumber(batchNumber);
       feedTemplate.setName(name);
       feedTemplate.setRemarks(remarks);
       feedTemplate.setPond(pondStr);
       feedTemplate.setFood(foodStr);
       feedTemplate.setTime(timeStr);
       feedTemplate.setUnit(unit);
       feedTemplate.setUsername(username);

        System.out.println(feedTemplate);

        System.out.println("before insert");
        feedTemplateMapper.insert(feedTemplate);
        System.out.println("after mapper");

        feedTemplateDto.setData(null);
        feedTemplateDto.setMsg("insert success！");
        feedTemplateDto.setStatus(HttpStatus.OK.value());
        feedTemplateDto.setTimestamp(System.currentTimeMillis());
        return feedTemplateDto;
    }

    @Override
    public FeedTemplateDto<?> delete(Integer id) throws Exception {
        FeedTemplateDto feedTemplateDto = new FeedTemplateDto();
      //  FeedTemplate feedTemplate = feedTemplateMapper.selectById(id);
        //if (fishInput == null) {
          //  throw new Exception("error! no fishinput in database!");
        //}

        feedTemplateMapper.deleteById(id);


        feedTemplateDto.setData(null);
        feedTemplateDto.setMsg("delete success！");
        feedTemplateDto.setStatus(HttpStatus.OK.value());
        feedTemplateDto.setTimestamp(System.currentTimeMillis());
        return feedTemplateDto;
    }

    @Override
    public FeedTemplateDto<?> update(Integer id, String name, String batchNumber, List<String> pond, List<String> food, Double amount, String unit, List<String> time, String remarks, String username) {
        System.out.println("enter update");
        FeedTemplateDto feedTemplateDto = new FeedTemplateDto();
        FeedTemplate feedTemplate = feedTemplateMapper.selectFeedTemplateById(id,username);
        if (pond != null) {
            StringBuilder sbpond = new StringBuilder();
            for (String i : pond) {
                sbpond.append(i + ";");

            }
            sbpond.deleteCharAt(sbpond.length()-1);
            String pondStr = sbpond.toString();
            feedTemplate.setPond(pondStr);

        }
        if (food != null) {
            StringBuilder sbfood=new StringBuilder();
            for (String i : food) {
                sbfood.append(i + ";");

            }
            sbfood.deleteCharAt(sbfood.length()-1);
            String foodStr=sbfood.toString();
            feedTemplate.setFood(foodStr);


        }
        if (food != null) {
            StringBuilder sbtime =new StringBuilder();
            for (String S : time) {
                sbtime.append(S + ";");

            }
            sbtime.deleteCharAt(sbtime.length()-1);

            String timeStr=sbtime.toString();
            feedTemplate.setTime(timeStr);

        }


        System.out.println("after sb");
        if (amount != null) {
            feedTemplate.setAmount(amount);
        }
        System.out.println(batchNumber);
        if (batchNumber != null) {
            feedTemplate.setBatchNumber(batchNumber);

        }
        if (name != null) {
            feedTemplate.setName(name);

        }
        if (remarks != null) {
            feedTemplate.setRemarks(remarks);

        }
        if (unit != null) {
            feedTemplate.setUnit(unit);

        }
        if (username != null) {
            feedTemplate.setUsername(username);
        }
        //name,batchNumber,pond,food, amount,unit,time,remarks,username
        System.out.println("before update");
        // fishinputMapper.insert(fishInput);

        System.out.println(feedTemplate);

        feedTemplateMapper.update(feedTemplate);
        System.out.println("after mapper");

        feedTemplateDto.setData(null);
        feedTemplateDto.setMsg("update success！");
        feedTemplateDto.setStatus(HttpStatus.OK.value());
        feedTemplateDto.setTimestamp(System.currentTimeMillis());
        return feedTemplateDto;
    }
}
