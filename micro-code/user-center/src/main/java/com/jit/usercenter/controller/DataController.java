package com.jit.usercenter.controller;

import com.jit.usercenter.dto.ShowDto;
import com.jit.usercenter.responseResult.result.ResponseResult;
import com.jit.usercenter.serviceinterface.ShowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "用户大数据展示接口")
@RestController
@ResponseResult
@RequestMapping("show")
public class DataController {
    @Autowired
    private ShowService showService;

    @ApiOperation(value = "获取用户数据", notes = "获取用户数据")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public List<ShowDto> getUserNumber(){
        return showService.getShowData();
    }
}
