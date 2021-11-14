package com.jit.usercenter.serviceinterface;


import com.github.pagehelper.PageInfo;
import com.jit.usercenter.commons.pages.PageQO;
import com.jit.usercenter.commons.pages.PageVO;
import com.jit.usercenter.domain.Role;
import com.jit.usercenter.domain.User;
import com.jit.usercenter.dto.ResetPassword;
import com.jit.usercenter.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {
    PageInfo<UserDto> getList(Integer pageNum, Integer pageSize, Integer role);

    User getOneUserById(Integer userId);

    User getOneUserByTel(String tel);

    User getOneUserByUser(String username);


    User addUser(User userDO,String username);

    User resetInfo(User userDO,String username);

    Boolean deleteUserInfo(Integer userId,String username);

    List<Role> getAllRoles();

    PageVO<UserDto> getAllExperts(PageQO pageQO);

    PageVO<UserDto> getAllCustomers(PageQO pageQO);

    User addImage(MultipartFile image,String username) throws Exception;

}
