package com.jit.aquaculture.serviceimpl.FishInputServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.aquaculture.domain.FishInput.FishInput;
import com.jit.aquaculture.domain.Input.input;
import com.jit.aquaculture.dto.FishInputDto.FishInputDto;
import com.jit.aquaculture.dto.FishInputDto.FishInputPageDto;
import com.jit.aquaculture.dto.Input.InputDto;
import com.jit.aquaculture.mapper.FishInput.FishInputMapper;
import com.jit.aquaculture.mapper.Input.InputMapper;
import com.jit.aquaculture.serviceinterface.FishInput.FishInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FishInputServiceImpl implements FishInputService {

    @Autowired
    private FishInputMapper fishinputMapper;
    private String type;
    private Double amount;
    private String date;
    private List<Integer> pond;
    private String batchNumber;

    @Override
    public FishInputDto<?> selectfishinputs(Integer pageNum, Integer pageSize,String username) {
        FishInputDto fishinputDto=new FishInputDto();
        FishInputPageDto fishInputPageDto = new FishInputPageDto();
        PageHelper.startPage(pageNum, pageSize);
        List<FishInput> fishinputs= fishinputMapper.selectFishInputs(username);
        PageInfo<FishInput> pageInfo = new PageInfo<>(fishinputs);
        fishInputPageDto.setFishinputs(fishinputs);
        fishInputPageDto.setPageNum(pageNum);
        fishInputPageDto.setPageSize(pageSize);
        fishInputPageDto.setPages(pageInfo.getPages());
        fishInputPageDto.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        fishinputDto.setData(fishInputPageDto);
        fishinputDto.setMsg("查看成功！");
        fishinputDto.setStatus(HttpStatus.OK.value());
        fishinputDto.setTimestamp(System.currentTimeMillis());
        return fishinputDto;

    }

    @Override
    public FishInputDto<?> selectfishinputsall(String username) {
            FishInputDto FishInputDto = new FishInputDto();
            List<FishInput> inputs =  fishinputMapper.selectFishInputs(username);
            FishInputDto.setData(inputs);
            FishInputDto.setMsg("查看成功！");
            FishInputDto.setStatus(HttpStatus.OK.value());
            FishInputDto.setTimestamp(System.currentTimeMillis());
            return FishInputDto;
        }




    @Override
    public FishInputDto<?> update(Integer id, String type, Double amount, String date, List<String> pond, String unit, String batchNumber, String username) throws Exception {
        FishInputDto fishInputDto = new FishInputDto();
        FishInput fishInput = fishinputMapper.selectById(id,username);
        if (fishInput == null) {
            throw new Exception("error! no fishinput in database!");
        }
        if (type != null) {
            fishInput.setType(type);
        }
        if (amount != null) {
            fishInput.setAmount(amount);
        }
        if (date != null) {
            fishInput.setDate(date);
        }
        if (pond != null) {
            StringBuilder sb = new StringBuilder();
            for (String s : pond) {
                sb.append(s + ";");
            }
            sb.deleteCharAt(sb.length()-1);
            fishInput.setPond(sb.toString());
        }
        if (batchNumber != null) {
            fishInput.setBatchNumber(batchNumber);
        }
        if (unit != null) {
            fishInput.setUnit(unit);
        }
        if (username != null) {
            fishInput.setUsername(username);
        }
        System.out.println("before update");
        fishinputMapper.update(fishInput);
        System.out.println("after update");


        fishInputDto.setData(null);
        fishInputDto.setMsg("update success！");
        fishInputDto.setStatus(HttpStatus.OK.value());
        fishInputDto.setTimestamp(System.currentTimeMillis());
        return fishInputDto;
    }

    @Override
    public FishInputDto<?> delete(Integer id) throws Exception {
        FishInputDto fishInputDto = new FishInputDto();
        /*FishInput fishInput = fishinputMapper.selectById(id);
        if (fishInput == null) {
            throw new Exception("error! no fishinput in database!");
        }
*/
        fishinputMapper.deleteById(id);


        fishInputDto.setData(null);
        fishInputDto.setMsg("delete success！");
        fishInputDto.setStatus(HttpStatus.OK.value());
        fishInputDto.setTimestamp(System.currentTimeMillis());
        return fishInputDto;
    }

    @Override
    public FishInputDto<?> insert(String type, Double amount, String date, List<String> pond, String unit, String batchNumber, String username) {
        FishInputDto fishInputDto = new FishInputDto();
        FishInput fishInput = new FishInput();
        StringBuilder sb = new StringBuilder();

        for (String i : pond) {
            sb.append(i + ";");
        }
        sb.deleteCharAt(sb.length()-1);
        String pondStr = sb.toString();
        fishInput.setType(type);
        fishInput.setAmount(amount);
        fishInput.setDate(date);
        fishInput.setPond(pondStr);
        fishInput.setBatchNumber(batchNumber);
        fishInput.setUnit(unit);
        fishInput.setUsername(username);
        fishinputMapper.insert(fishInput);
        fishInputDto.setData(null);
        fishInputDto.setMsg("insert success！");
        fishInputDto.setStatus(HttpStatus.OK.value());
        fishInputDto.setTimestamp(System.currentTimeMillis());
        return fishInputDto;
    }


}
