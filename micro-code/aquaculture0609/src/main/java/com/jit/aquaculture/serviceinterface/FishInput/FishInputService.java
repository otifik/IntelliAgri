package com.jit.aquaculture.serviceinterface.FishInput;

import com.jit.aquaculture.dto.FishInputDto.FishInputDto;
import com.jit.aquaculture.dto.Input.InputDto;

import java.util.List;

public interface FishInputService {
   // InputDto<?> selectinputs();
      FishInputDto<?> selectfishinputs(Integer pageNum, Integer pageSize0,String username);
      FishInputDto<?> selectfishinputsall(String username);
      FishInputDto<?> insert(String type, Double amount, String date, List<String> pond, String unit, String batchNumber, String username);

      FishInputDto<?> update(Integer id, String type, Double amount, String date, List<String> pond, String unit, String batchNumber, String username) throws Exception;

      FishInputDto<?> delete(Integer id) throws Exception;

}
