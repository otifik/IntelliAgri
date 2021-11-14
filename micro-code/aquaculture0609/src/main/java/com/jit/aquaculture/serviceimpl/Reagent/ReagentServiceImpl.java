package com.jit.aquaculture.serviceimpl.Reagent;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.aquaculture.domain.FishInput.FishInput;
import com.jit.aquaculture.domain.Reagent.Reagent;
import com.jit.aquaculture.dto.FishInputDto.FishInputDto;
import com.jit.aquaculture.dto.FishInputDto.FishInputPageDto;
import com.jit.aquaculture.dto.Reagent.ReagentDto;
import com.jit.aquaculture.dto.Reagent.ReagentPageDto;
import com.jit.aquaculture.mapper.FishInput.FishInputMapper;
import com.jit.aquaculture.mapper.Reagent.ReagentMapper;
import com.jit.aquaculture.serviceinterface.Reagent.ReagentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReagentServiceImpl  implements ReagentService {
    @Autowired
    private ReagentMapper reagentMapper;
    @Override
    public ReagentDto<?> selectreagent(Integer pageNum, Integer pageSize,String username) {
        ReagentDto reagentDto=new ReagentDto();
        ReagentPageDto reagentPageDto = new ReagentPageDto();
        PageHelper.startPage(pageNum, pageSize);
        List<Reagent> reagents= reagentMapper.selectreagent(username);
        PageInfo<Reagent> pageInfo = new PageInfo<>(reagents);
        reagentPageDto.setReagents(reagents);
        reagentPageDto.setPageNum(pageNum);
        reagentPageDto.setPageSize(pageSize);
        reagentPageDto.setPages(pageInfo.getPages());
        reagentPageDto.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        reagentDto.setData(reagentPageDto);
        reagentDto.setMsg("查看成功！");
        reagentDto.setStatus(HttpStatus.OK.value());
        reagentDto.setTimestamp(System.currentTimeMillis());
        return reagentDto;
    }

    @Override
    public ReagentDto<?> selectreagentall(String username) {
        ReagentDto reagentDto = new ReagentDto();
        List<Reagent> inputs =  reagentMapper.selectreagent(username);
        reagentDto.setData(inputs);
        reagentDto.setMsg("查看成功！");
        reagentDto.setStatus(HttpStatus.OK.value());
        reagentDto.setTimestamp(System.currentTimeMillis());
        return reagentDto;
    }

    @Override
    public ReagentDto<?> insert(String unit, List<String> batchNumber, List<String> pond, String reagent, Double amount, List<String> time, String remarks, String username) {
        System.out.println("enter insert");
        ReagentDto reagentDto = new ReagentDto();
        Reagent r = new Reagent();
        r.setAmount(amount);
        r.setUnit(unit);
        StringBuilder sbbn=new StringBuilder();
        for (String S : batchNumber) {
            sbbn.append(S + ";");
        }
        sbbn.deleteCharAt(sbbn.length()-1);
        r.setBatchNumber(sbbn.toString());

        StringBuilder sbpond=new StringBuilder();
        for (String S : pond) {
            sbpond.append(S + ";");
        }
        sbpond.deleteCharAt(sbpond.length()-1);
        r.setPond(sbpond.toString());

        r.setReagent(reagent);

        StringBuilder sbtime=new StringBuilder();
        for (String S : time) {
            sbtime.append(S + ";");
        }
        sbtime.deleteCharAt(sbtime.length()-1);
        r.setTime(sbtime.toString());

        r.setUsername(username);
        r.setRemarks(remarks);

         System.out.println("before insert");
        reagentMapper.insert(r);
        System.out.println("after mapper");
        reagentDto.setData(null);
        reagentDto.setMsg("insert success！");
        reagentDto.setStatus(HttpStatus.OK.value());
        reagentDto.setTimestamp(System.currentTimeMillis());
        return reagentDto;
    }

    @Override
    public ReagentDto<?> update(Integer id, String unit, List<String> batchNumber, List<String> pond, String reagent, Double amount, List<String> time, String remarks, String username) throws Exception {
        System.out.println("enter reagent update");
        ReagentDto reagentDto = new ReagentDto();
        Reagent r = reagentMapper.selectById(id,username);
        if (unit != null) {
            r.setUnit(unit);
        }
        if (batchNumber != null) {
            StringBuilder sbbn=new StringBuilder();
            for (String S : batchNumber) {
                sbbn.append(S + ";");
            }
            sbbn.deleteCharAt(sbbn.length()-1);
            r.setBatchNumber(sbbn.toString());

        }
        if (pond != null) {
            StringBuilder sbpond=new StringBuilder();
            for (String S : pond) {
                sbpond.append(S + ";");
            }
            sbpond.deleteCharAt(sbpond.length()-1);
            r.setPond(sbpond.toString());

        }
        if (reagent != null) {
            r.setReagent(reagent);

        }
        if (amount != null) {
            r.setAmount(amount);
        }
        if (time != null) {
            StringBuilder sbtime=new StringBuilder();
            for (String S : time) {
                sbtime.append(S + ";");
            }
            sbtime.deleteCharAt(sbtime.length()-1);
            r.setTime(sbtime.toString());

        }
        if (remarks != null) {
            r.setRemarks(remarks);
        }
        if (username != null) {
            r.setUsername(username);
        }
        System.out.println("before update");
        System.out.println(r);
        reagentMapper.update(r);
        System.out.println("after mapper");
        reagentDto.setData(null);
        reagentDto.setMsg("update success！");
        reagentDto.setStatus(HttpStatus.OK.value());
        reagentDto.setTimestamp(System.currentTimeMillis());
        return reagentDto;

    }

    @Override
    public ReagentDto<?> delete(Integer id) throws Exception {
        ReagentDto reagentDto = new ReagentDto();
     /*   Reagent reagent = reagentMapper.selectById(id);
        if (reagent == null) {
            throw new Exception("error! no reagent in database!");
        }*/

        reagentMapper.deleteById(id);


        reagentDto.setData(null);
        reagentDto.setMsg("delete success！");
        reagentDto.setStatus(HttpStatus.OK.value());
        reagentDto.setTimestamp(System.currentTimeMillis());
        return reagentDto;
    }
}
