package com.jit.aquaculture.controller.pond;
import com.jit.aquaculture.domain.pond.Pond;
import com.jit.aquaculture.dto.pond.PondDto;
import com.jit.aquaculture.mapper.pond.PondMapper;
import com.jit.aquaculture.serviceinterface.pond.pondService;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pond")
public class pondController {

    @Autowired
    private pondService pondService;


    @PostMapping("/add")
    public PondDto<?> addpond(@RequestParam("name") String name,
                              @RequestParam("length") Double length,
                              @RequestParam("width") Double width,
                              @RequestParam("depth") Double depth,
                              @RequestParam("orientation") String orientation,
                              @RequestParam(value = "longitude", required = false) Double longitude,
                              @RequestParam(value = "latitude", required = false) Double latitude,
                              @RequestParam("address") String address,
                              @RequestParam("product") String product,
                              @RequestParam("username") String username) {

        return pondService.addpond(name, length, width, depth, orientation, longitude, latitude, address, product, username);
    }
    @GetMapping("/queri")
    public PondDto<?> selectpond(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,@RequestParam("username") String  username) {
        return pondService.selectAll(pageNum, pageSize,username);
    }
    @GetMapping("/queriAll")
    public PondDto<?> selectponds(@RequestParam("username") String  username) {
        return pondService.select(username);
    }
    @DeleteMapping("/delete/{id}")
    public PondDto<?>  deletepond(Integer id){
            return pondService.deletepond(id);
    }
    @PutMapping ("/update/{id}")
    public PondDto<?>  updatepond(@PathVariable("id") Integer id,
                                  @RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "length", required = false) Double length,
                                  @RequestParam(value = "width", required = false) Double width,
                                  @RequestParam(value = "depth", required = false) Double depth,
                                  @RequestParam(value = "orientation", required = false) String orientation,
                                  @RequestParam(value = "longitude", required = false) Double longitude,
                                  @RequestParam(value = "latitude", required = false) Double latitude,
                                  @RequestParam(value = "address", required = false) String address,
                                  @RequestParam(value = "product", required = false) String product,
                                  @RequestParam(value = "username", required = false) String username){

        return pondService.updatepond(name,length,width, depth,orientation,longitude,latitude,address,product,username,id);
    }
}




































































