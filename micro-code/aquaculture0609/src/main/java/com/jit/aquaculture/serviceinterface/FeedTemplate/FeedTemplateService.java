package com.jit.aquaculture.serviceinterface.FeedTemplate;

import com.jit.aquaculture.domain.FeedTemplate.FeedTemplate;
import com.jit.aquaculture.dto.FeedTemplateDto.FeedTemplateDto;
import com.jit.aquaculture.dto.FishInputDto.FishInputDto;
import io.swagger.models.auth.In;

import java.util.List;

public interface FeedTemplateService {


    FeedTemplateDto<?> selectAllFeedTemplate(String username);

    FeedTemplateDto<?> selectFeedTemplateByName(String name,String username);

    FeedTemplateDto<?> selectFeedTemplate(Integer pageNum, Integer pageSize,String username);

    FeedTemplateDto<?> insert(String name, String batchNumber, List<String> pond, List<String> food, Double amount, String unit, List<String> time, String remarks, String username);

    FeedTemplateDto<?> delete(Integer id) throws Exception;

    FeedTemplateDto<?> update(Integer id, String name, String batchNumber, List<String> pond, List<String> food, Double amount, String unit, List<String> time, String remarks, String username);



}
