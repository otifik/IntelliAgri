package com.jit.authority.dto;

import lombok.Data;

@Data
public class DailyFixedDto {
    private Integer id;
    private String name;
    private Float count;
    private String unit;
    private Float price;
    private Float total;
    private String server;
    private String tel;
}
