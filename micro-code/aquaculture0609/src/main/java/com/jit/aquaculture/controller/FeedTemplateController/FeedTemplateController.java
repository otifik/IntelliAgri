package com.jit.aquaculture.controller.FeedTemplateController;

import com.jit.aquaculture.domain.FeedTemplate.FeedTemplate;
import com.jit.aquaculture.dto.FeedTemplateDto.FeedTemplateDto;
import com.jit.aquaculture.dto.FishInputDto.FishInputDto;
import com.jit.aquaculture.dto.Input.InputDto;
import com.jit.aquaculture.serviceinterface.FeedTemplate.FeedTemplateService;
import com.jit.aquaculture.serviceinterface.FishInput.FishInputService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/feedtemplate")
public class FeedTemplateController {
    @Autowired
    private FeedTemplateService feedTemplateService;


    @GetMapping("/queriAll")
    public FeedTemplateDto<?> selectAllFeedTemplate(@RequestParam("username") String  username) {
        return feedTemplateService.selectAllFeedTemplate(username);
    }





    @GetMapping("/queri")
    public FeedTemplateDto<?> selectFeedTemplate(@RequestParam("pageNum") Integer pageNum,
                                                 @RequestParam("pageSize") Integer pageSize,
                                                 @RequestParam("username") String  username) {
        System.out.println("pages feedtemplate");
        return feedTemplateService.selectFeedTemplate(pageNum, pageSize,username);
    }


    @PostMapping("/add")
    public FeedTemplateDto<?> addFeedTemplate(
            @RequestParam("name") String name,
            @RequestParam("batchNumber") String batchNumber,
            @RequestParam("food") List<String> food,
            @RequestParam("pond") List<String> pond,
            @RequestParam("unit") String unit,
            @RequestParam("amount") Double amount ,
            @RequestParam("username") String username,
            @RequestParam("remarks") String remarks,
            @RequestParam("time") List<String> time
    ) {
        System.out.println("add controller");
        return feedTemplateService.insert(name,batchNumber,pond,food, amount,unit,time,remarks,username);
    }
    @DeleteMapping("/delete/{id}")
    public FeedTemplateDto<?> deleteinput(@PathVariable("id") Integer id) throws Exception {
        System.out.println("delete");
        return feedTemplateService.delete(id);
    }

    @PutMapping("/update/{id}")
    public FeedTemplateDto<?> updateFeedTemplate(
            @PathVariable("id") Integer id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "batchNumber", required = false) String batchNumber,
            @RequestParam(value = "food", required = false) List<String> food,
            @RequestParam(value = "pond", required = false) List<String> pond,
            @RequestParam(value = "unit", required = false) String unit,
            @RequestParam(value = "amount", required = false) Double amount ,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "remarks", required = false) String remarks,
            @RequestParam(value = "time", required = false) List<String> time
    ) {

        return feedTemplateService.update(id, name,batchNumber,pond,food, amount,unit,time,remarks,username);
    }

}
