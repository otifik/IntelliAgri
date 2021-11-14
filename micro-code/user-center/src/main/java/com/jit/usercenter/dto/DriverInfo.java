package com.jit.usercenter.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DriverInfo {
    private String id;
    private Integer belongUserId;//所属管理员id
    private String name;
    private String sex;
    private Date birthDay;
    private String tel;
    private String licenseNum;
    private String vehicleType;
    private String token;

}
