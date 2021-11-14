package com.jit.aquaculture.serviceinterface.Input;

import com.jit.aquaculture.domain.Input.input;
import com.jit.aquaculture.dto.Input.InputDto;
import com.jit.aquaculture.dto.pond.PondDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface InputService {

    InputDto<?> selectAll(Integer pageNum, Integer pageSize,String username);
    InputDto<?> select(String username);
    InputDto<?> addinput (String type, String name, String manufacture,  String remarks, String username, MultipartFile[] pictures) throws IOException;
    InputDto<?> deleteinput (Integer id);
    InputDto<?> updateInput(Integer id, String type, String name, String manufacture,  String remarks, String username, MultipartFile[] pictures) throws IOException;


}
