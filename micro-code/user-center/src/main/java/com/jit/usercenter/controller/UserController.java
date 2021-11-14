package com.jit.usercenter.controller;

import com.jit.usercenter.domain.User;
import com.jit.usercenter.dto.ResetPassword;
import com.jit.usercenter.responseResult.result.ResponseResult;
import com.jit.usercenter.serviceinterface.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Api(value = "用户管理", tags = "用户基本信息管理")
@RestController
//@ResponseResult
//@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;




    /**
     * 用户获取个人信息
     *
     * @return
     */
    @ApiOperation(value = "用户获取个人信息", notes = "用户获取个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int")
    })

    @RequestMapping(value = "/one/id", method = RequestMethod.GET)
    User getOneUserbyUser(@RequestParam("userId") Integer userId) {
        return userService.getOneUserById(userId);
    }


    /**
     * 根据手机号码获取用户信息
     *
     * @param tel
     * @return
     */
    @ApiOperation(value = "根据手机号码获取用户信息", notes = "根据手机号码获取某一用户的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "tel", value = "手机号码", required = true, dataType = "String")
    })

    @RequestMapping(value = "/one/tel", method = RequestMethod.GET)
    User getOneUserbyTel(@RequestParam("tel") String tel) {
        log.info("========getOneUserbyTel==start====");
        return userService.getOneUserByTel(tel);

    }




    /**
     * 更新用户信息
     *
     * @param userDO
     * @return
     */
    @ApiOperation(value = "更新用户信息", notes = "用户更新个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userDO", value = "用户信息", required = true, dataType = "User"),
            @ApiImplicitParam(name = "username", value = "当前登录用户名", required = true, dataType = "String")
    })
    @RequestMapping(value = "/one/update", method = RequestMethod.PUT)
    User resetUserInfo(@RequestBody User userDO,@RequestParam("username")String username) {
        return userService.resetInfo(userDO,username);
    }





    @ApiOperation(value = "用户更新个人头像", notes = "用户更新个人头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/one/image", method = RequestMethod.POST)
    public User addImage(MultipartFile image,@RequestParam("username") String username) throws Exception {
        return userService.addImage(image,username);
    }

}
