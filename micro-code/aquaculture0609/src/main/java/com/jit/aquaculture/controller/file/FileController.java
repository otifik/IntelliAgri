package com.jit.aquaculture.controller.file;

import com.jit.aquaculture.dto.Input.InputDto;
import com.jit.aquaculture.serviceinterface.file.FileServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author croton
 * @create 2021/4/6 10:22
 */
@RestController
public class FileController {

    @Autowired
    private FileServie fileServie;

    /**
     * 单文件下载
     * @param request
     * @param response
     * @param uuid
     * @return
     */
    @GetMapping("/file/download/{uuid}")
    public InputDto<?> download(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable("uuid") String uuid) throws Exception {


        if (uuid.contains("_")) {
            return fileServie.downloadZip(request, response, uuid);
        }
        return fileServie.download(request, response, uuid);

    }

    /**
     * 多文件打包下载
     * @param request
     * @param response
     * @param uuid
     * @return
     */
    @GetMapping("/file/download/zip/{uuid}")
    public InputDto<?> downloadZip(HttpServletRequest request, HttpServletResponse response,
                                  @PathVariable("uuid") String uuid) throws Exception {
        System.out.println("download");
        return fileServie.downloadZip(request, response, uuid);
    }


}
