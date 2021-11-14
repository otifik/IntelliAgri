package com.jit.usercenter.dto;


import lombok.Data;

import java.util.Date;

@Data
public class DatePairDto {
    private Integer id;
    private Date startDate;
    private Date endDate;
}
