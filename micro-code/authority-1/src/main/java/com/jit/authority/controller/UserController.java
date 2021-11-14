package com.jit.authority.controller;

import com.jit.authority.domain.User;
import com.jit.authority.dto.ResetPassword;

import com.jit.authority.responseResult.result.ResponseResult;

import com.jit.authority.serviceinterface.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.reflections.Reflections.log;

@Api(value = "用户管理", tags = "用户基本信息管理")
@RestController
@ResponseResult
@RequestMapping("one")
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

    @RequestMapping(value = "/id", method = RequestMethod.GET)
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

    @RequestMapping(value = "/tel", method = RequestMethod.GET)
    User getOneUserbyTel(@RequestParam("tel") String tel) {
        log.info("getOneUserByTel========authority=====:   "+tel);
        return userService.getOneUserByTel(tel);
    }

    /**
     * 重置密码
     *
     * @param request       HttpServlet 请求
     * @param resetPassword 密码对象
     * @return 带有token的用户信息
     */
    @ApiOperation(value = "重置密码", notes = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "resetPassword", value = "密码对对象", required = true, dataType = "ResetPassword")
    })

    @RequestMapping(value = "/resetpassword", method = RequestMethod.PUT)
    Boolean resetPassword(HttpServletRequest request, @RequestBody ResetPassword resetPassword, HttpServletResponse response) {
        return userService.resetPassword(request, resetPassword, response);
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
            @ApiImplicitParam(name = "userDO", value = "用户信息", required = true, dataType = "User")
    })
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    User resetUserInfo(@RequestBody User userDO) {
        return userService.resetInfo(userDO);
    }



//    @ApiOperation(value = "分页获取全部专家", notes = "分页获取全部专家")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String")
//    })
//    @GetMapping("/all/experts")
//    PageVO<UserDto> getAllExperts(PageQO pageQO) {
//        return userService.getAllExperts(pageQO);
//    }


    @ApiOperation(value = "用户更新个人头像", notes = "用户更新个人头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public User addImage(MultipartFile image) throws Exception {
        return userService.addImage(image);
    }

}
