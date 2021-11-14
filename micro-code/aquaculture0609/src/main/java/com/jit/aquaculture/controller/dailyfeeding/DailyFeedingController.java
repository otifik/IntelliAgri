package com.jit.aquaculture.controller.dailyfeeding;

import com.jit.aquaculture.dto.FeedTemplateDto.FeedTemplateDto;
import com.jit.aquaculture.dto.addfood.AddFoodDto;
import com.jit.aquaculture.serviceinterface.FeedTemplate.FeedTemplateService;
import com.jit.aquaculture.serviceinterface.addfood.AddFoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "dailyfeeding", description = "日hangtouru")
@RestController
@RequestMapping("/dailyfeeding")
public class DailyFeedingController {

    @Autowired
    private FeedTemplateService feedTemplateService;
    @Autowired
    private AddFoodService addFoodService;

    @ApiOperation(value = "根据日期获取所有成本列表（包括固定成本和日常投入）", notes = "根据日期获取所有成本（包括固定成本和日常投入）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "startDate", value = "起始日期", required = true, dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = true, dataType = "String")
    })
    @GetMapping("/queriByName")
    public FeedTemplateDto<?> selectFeedTemplateByName(@RequestParam("name") String name,@RequestParam("username") String  username){
        return feedTemplateService.selectFeedTemplateByName(name,username);
    }

    @GetMapping("/queriAll")
    public AddFoodDto<?> selectAllFeedTemplate(@RequestParam("username") String  username) {
        return addFoodService.selectAllAddFood(username);
    }





    @GetMapping("/queri")
    public AddFoodDto<?> selectFeedTemplate(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,@RequestParam("username") String  username) {
        return addFoodService.selectAddFood(pageNum, pageSize,username);
    }


    @PostMapping("/add")
    public AddFoodDto<?> addFeedTemplate(
            @RequestParam("name") String name,
            @RequestParam("batchNumber") String batchNumber,
            @RequestParam("food") List<String> food,
            @RequestParam("pond") List<String> pond,
            @RequestParam("unit") String unit,
            @RequestParam("amount") String amount ,
            @RequestParam("username") String username,
            @RequestParam("remarks") String remarks,
            @RequestParam("time") List<String> time
    ) {

        System.out.println("add daily");
        return addFoodService.insert(name,batchNumber,pond,food, Double.parseDouble(amount),unit,time,remarks,username);
    }
    @DeleteMapping("/delete/{id}")
    public AddFoodDto<?> deleteinput(@PathVariable("id") Integer id) throws Exception {
        return addFoodService.delete(id);
    }

    @PutMapping("/update/{id}")
    public AddFoodDto<?> updateFeedTemplate(
            @PathVariable("id") Integer id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "batchNumber", required = false) String batchNumber,
            @RequestParam(value = "food", required = false) List<String> food,
            @RequestParam(value = "pond", required = false) List<String> pond,
            @RequestParam(value = "unit", required = false) String unit,
            @RequestParam(value = "amount", required = false) String amount ,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "remarks", required = false) String remarks,
            @RequestParam(value = "time", required = false) List<String> time
    ) {
        return addFoodService.update(id, name,batchNumber,pond,food, Double.parseDouble(amount),unit,time,remarks,username);
    }

}
