package com.jit.usercenter.controller;

import com.jit.usercenter.commons.pages.PageVO;
import com.jit.usercenter.dto.DriverInfo;
import com.jit.usercenter.dto.DriverNameTel;
import com.jit.usercenter.responseResult.result.ResponseResult;
import com.jit.usercenter.serviceimpl.DriverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@Api(tags = "司机/揽件员/配货员管理接口")
@RestController
@ResponseResult
@RequestMapping("driver")
public class DriverController {


    @Autowired
    private DriverService driverService;

    @ApiOperation(value = "手机号校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "物流企业管理员id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "driverTel", value = "司机手机号", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/checkDriverTel", method = RequestMethod.GET)
    public Boolean checkDriverTel(@RequestParam("userId") Integer userId, @RequestParam("driverTel") String driverTel) {
        driverService.checkDriverTel(userId, driverTel);
        return true;
    }

    @ApiOperation(value = "新增司机或揽件员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "物流企业管理员id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "司机或揽件员名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "tel", value = "手机号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别，男或女,非必填项", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "birthDate", value = "格式为yyyy-MM-dd,非必填项", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "licenseNum", value = "驾驶证号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "vehicleType", value = "驾驶车型", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "role", value = "用户角色，ROLE_DRIVER---司机；ROLE_COLLECTOR---揽件员；ROLE_LOADER---配货员", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/addDriver", method = RequestMethod.POST)
    public Boolean addDriver(@RequestParam("userId") Integer userId,
                             @RequestParam("name") String name,
                             @RequestParam("tel") String tel,
                             @RequestParam(value = "sex", defaultValue = "", required = false) String sex,
                             @RequestParam(value = "birthDate", defaultValue = "", required = false) String birthDate,
                             @RequestParam("licenseNum") String licenseNum,
                             @RequestParam("vehicleType") String vehicleType,
                             @RequestParam("role")String role) throws ParseException {
        driverService.add(userId, name, sex, birthDate, tel, licenseNum, vehicleType,role);
        return true;
    }

    @ApiOperation(value = "新增配货员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "物流企业管理员id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "配货员名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "tel", value = "手机号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别，男或女,非必填项", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "birthDate", value = "格式为yyyy-MM-dd,非必填项", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "licenseNum", value = "驾驶证号,非必填项", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "vehicleType", value = "驾驶车型,非必填项", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "role", value = "用户角色，ROLE_DRIVER---司机；ROLE_COLLECTOR---揽件员；ROLE_LOADER---配货员", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/addLoader", method = RequestMethod.POST)
    public Boolean addLoader(@RequestParam("userId") Integer userId,
                             @RequestParam("name") String name,
                             @RequestParam("tel") String tel,
                             @RequestParam(value = "sex", defaultValue = "", required = false) String sex,
                             @RequestParam(value = "birthDate", defaultValue = "", required = false) String birthDate,
                             @RequestParam("licenseNum") String licenseNum,
                             @RequestParam("vehicleType") String vehicleType,
                             @RequestParam("role")String role) throws ParseException {
        driverService.add(userId, name, sex, birthDate, tel, licenseNum, vehicleType,role);
        return true;
    }

    @ApiOperation(value = "编辑司机或揽件员信息，各字段类型同添加司机或揽件员接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "司机或揽件员id", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "司机或揽件员名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "tel", value = "手机号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别，男或女,非必填项", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "birthDate", value = "格式为yyyy-MM-dd,非必填项", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "licenseNum", value = "驾驶证号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "vehicleType", value = "驾驶车型", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "role", value = "用户角色，ROLE_DRIVER---司机；ROLE_COLLECTOR---揽件员；ROLE_LOADER---配货员", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/editDriver", method = RequestMethod.POST)
    public Boolean editDriver(@RequestParam("id") String id,
                              @RequestParam("name") String name,
                              @RequestParam("tel") String tel,
                              @RequestParam(value = "sex", defaultValue = "", required = false) String sex,
                              @RequestParam(value = "birthDate", defaultValue = "", required = false) String birthDate,
                              @RequestParam("licenseNum") String licenseNum,
                              @RequestParam("vehicleType") String vehicleType,
                              @RequestParam("role")String role) throws ParseException {
        driverService.edit(id, name, sex, birthDate, tel, licenseNum, vehicleType,role);
        return true;
    }
    @ApiOperation(value = "编辑配货员信息，各字段类型同添加配货员接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "司机或揽件员id", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "司机或揽件员名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "tel", value = "手机号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别，男或女,非必填项", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "birthDate", value = "格式为yyyy-MM-dd,非必填项", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "licenseNum", value = "驾驶证号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "vehicleType", value = "驾驶车型", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "role", value = "用户角色，ROLE_DRIVER---司机；ROLE_COLLECTOR---揽件员；ROLE_LOADER---配货员", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/editLoader", method = RequestMethod.POST)
    public Boolean editLoader(@RequestParam("id") String id,
                              @RequestParam("name") String name,
                              @RequestParam("tel") String tel,
                              @RequestParam(value = "sex", defaultValue = "", required = false) String sex,
                              @RequestParam(value = "birthDate", defaultValue = "", required = false) String birthDate,
                              @RequestParam("licenseNum") String licenseNum,
                              @RequestParam("vehicleType") String vehicleType,
                              @RequestParam("role")String role) throws ParseException {
        driverService.edit(id, name, sex, birthDate, tel, licenseNum, vehicleType,role);
        return true;
    }
    @ApiOperation(value = "删除司机或揽件员或配货员信息，新增接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "司机或揽件员或配货员id", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/deleteDriver", method = RequestMethod.DELETE)
    public Boolean deleteDriver(@RequestParam("id") String id) {
        driverService.delete(id);
        return true;
    }

    @ApiOperation(value = "搜索司机或揽件员或配货员信息，根据司机或揽件员或配货员搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "物流企业管理员id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "司机或揽件员或配货员名", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/findDriverList", method = RequestMethod.GET)
    public List<DriverInfo> findDriverList(@RequestParam("userId") Integer userId, @RequestParam("name") String name) {

        return driverService.findDriverList(userId, name);
    }

    @ApiOperation(value = "分页获取所有司机或揽件员或配货员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "物流企业管理员id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "获取第几页数据，默认为1，表示获取第1页数据,非必填项", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的条数,默认为10，非必填项", paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    public PageVO<DriverInfo> getAllByPage(@RequestParam("userId") Integer userId,
                                           @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                           @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {

        return driverService.getAllByPage(userId, page, pageSize);
    }

    @ApiOperation(value = "获取所有司机或揽件员或配货员姓名和手机号。新增接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "物流企业管理员id", required = true, paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "/getAllNameTel", method = RequestMethod.GET)
    public List<DriverNameTel> getAllNameTel(@RequestParam("userId") Integer userId) {

        return driverService.getAllNameTel(userId);
    }


}
