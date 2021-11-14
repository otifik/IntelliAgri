package com.jit.usercenter.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.usercenter.commons.pages.PageQO;
import com.jit.usercenter.commons.pages.PageVO;
import com.jit.usercenter.commons.util.ImageUtils;
import com.jit.usercenter.domain.Role;
import com.jit.usercenter.domain.User;
import com.jit.usercenter.domain.UserRole;
import com.jit.usercenter.dto.ResetPassword;
import com.jit.usercenter.dto.UserDto;

import com.jit.usercenter.mapper.RoleMapper;
import com.jit.usercenter.mapper.UserMapper;
import com.jit.usercenter.mapper.UserRoleMapper;
import com.jit.usercenter.responseResult.ResultCode;
import com.jit.usercenter.responseResult.exceptions.BusinessException;
import com.jit.usercenter.serviceinterface.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Value("${image.user.path}")
    private String user_path;

    @Value("${image.user.url}")
    private String image_url;

    @Value("${image.url}")
    private String url;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;




    private final String TOKEN_HEADER = "Authorization";

    @Override
    public PageInfo<UserDto> getList(Integer pageNum, Integer pageSize,Integer role) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserDto> users = userMapper.selectAllUserByRole(role);
        return new PageInfo(users);
    }

    /**
     * 根据id获取某一个用户的基本信息
     *
     * @param userId
     * @return
     */
    @Override
    public User getOneUserById(Integer userId) {
        User userDO = userMapper.selectOne(User.of().setId(userId));
        if (userDO == null) {
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }

        return userDO;

    }

    /**
     * 根据手机号码取某一个用户的基本信息
     *
     * @param tel
     * @return
     */
    @Override
    public User getOneUserByTel(String tel) {
        User userDO = userMapper.selectOne(User.of().setTel(tel));
        //log.info("getOneUserByTel:   "+tel);
        if (userDO == null) {
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        log.info("========getOneUserbyTel===end===");
        return userDO;

    }

    /**
     * 获取某一个用户的基本信息
     *
     * @return
     */
    @Override
    public User getOneUserByUser(String username) {
        //String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userDto = userMapper.selectOne(User.of().setUsername(username));
        return userDto;
    }



    /**
     * 新增用户
     *
     * @param userDO
     * @return
     */
    @Override
    public User addUser(User userDO,String username) {
       // String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer adminId = userMapper.selectOne(User.of().setUsername(username)).getId();
        if (1 != userRoleMapper.selectOne(UserRole.of().setUserId(adminId)).getRoleId()) {//只有管理员有增加用户的权限
            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
        }
        Integer flag = userMapper.insert(userDO);
        if (flag <= 0) {
            throw new BusinessException(ResultCode.DATA_IS_WRONG);
        }
        return userDO;


    }

    /**
     * 修改用户基本信息
     *
     * @param userDO 用户信息对象
     * @return
     */
    @Override
    public User resetInfo(User userDO,String username) {
        //String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User isExist = userMapper.selectOne(User.of().setUsername(username));
        if (null == isExist) {
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        if (null == userDO.getId()) {
            throw new BusinessException(ResultCode.DATA_IS_WRONG);
        }

        User user = userDO;
        userMapper.updateById(userDO);
        User returnUser = userMapper.selectOne(User.of().setUsername(username));
        return returnUser;

    }

    /**
     * 删除某一个用户
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public Boolean deleteUserInfo(Integer userId,String username) {
        //String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer adminId = userMapper.selectOne(User.of().setUsername(username)).getId();
        if (1 != userRoleMapper.selectOne(UserRole.of().setUserId(adminId)).getRoleId()) {//只有管理员有删除用户权限
            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
        }
        User userDO = userMapper.selectOne(User.of().setId(userId));
        if (null == userDO) {
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        Integer flag = userMapper.deleteById(userId);
        if (flag > 0) {
            return true;
        } else {
            throw new BusinessException(ResultCode.DATA_IS_WRONG);
        }

    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = roleMapper.selectList(null);
        return roles;
    }

    @Override
    public PageVO<UserDto> getAllExperts(PageQO pageQO) {
        Page<UserDto> page = PageHelper.startPage(pageQO.getPageNum(), pageQO.getPageSize());
        List<UserDto> users = userMapper.selectAllUserByRole(2);
        return PageVO.build(users);

    }

    @Override
    public PageVO<UserDto> getAllCustomers(PageQO pageQO) {
        Page<UserDto> page = PageHelper.startPage(pageQO.getPageNum(), pageQO.getPageSize());
        List<UserDto> users = userMapper.selectAllUserByRole(3);
        return PageVO.build(users);
    }

    /**
     * 用户增加个人头像
     *
     * @param image
     * @return
     * @throws Exception
     */
    @Override
    public User addImage(MultipartFile image,String username) throws Exception {
        //String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.selectOne(User.of().setUsername(username));

        if (image != null) {
            String fileName = ImageUtils.ImgReceive(image, user_path);
            user.setImage(url + image_url + fileName);
        }
        int flag = userMapper.updateById(user);
        return user;
    }
}
