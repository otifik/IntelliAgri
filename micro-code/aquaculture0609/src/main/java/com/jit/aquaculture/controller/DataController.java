package com.jit.aquaculture.controller;

import com.jit.aquaculture.responseResult.result.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "养殖大数据管理", description = "水产养殖大数据管理")
@ResponseResult
@RestController
//@EnableEurekaClient
@RequestMapping("/show")
public class DataController {



}
