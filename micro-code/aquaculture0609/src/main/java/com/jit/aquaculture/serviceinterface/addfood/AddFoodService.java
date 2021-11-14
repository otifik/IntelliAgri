package com.jit.aquaculture.serviceinterface.addfood;

import com.jit.aquaculture.dto.FeedTemplateDto.FeedTemplateDto;
import com.jit.aquaculture.dto.addfood.AddFoodDto;

import java.util.List;

public interface AddFoodService {

    AddFoodDto<?> selectAllAddFood(String username);


    AddFoodDto<?> selectAddFood(Integer pageNum, Integer pageSize,String username);

    AddFoodDto<?> insert(String name, String batchNumber, List<String> pond, List<String> food, Double amount, String unit, List<String> time, String remarks, String username);

    AddFoodDto<?> delete(Integer id) throws Exception;

    AddFoodDto<?> update(Integer id, String name, String batchNumber, List<String> pond, List<String> food, Double amount, String unit, List<String> time, String remarks, String username);
}
