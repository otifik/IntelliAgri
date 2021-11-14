package com.jit.aquaculture.controller;

import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.User;
import com.jit.aquaculture.domain.knowledge.Knowledge;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(value = "test", description = "test")
@ResponseResult
@RestController
@RequestMapping("/")
public class FeignTestController {
    @Autowired
    private UserFeignClient userFeignClient;
    /**
     * 获取所有百科知识
     *
     * @return
     */
    @ApiOperation(value = "获取tel", notes = "tel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "tel", value = "tel", required = true, dataType = "String")
    })
    @RequestMapping(value = "one",method = RequestMethod.GET)
    public User getAll(@RequestParam("tel") String tel) {

        User user = userFeignClient.getOneUserbyTel(tel);
        return user;
    }

}
