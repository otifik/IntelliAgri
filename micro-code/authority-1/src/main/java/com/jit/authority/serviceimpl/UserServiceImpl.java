package com.jit.authority.serviceimpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.authority.commons.pages.PageQO;
import com.jit.authority.commons.pages.PageVO;
import com.jit.authority.commons.util.ImageUtils;
import com.jit.authority.domain.Role;
import com.jit.authority.mapper.RoleMapper;
import com.jit.authority.responseResult.ResultCode;
import com.jit.authority.domain.User;
import com.jit.authority.domain.UserRole;
import com.jit.authority.dto.ResetPassword;

import com.jit.authority.dto.UserDto;
import com.jit.authority.jwtsecurity.bean.JwtProperty;
import com.jit.authority.jwtsecurity.util.CustomPasswordEncoder;
import com.jit.authority.jwtsecurity.util.JwtUtil;
import com.jit.authority.mapper.UserMapper;
import com.jit.authority.mapper.UserRoleMapper;

import com.jit.authority.responseResult.exceptions.BusinessException;
import com.jit.authority.serviceinterface.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

import static org.reflections.Reflections.log;

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


    @Autowired
    private JwtProperty jwtProperty;

    private final String TOKEN_HEADER = "Authorization";

    @Override
    public PageInfo<UserDto> getList(Integer pageNum, Integer pageSize,Integer role) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserDto> users = userMapper.selectAllUserByRole(role);
        return new PageInfo(users);
    }

    /**
     * ??????id????????????????????????????????????
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
     * ???????????????????????????????????????????????????
     *
     * @param tel
     * @return
     */
    @Override
    public User getOneUserByTel(String tel) {
        User userDO = userMapper.selectOne(User.of().setTel(tel));
        log.info("getOneUserByTel:   "+tel);
        if (userDO == null) {
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }

        return userDO;

    }

    /**
     * ????????????????????????????????????
     *
     * @return
     */
    @Override
    public User getOneUserByUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userDto = userMapper.selectOne(User.of().setUsername(username));
        return userDto;
    }


    /**
     * ????????????
     *
     * @param request
     * @param resetPassword ????????????
     * @return ????????????token???UserDTO??????
     */
    @Override
    public Boolean resetPassword(HttpServletRequest request, ResetPassword resetPassword, HttpServletResponse response) {

        String authorization = request.getHeader(TOKEN_HEADER);
        String username = JwtUtil.getUsernameFromToken(authorization, jwtProperty);
        if (StringUtils.isEmpty(username)) {
            throw new BusinessException(ResultCode.TOKEN_INVALID);
        }
        User user = userMapper.selectOne(User.of().setUsername(username));
        if (null == user) {
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        CustomPasswordEncoder encoder = new CustomPasswordEncoder();
        if (!encoder.matches(resetPassword.getOldPassword(), user.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_IS_ERROR);
        }
        if (encoder.matches(resetPassword.getNewPassword(), user.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_SAME);

        }
        user.setPassword(encoder.encode(resetPassword.getNewPassword()));
        user.setRegister_time(new Date());

        int updateRes = userMapper.updateById(user);
        if (updateRes <= 0) {
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        UserDto userDto = UserDto.of();
        BeanUtils.copyProperties(user, userDto);


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return true;
//        final String token = createToken(username,jwtProperty);
//
//        return userDto.setToken(token);

    }

    /**
     * ????????????
     *
     * @param userDO
     * @return
     */
    @Override
    public User addUser(User userDO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer adminId = userMapper.selectOne(User.of().setUsername(username)).getId();
        if (1 != userRoleMapper.selectOne(UserRole.of().setUserId(adminId)).getRoleId()) {//???????????????????????????????????????
            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
        }
        Integer flag = userMapper.insert(userDO);
        if (flag <= 0) {
            throw new BusinessException(ResultCode.DATA_IS_WRONG);
        }
        return userDO;


    }

    /**
     * ????????????????????????
     *
     * @param userDO ??????????????????
     * @return
     */
    @Override
    public User resetInfo(User userDO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
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
     * ?????????????????????
     *
     * @param userId ??????id
     * @return
     */
    @Override
    public Boolean deleteUserInfo(Integer userId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer adminId = userMapper.selectOne(User.of().setUsername(username)).getId();
        if (1 != userRoleMapper.selectOne(UserRole.of().setUserId(adminId)).getRoleId()) {//????????????????????????????????????
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
     * ????????????????????????
     *
     * @param image
     * @return
     * @throws Exception
     */
    @Override
    public User addImage(MultipartFile image) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.selectOne(User.of().setUsername(username));

        if (image != null) {
            String fileName = ImageUtils.ImgReceive(image, user_path);
            user.setImage(url + image_url + fileName);
        }
        int flag = userMapper.updateById(user);
        return user;
    }
}
