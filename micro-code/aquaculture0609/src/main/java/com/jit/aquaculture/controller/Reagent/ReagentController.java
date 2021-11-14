package com.jit.aquaculture.controller.Reagent;

import com.jit.aquaculture.dto.FishInputDto.FishInputDto;
import com.jit.aquaculture.dto.Reagent.ReagentDto;
import com.jit.aquaculture.dto.addfood.AddFoodDto;
import com.jit.aquaculture.serviceinterface.FishInput.FishInputService;
import com.jit.aquaculture.serviceinterface.Reagent.ReagentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/reagent")
public class ReagentController {
    @Autowired
    private ReagentService reagentService;
    @GetMapping("/queri")
    public ReagentDto<?> selectReagent(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,@RequestParam("username") String username) {

        System.out.println("reagent查看age");
        System.out.println(pageNum);
        System.out.println(pageSize);
        return reagentService.selectreagent(pageNum, pageSize,username);
    }
    @GetMapping("/queriAll")
    public ReagentDto<?> selectAllReagent(@RequestParam("username") String username) {
        System.out.println("reagents查看");
        return reagentService.selectreagentall(username);
    }
    @PostMapping("/add")
    public ReagentDto<?> addreagent(
            @RequestParam("unit") String unit,
            @RequestParam("batchNumber") List<String> batchNumber,
            @RequestParam("pond") List<String> pond,
            @RequestParam("reagent") String reagent,
            @RequestParam("amount") String amount,
            @RequestParam("time") List<String> time,
            @RequestParam("remarks") String remarks,
            @RequestParam("username") String username
            ){
        System.out.println("reagent add");
        return reagentService.insert(unit, batchNumber,pond,reagent,Double.parseDouble(amount),time,remarks,username);
    }

    @PutMapping("/update/{id}")
    public ReagentDto<?> updatereagent(
            @PathVariable("id") Integer id,
            @RequestParam("unit") String unit,
            @RequestParam("batchNumber") List<String> batchNumber,
            @RequestParam("pond") List<String> pond,
            @RequestParam("reagent") String reagent,
            @RequestParam("amount") String amount,
            @RequestParam("time") List<String> time,
            @RequestParam("remarks") String remarks,
            @RequestParam("username") String username
    ) throws Exception {

        System.out.println("amount: " + amount);
        System.out.println("reagent update");
        return reagentService.update(id, unit, batchNumber,pond,reagent,Double.parseDouble(amount),time,remarks,username);
    }

    @DeleteMapping("/delete/{id}")
    public ReagentDto<?> deletereagent(@PathVariable("id") Integer id) throws Exception {
        return reagentService.delete(id);
    }










}
