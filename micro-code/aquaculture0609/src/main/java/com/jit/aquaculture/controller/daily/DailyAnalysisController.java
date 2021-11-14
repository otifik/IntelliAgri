package com.jit.aquaculture.controller.daily;

import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.daily.DailyIncome;
import com.jit.aquaculture.dto.AnalysisDto;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.daily.DailyIncomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "analysis", description = " 统计分析")
@ResponseResult
@RestController
@RequestMapping("analysis")
public class DailyAnalysisController {
    @Autowired
    private DailyIncomeService dailyIncomeService;

    @ApiOperation(value = "根据日期获取所有成本列表（包括固定成本和日常投入）", notes = "根据日期获取所有成本（包括固定成本和日常投入）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "startDate", value = "起始日期", required = true, dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = true, dataType = "String")
    })
    @GetMapping("cost/list")
    PageVO<DailyIncome> getCostsByDate(PageQO pageQO, String startDate, String endDate) {
        return dailyIncomeService.getAllCostsByDate(pageQO, startDate, endDate);
    }

    @ApiOperation(value = "根据日期获取所有收益列表", notes = "根据日期获取所有收益列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "startDate", value = "起始日期", required = true, dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = true, dataType = "String")
    })
    @GetMapping("income/list")
    PageVO<DailyIncome> getIncomeByDate(PageQO pageQO, String startDate, String endDate) {
        return dailyIncomeService.getAllIncomeByDate(pageQO, startDate, endDate);
    }

    @ApiOperation(value = "根据日期获取成本统计数据", notes = "根据日期获取成本统计数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "startDate", value = "起始日期", required = true, dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = true, dataType = "String")
    })
    @GetMapping("cost")
    AnalysisDto getCostAnalysisByDate(PageQO pageQO, String startDate, String endDate) {
        return dailyIncomeService.getAnalysisByDate(startDate, endDate, 1);
    }

    @ApiOperation(value = "根据日期获取收入统计数据", notes = "根据日期获取收入统计数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "startDate", value = "起始日期", required = true, dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = true, dataType = "String")
    })
    @GetMapping("income")
    AnalysisDto getIncomeAnalysisByDate(PageQO pageQO, String startDate, String endDate) {
        return dailyIncomeService.getAnalysisByDate(startDate, endDate, 2);
    }
}
