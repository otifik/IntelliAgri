package com.jit.aquaculture.controller.Input;

import com.jit.aquaculture.dto.Input.InputDto;
import com.jit.aquaculture.dto.pond.PondDto;
import com.jit.aquaculture.serviceinterface.Input.InputService;
import com.jit.aquaculture.serviceinterface.pond.pondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/inputs")
public class InputController {
    @Autowired
    private  com.jit.aquaculture.serviceinterface.Input.InputService inputService;
    @GetMapping("/queri")
    public InputDto<?> selectInput(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,@RequestParam("username") String  username) throws Exception {
        return inputService.selectAll(pageNum,pageSize,username);
    }
    @GetMapping("/queriAll")
    public InputDto<?> selectAll(@RequestParam("username") String  username) {
        return inputService.select(username);
    }
    @PostMapping("/add")
    public InputDto<?> addinput(@RequestParam("type") String type,
                              @RequestParam("name") String name,
                              @RequestParam("manufacture") String manufacture,
                                @RequestParam(value = "pictures", required = false) MultipartFile[] pictures,
                              @RequestParam("remarks") String remarks,
                              @RequestParam("username") String username) throws IOException {


        return inputService.addinput(type,name,manufacture,remarks,username, pictures);
    }
    @DeleteMapping("/delete/{id}")
    public InputDto<?>  deleteinput(Integer id){
        return inputService.deleteinput(id);
    }

    @PutMapping("/update/{id}")
    public InputDto<?> updateinput(@PathVariable("id") Integer id,
                                @RequestParam(value = "type", required = false) String type,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "manufacture", required = false) String manufacture,
                                @RequestParam(value = "pictures", required = false) MultipartFile[] pictures,
                                @RequestParam(value = "remarks", required = false) String remarks,
                                @RequestParam(value = "username", required = false) String username) throws IOException {


        return inputService.updateInput(id, type,name,manufacture,remarks,username, pictures);
    }



}
