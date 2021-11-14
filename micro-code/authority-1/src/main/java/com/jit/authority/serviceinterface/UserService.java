package com.jit.authority.serviceinterface;


import com.github.pagehelper.PageInfo;
import com.jit.authority.commons.pages.PageQO;
import com.jit.authority.commons.pages.PageVO;
import com.jit.authority.domain.Role;
import com.jit.authority.domain.User;
import com.jit.authority.dto.ResetPassword;
import com.jit.authority.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {
    PageInfo<UserDto> getList(Integer pageNum, Integer pageSize,Integer role);

    User getOneUserById(Integer userId);

    User getOneUserByTel(String tel);

    User getOneUserByUser();

    Boolean resetPassword(HttpServletRequest request, ResetPassword resetPassword, HttpServletResponse response);

    User addUser(User userDO);

    User resetInfo(User userDO);

    Boolean deleteUserInfo(Integer userId);

    List<Role> getAllRoles();

    PageVO<UserDto> getAllExperts(PageQO pageQO);

    PageVO<UserDto> getAllCustomers(PageQO pageQO);

    User addImage(MultipartFile image) throws Exception;

}
