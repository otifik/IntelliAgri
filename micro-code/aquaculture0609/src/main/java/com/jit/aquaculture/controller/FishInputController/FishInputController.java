package com.jit.aquaculture.controller.FishInputController;
import com.jit.aquaculture.dto.FishInputDto.FishInputDto;
import com.jit.aquaculture.dto.pond.PondDto;
import com.jit.aquaculture.serviceinterface.FishInput.FishInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/fishinput")
public class FishInputController {
    @Autowired
    private FishInputService fishInputService;
    @GetMapping("/queri")
    public FishInputDto<?> selectfishinputs(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,@RequestParam("username") String  username) {
        System.out.println("fishinput查看");
        return fishInputService.selectfishinputs(pageNum, pageSize,username);
    }
    @GetMapping("/queriAll")
    public FishInputDto<?> selectfishinputs(@RequestParam("username") String  username) {
        System.out.println("fishinput查看");
        return fishInputService.selectfishinputsall(username);
    }
    @PostMapping("/add")
    public FishInputDto<?> addFishInput(
                                        @RequestParam("type") String type,
                                        @RequestParam("amount") Double amount,
                                        @RequestParam("date") String date,
                                        @RequestParam("pond") List<String> pond,
                                        @RequestParam("unit") String unit,
                                        @RequestParam("batchNumber") String batchNumber,
                                        @RequestParam("username") String username) {

        return fishInputService.insert(type, amount, date, pond, unit, batchNumber, username);
    }

    @PutMapping("/update/{id}")
    public FishInputDto<?> updateFishInput(@PathVariable("id") Integer id,
                                           @RequestParam(value = "type", required = false) String type,
                                        @RequestParam(value = "amount", required = false) Double amount,
                                        @RequestParam(value = "date", required = false) String date,
                                        @RequestParam(value = "pond", required = false) List<String> pond,
                                           @RequestParam(value = "unit", required = false) String unit,
                                        @RequestParam(value = "batchNumber", required = false) String batchNumber,
                                           @RequestParam(value = "username", required = false) String username) throws Exception {
        System.out.println("pond:" + pond);
        return fishInputService.update(id, type, amount, date, pond, unit, batchNumber, username);
    }
    @DeleteMapping("/delete/{id}")
    public FishInputDto<?> deletefishinput(Integer id,String username) throws Exception {
        System.out.println("delete");
        return fishInputService.delete(id);
    }





}
