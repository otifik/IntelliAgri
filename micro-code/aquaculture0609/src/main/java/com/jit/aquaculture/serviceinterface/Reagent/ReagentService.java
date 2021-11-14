package com.jit.aquaculture.serviceinterface.Reagent;

import com.jit.aquaculture.dto.FishInputDto.FishInputDto;
import com.jit.aquaculture.dto.Reagent.ReagentDto;

import java.util.List;

public interface ReagentService {
    ReagentDto<?> selectreagent(Integer pageNum, Integer pageSize,String username);
    ReagentDto<?> selectreagentall(String username);
    ReagentDto<?> insert(String unit, List<String> batchNumber, List<String> pond, String reagent, Double amount, List<String> time, String remarks, String username);
    ReagentDto<?> update(Integer id, String unit, List<String> batchNumber, List<String> pond, String reagent, Double amount, List<String> time, String remarks, String username) throws Exception;
    ReagentDto<?> delete(Integer id) throws Exception;


}
