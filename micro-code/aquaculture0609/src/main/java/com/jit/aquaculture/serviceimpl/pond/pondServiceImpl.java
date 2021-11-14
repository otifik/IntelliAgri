
package com.jit.aquaculture.serviceimpl.pond;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.aquaculture.domain.pond.Pond;
import com.jit.aquaculture.dto.pond.PondDto;
import com.jit.aquaculture.dto.pond.PondPageDto;
import com.jit.aquaculture.mapper.pond.PondMapper;
import com.jit.aquaculture.serviceinterface.pond.pondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class pondServiceImpl implements pondService {
    @Autowired
    private PondMapper pondMapper;
    @Override
    public PondDto<Void> addpond(String name, Double length, Double width, Double depth, String orientation, Double longitude, Double latitude, String address, String product, String username) {
        PondDto pondDto = new PondDto();
        //....addpond逻辑
        Pond pond = new Pond();
        pond.setName(name);
        pond.setLength(length);
        pond.setWidth(width);
        pond.setDepth(depth);
        pond.setOrientation(orientation);
        pond.setLongitude(longitude);
        pond.setLatitude(latitude);
        pond.setAddress(address);
        pond.setProduct(product);
        pond.setUsername(username);
        pondMapper.addpond(pond);
        pondDto.setData(null);
        pondDto.setMsg("添加成功！");
        pondDto.setStatus(HttpStatus.OK.value());
        pondDto.setTimestamp(System.currentTimeMillis());
        return pondDto;
    }

    @Override
    public PondDto<PondPageDto> selectAll(Integer pageNum, Integer pageSize,String username) {
        PondDto pondDto = new PondDto();
        PondPageDto pondPageDto = new PondPageDto();
        PageHelper.startPage(pageNum, pageSize);
        List<Pond> ponds =  pondMapper.selectAll(username);
        PageInfo<Pond> pageInfo = new PageInfo<>(ponds);
        pondPageDto.setPonds(ponds);
        pondPageDto.setPageNum(pageNum);
        pondPageDto.setPageSize(pageSize);
        pondPageDto.setPages(pageInfo.getPages());
        pondPageDto.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        pondDto.setData(pondPageDto);
        pondDto.setMsg("查看成功！");
        pondDto.setStatus(HttpStatus.OK.value());
        pondDto.setTimestamp(System.currentTimeMillis());
        return pondDto;
    }

    @Override
    public PondDto<?> select(String username) {
        PondDto pondDto = new PondDto();
        List<Pond> ponds =  pondMapper.selectAll(username);
        pondDto.setData(ponds);
        pondDto.setMsg("查看成功！");
        pondDto.setStatus(HttpStatus.OK.value());
        pondDto.setTimestamp(System.currentTimeMillis());
        return pondDto;
    }

    @Override
    public PondDto<Void> deletepond(Integer id) {
        PondDto pondDto = new PondDto();
        pondMapper.deletepond(id);
        pondDto.setData(null);
        pondDto.setMsg("delete sucess");
        pondDto.setStatus(HttpStatus.OK.value());
        pondDto.setTimestamp(System.currentTimeMillis());
        return pondDto;
    }
    @Override
    public PondDto<Void> updatepond(String name, Double length, Double width, Double depth, String orientation, Double longitude, Double latitude, String address, String product, String username,Integer id) {
        PondDto pondDto = new PondDto();
        Pond pond = pondMapper.selectById(id,username);
        pond.setName(name);
        pond.setLength(length);
        pond.setWidth(width);
        pond.setDepth(depth);
        pond.setOrientation(orientation);
        pond.setLongitude(longitude);
        pond.setLatitude(latitude);
        pond.setAddress(address);
        pond.setProduct(product);
        pond.setUsername(username);
        pondMapper.updateappond(pond);
        pondDto.setData(null);
        pondDto.setStatus(HttpStatus.OK.value());
        pondDto.setTimestamp(System.currentTimeMillis());
        pondDto.setMsg("update success!");
        return pondDto;
    }



}


