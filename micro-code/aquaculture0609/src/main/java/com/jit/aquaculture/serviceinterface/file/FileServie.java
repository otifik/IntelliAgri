package com.jit.aquaculture.serviceinterface.file;



import com.jit.aquaculture.dto.Input.InputDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author croton
 * @create 2021/4/6 10:26
 */
public interface FileServie {

    InputDto<?> download(HttpServletRequest request, HttpServletResponse response, String uuid) throws Exception;
    InputDto<?> downloadZip(HttpServletRequest request, HttpServletResponse response, String uuid) throws Exception;
}
