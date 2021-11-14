package com.jit.usercenter.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.usercenter.commons.pages.PageVO;
import com.jit.usercenter.domain.User;
import com.jit.usercenter.dto.CustomPasswordEncoder;
import com.jit.usercenter.dto.DriverInfo;
import com.jit.usercenter.dto.DriverNameTel;

import com.jit.usercenter.mapper.UserMapper;
import com.jit.usercenter.responseResult.ResultCode;
import com.jit.usercenter.responseResult.exceptions.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DriverService {

    public static final Logger logger = LoggerFactory.getLogger(DriverService.class);

    @Autowired
    private UserMapper driverInfoMapper;

    public void checkDriverTel(Integer userId, String driverTel) {
        logger.info("invoke checkDriverTel() function!");
        List<DriverInfo> driverInfo = driverInfoMapper.getByDriverTel(userId, driverTel);
        if (driverInfo != null) {
            throw new BusinessException(ResultCode.PARAM_DRIVER_MOBILE_INVALID);
        }
    }

    /*public void getByDriverName(Integer userId, String driverName){
        logger.info("invoke getByDriverName() function!");
        DriverInfo driverInfo = driverInfoMapper.getByDriverName(userId,driverName);
        if (driverInfo != null)
        {
            throw new GlobalException(ResultEnum.PARAM_NAME_AREADY_EXIST);
        }
    }*/

    @Transactional
    public User add(Integer userId, String name, String sex, String birthDate, String tel, String licenseNum, String vehicleType,String role) throws ParseException {
        logger.info("invoke add() function!");
        //用户名为空
        if (name.trim().isEmpty()) {
            throw new BusinessException(ResultCode.DATA_IS_NULL);
        }
        //用户名已存在
        List<User> users = driverInfoMapper.selectList(new EntityWrapper<User>().eq("username", name));
        if (null != users && users.size() > 0) {
            throw new BusinessException(ResultCode.USER_ISEXITE);
        }
        //用户电话已存在
        List<User> users_ = driverInfoMapper.selectList(new EntityWrapper<User>().eq("tel", tel));
        if (null != users_ && users_.size() > 0) {
            throw new BusinessException(ResultCode.USER_TEL_ISEXITE);
        }
        /*DriverInfo driverFromDB = driverInfoMapper.getByDriverTelName(userId,tel,name);
        if (driverFromDB != null){
            throw new GlobalException(ResultEnum.PARAM_NAME_ALREADY_USED);
        }*/
        //DriverInfo driverInfo = new DriverInfo();
        User driverInfo = User.of();
        //driverInfo.setId(CommonUtil.createNo());
        driverInfo.setUsername(name);

        if (birthDate != null && !birthDate.isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            driverInfo.setBirthDay(formatter.parse(birthDate));
        }

        driverInfo.setTel(tel);
        driverInfo.setSex(sex);
        driverInfo.setBelongUserId(userId);
        driverInfo.setLicenseNum(licenseNum);
        driverInfo.setVehicleType(vehicleType);
        //注册密码加密
        String passWord = "123456";
        PasswordEncoder encoder = new CustomPasswordEncoder();
        driverInfo.setPassword(encoder.encode(passWord));
        driverInfo.setRole(role);
        int flag = driverInfoMapper.insert(driverInfo);
        if (flag > 0) {
            return driverInfo;
        } else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }


    @Transactional
    public void edit(String id, String name, String sex, String birthDate, String tel, String licenseNum, String vehicleType,String role) throws ParseException {
        logger.info("invoke edit() function!");

        Date birthDateInDB = null;
        if (birthDate != null && birthDate.trim().length() > 0) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            birthDateInDB = formatter.parse(birthDate);
        }
        Integer id_int = Integer.parseInt(id);
        User driverInfo = User.of();
        driverInfo.setId(id_int).setUsername(name).setSex(sex).setBirthDay(birthDateInDB).setTel(tel).setLicenseNum(licenseNum).setVehicleType(vehicleType).setRole(role);
        driverInfoMapper.updateById(driverInfo);
    }

    public void delete(String id) {
        logger.info("invoke delete() function!");
        Integer id_int = Integer.parseInt(id);
        User driverInfo = driverInfoMapper.selectById(id_int);

        if (driverInfo != null) {
            driverInfoMapper.deleteById(id_int);
        } else {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }
    }

    public List<DriverInfo> findDriverList(Integer userId, String name) {
        List<DriverInfo> driverInfoList = driverInfoMapper.findDriverList(name, userId);
        return driverInfoList;
    }

    public PageVO<DriverInfo> getAllByPage(Integer userId, Integer page, Integer pageSize) {
        Integer newPage = page;
        Page<DriverInfo> pages = PageHelper.startPage(newPage, pageSize);
        List<DriverInfo> users = driverInfoMapper.getAllDriverByPage(userId);
        return PageVO.build(users);
    }

    public List<DriverNameTel> getAllNameTel(Integer userId) {
        logger.info("invoke getAllNameTel() function!");


        List<DriverNameTel> driverList = driverInfoMapper.getAllNameTel(userId);
        return driverList;
    }


}
