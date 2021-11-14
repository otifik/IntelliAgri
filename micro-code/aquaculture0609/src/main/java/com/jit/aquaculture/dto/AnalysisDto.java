package com.jit.aquaculture.dto;

import com.jit.aquaculture.domain.daily.DailyIncome;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AnalysisDto {
    //    private Integer type;
//    private Float total;
    private String startDate;
    private String endDate;
    private List<DataListDto> dataListDtos;
}
