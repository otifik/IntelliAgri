package com.jit.aquaculture.serviceimpl.Input;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.aquaculture.domain.Input.input;
import com.jit.aquaculture.dto.Input.InputDto;
import com.jit.aquaculture.dto.Input.InputPageDto;
import com.jit.aquaculture.mapper.Input.InputMapper;
import com.jit.aquaculture.serviceinterface.Input.InputService;
import com.jit.aquaculture.utils.FileUtils;
import com.jit.aquaculture.utils.PropertiesReadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
@Service
public class InputServiceImpl implements InputService {

    @Autowired
    private InputMapper inputMapper;
    @Override
    public InputDto<InputPageDto> selectAll(Integer pageNum, Integer pageSize,String username) {
        InputDto inputDto=new InputDto();
        InputPageDto InputPageDto = new InputPageDto();
        PageHelper.startPage(pageNum, pageSize);
        List<input> inputs= inputMapper.selectAll(username);
        PageInfo<input> pageInfo = new PageInfo<>(inputs);
        InputPageDto.setInputs(inputs);
        InputPageDto.setPageNum(pageNum);
        InputPageDto.setPageSize(pageSize);
        InputPageDto.setPages(pageInfo.getPages());
        InputPageDto.setTotal(Integer.parseInt(String.valueOf(pageInfo.getTotal())));
        inputDto.setData(InputPageDto);
        inputDto.setMsg("查看成功！");
        inputDto.setStatus(HttpStatus.OK.value());
        inputDto.setTimestamp(System.currentTimeMillis());
        return inputDto;
    }

    @Override
    public InputDto<?> select(String username) {
        InputDto InputDto = new InputDto();
        List<input> inputs =  inputMapper.selectAll(username);
        InputDto.setData(inputs);
        InputDto.setMsg("查看成功！");
        InputDto.setStatus(HttpStatus.OK.value());
        InputDto.setTimestamp(System.currentTimeMillis());
        return InputDto;
    }

    @Override
    public InputDto<?> addinput(String type, String name, String manufacture,String remarks, String username, MultipartFile[] pictures) throws IOException {
        System.out.println("pictures:" + pictures);
        InputDto inputDto =new InputDto();
        String picturesPath = null;
        if(pictures != null && pictures.length != 0) {
            StringBuilder sb = new StringBuilder();
            Integer number = 1;
            for (MultipartFile file: pictures) {
                String pictureFileName = file.getOriginalFilename();
                String newPictureFileName = "pictures(" + number + ")" + pictureFileName.substring(pictureFileName.lastIndexOf("."));
                number++;
                String p =  PropertiesReadUtils.PUT_IMG_PATH + name + "-" + username + File.separator + newPictureFileName;
                sb.append(p+";");
                Path picturePathFile = FileUtils.forceCreateFile(p);
                file.transferTo(picturePathFile);
            }
            picturesPath = sb.toString();
        }


        com.jit.aquaculture.domain.Input.input input1 =new input();
        input1.setManufacture(manufacture);
        input1.setName(name);
        input1.setUsername(username);
        input1.setRemarks(remarks);
        input1.setType(type);
        input1.setPictures(picturesPath);
        inputMapper.addinput(input1);
        inputDto.setData(null);
        inputDto.setMsg("添加成功！");
        inputDto.setStatus(HttpStatus.OK.value());
        inputDto.setTimestamp(System.currentTimeMillis());
        return inputDto;
    }

    @Override
    public InputDto<Void> deleteinput(Integer id) {
        InputDto inputDto = new InputDto();
        inputMapper.deleteinput(id);
        inputDto.setData(null);
        inputDto.setMsg("delete success");
        inputDto.setStatus(HttpStatus.OK.value());
        inputDto.setTimestamp(System.currentTimeMillis());
        return inputDto;
    }

    @Override
    public InputDto<?> updateInput(Integer id, String type, String name, String manufacture, String remarks, String username, MultipartFile[] pictures) throws IOException {
        InputDto inputDto =new InputDto();
        input input = inputMapper.selectById(id,username);
        System.out.println(input);
        if (pictures != null) {
            String picturesPath = null;
            if(pictures != null && pictures.length != 0) {
                StringBuilder sb = new StringBuilder();
                Integer number = 1;
                for (MultipartFile file: pictures) {
                    String pictureFileName = file.getOriginalFilename();
                    String newPictureFileName = "pictures(" + number + ")" + pictureFileName.substring(pictureFileName.lastIndexOf("."));
                    number++;
                    String p =  PropertiesReadUtils.PUT_IMG_PATH + name + "-" + username + File.separator + newPictureFileName;
                    sb.append(p+";");
                    Path picturePathFile = FileUtils.forceCreateFile(p);
                    file.transferTo(picturePathFile);
                }
                picturesPath = sb.toString();
            }
            input.setPictures(picturesPath);

        }
        if (manufacture != null) {
            input.setManufacture(manufacture);

        }
        if (name != null) {
            input.setName(name);

        }
        if (username != null) {
            input.setUsername(username);

        }
        if (remarks != null) {
            input.setRemarks(remarks);

        }
        if (type != null) {
            input.setType(type);

        }
        System.out.println(input);
        inputMapper.update(input);
        inputDto.setData(null);
        inputDto.setMsg("update success!");
        inputDto.setStatus(HttpStatus.OK.value());
        inputDto.setTimestamp(System.currentTimeMillis());
        return inputDto;
    }
}
