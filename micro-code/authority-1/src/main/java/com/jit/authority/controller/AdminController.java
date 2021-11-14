package com.jit.authority.controller;

import com.github.pagehelper.PageInfo;
import com.jit.authority.domain.Role;
import com.jit.authority.domain.User;
import com.jit.authority.dto.UserDto;
import com.jit.authority.responseResult.result.ResponseResult;
import com.jit.authority.serviceinterface.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "管理员管理", tags = "管理员基本信息管理")
@RestController
@RequestMapping("admin")
@ResponseResult
public class AdminController {
    @Autowired
    private UserService userService;

    /**
     * 管理员分页获取全部用户
     *
     * @param pageNum  页码
     * @param pageSize 每页显示条数
     * @return 用户列表
     */
    @ApiOperation(value = "根据角色分页获取全部用户", notes = "管理员根据角色分页获取全部用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "起始页码", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, dataType = "int"),
            @ApiImplicitParam(name = "role", value = "用户角色，1：管理员；2：普通用户；3：专家用户", required = true, dataType = "int")
    })
    @GetMapping("lists")
    PageInfo<UserDto> getLists(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,@RequestParam("role")Integer role) {
        return userService.getList(pageNum, pageSize,role);
    }

    /**
     * 新增用户信息
     *
     * @param userDO
     * @return
     */
    @ApiOperation(value = "新增用户信息", notes = "管理员增加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userDO", value = "用户信息", required = true, dataType = "User")
    })
    @PostMapping("add")
    User addUser(@RequestBody User userDO) {
        return userService.addUser(userDO);
    }

    /**
     * 管理员删除用户
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "管理员删除用户", notes = "管理员删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int")
    })
    @DeleteMapping("/{userId}")
    Boolean deleteUserInfo(@PathVariable Integer userId) {
        return userService.deleteUserInfo(userId);
    }

    /**
     * 根据id获取用户信息
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据id获取用户信息", notes = "根据id获取某一用户的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int")
    })
    @GetMapping("/id")
    User getOneUserbyId(@RequestParam("userId") Integer userId) {
        return userService.getOneUserById(userId);
    }


    /**
     * 获取所有角色
     * @return
     */
    @ApiOperation(value = "获取所有角色", notes = "获取所有角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String")
    })
    @GetMapping("/roles")
    List<Role> getRoles() {
        return userService.getAllRoles();
    }
}
