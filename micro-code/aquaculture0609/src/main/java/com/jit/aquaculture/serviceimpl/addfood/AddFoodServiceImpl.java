package com.jit.aquaculture.serviceimpl.addfood;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.aquaculture.domain.Reagent.Reagent;
import com.jit.aquaculture.domain.addfood.AddFood;
import com.jit.aquaculture.dto.addfood.AddFoodDto;
import com.jit.aquaculture.dto.addfood.AddFoodPageDto;
import com.jit.aquaculture.mapper.addfood.AddFoodMapper;
import com.jit.aquaculture.serviceinterface.addfood.AddFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddFoodServiceImpl implements AddFoodService {
    @Autowired
    private AddFoodMapper addFoodMapper;

    @Override
    public AddFoodDto<?> selectAllAddFood(String username) {
        AddFoodDto<List<AddFood>> addFoodDto=new AddFoodDto<>();
        List<AddFood> addFoods = new ArrayList<>();
        addFoods= addFoodMapper.selectAddFood(username);
        addFoodDto.setData(addFoods);
        addFoodDto.setMsg("查看成功！");
        addFoodDto.setStatus(HttpStatus.OK.value());
        addFoodDto.setTimestamp(System.currentTimeMillis());
        return addFoodDto;
    }


    @Override
    public AddFoodDto<?> selectAddFood(Integer pageNum, Integer pageSize,String username) {
        AddFoodDto<AddFoodPageDto> addFoodDto=new AddFoodDto<>();
        AddFoodPageDto addFoodPageDto = new AddFoodPageDto();

        PageHelper.startPage(pageNum, pageSize);
        List<AddFood> addFoods= addFoodMapper.selectAddFood(username);
        PageInfo<AddFood> pageInfo = new PageInfo<>(addFoods);

        addFoodPageDto.setAddFoods(addFoods);
        addFoodPageDto.setPageNum(pageNum);
        addFoodPageDto.setPageSize(pageSize);
        addFoodPageDto.setPages(pageInfo.getPages());
        addFoodPageDto.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));


        addFoodDto.setData(addFoodPageDto);
        addFoodDto.setMsg("查看成功！");
        addFoodDto.setStatus(HttpStatus.OK.value());
        addFoodDto.setTimestamp(System.currentTimeMillis());
        return addFoodDto;
    }

    @Override
    public AddFoodDto<?> insert(String name, String batchNumber, List<String> pond, List<String> food, Double amount, String unit, List<String> time, String remarks, String username) {
        System.out.println("enter insert");
        AddFoodDto addFoodDto = new AddFoodDto();
        AddFood addFood = new AddFood();
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
        addFood.setAmount(amount);
        addFood.setBatchNumber(batchNumber);
        addFood.setName(name);
        addFood.setRemarks(remarks);
        addFood.setPond(pondStr);
        addFood.setFood(foodStr);
        addFood.setTime(timeStr);
        addFood.setUnit(unit);
        addFood.setUsername(username);

        System.out.println(addFood);

        System.out.println("before insert");
        addFoodMapper.insert(addFood);
        System.out.println("after mapper");

        addFoodDto.setData(null);
        addFoodDto.setMsg("insert success！");
        addFoodDto.setStatus(HttpStatus.OK.value());
        addFoodDto.setTimestamp(System.currentTimeMillis());
        return addFoodDto;
    }

    @Override
    public AddFoodDto<?> delete(Integer id) throws Exception {
        AddFoodDto<Void> addFoodDto = new AddFoodDto<>();
        //  FeedTemplate feedTemplate = feedTemplateMapper.selectById(id);
        //if (fishInput == null) {
        //  throw new Exception("error! no fishinput in database!");
        //}

        addFoodMapper.deleteById(id);


        addFoodDto.setData(null);
        addFoodDto.setMsg("delete success！");
        addFoodDto.setStatus(HttpStatus.OK.value());
        addFoodDto.setTimestamp(System.currentTimeMillis());
        return addFoodDto;
    }

    @Override
    public AddFoodDto<?> update(Integer id, String name, String batchNumber, List<String> pond, List<String> food, Double amount, String unit, List<String> time, String remarks, String username) {
        System.out.println("enter update");
        AddFoodDto addFoodDto = new AddFoodDto();
        AddFood addFood = addFoodMapper.selectAddFoodById(id,username);
        if (pond != null) {
            StringBuilder sbpond = new StringBuilder();
            for (String i : pond) {
                sbpond.append(i + ";");

            }
            sbpond.deleteCharAt(sbpond.length()-1);
            String pondStr = sbpond.toString();
            addFood.setPond(pondStr);

        }
        if (food != null) {
            StringBuilder sbfood=new StringBuilder();
            for (String i : food) {
                sbfood.append(i + ";");

            }
            sbfood.deleteCharAt(sbfood.length()-1);
            String foodStr=sbfood.toString();
            addFood.setFood(foodStr);


        }
        if (food != null) {
            StringBuilder sbtime =new StringBuilder();
            for (String S : time) {
                sbtime.append(S + ";");

            }
            sbtime.deleteCharAt(sbtime.length()-1);

            String timeStr=sbtime.toString();
            addFood.setTime(timeStr);

        }


        System.out.println("after sb");
        if (amount != null) {
            addFood.setAmount(amount);
        }
        System.out.println(batchNumber);
        if (batchNumber != null) {
            addFood.setBatchNumber(batchNumber);

        }
        if (name != null) {
            addFood.setName(name);

        }
        if (remarks != null) {
            addFood.setRemarks(remarks);

        }
        if (unit != null) {
            addFood.setUnit(unit);

        }
        if (username != null) {
            addFood.setUsername(username);
        }
        //name,batchNumber,pond,food, amount,unit,time,remarks,username
        System.out.println("before update");
        // fishinputMapper.insert(fishInput);

        System.out.println(addFood);

        addFoodMapper.update(addFood);
        System.out.println("after mapper");

        addFoodDto.setData(null);
        addFoodDto.setMsg("update success！");
        addFoodDto.setStatus(HttpStatus.OK.value());
        addFoodDto.setTimestamp(System.currentTimeMillis());
        return addFoodDto;
    }
}
