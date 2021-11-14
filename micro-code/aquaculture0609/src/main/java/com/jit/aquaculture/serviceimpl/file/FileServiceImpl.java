package com.jit.aquaculture.serviceimpl.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.jit.aquaculture.dto.Input.InputDto;
import com.jit.aquaculture.serviceinterface.file.FileServie;
import com.jit.aquaculture.utils.BaseUtils;
import com.jit.aquaculture.utils.PropertiesReadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipOutputStream;

import static com.jit.aquaculture.utils.FileUtils.zipFile;


/**
 * @author croton
 * @create 2021/4/6 10:26
 */
@Service
@Transactional
public class FileServiceImpl implements FileServie {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 单文件直接下载
     * @param request
     * @param response
     * @param uuid
     * @return
     */
    @Override
    public InputDto<Void> download(HttpServletRequest request, HttpServletResponse response, String uuid) throws Exception {
        InputDto<Void> result = new InputDto<>();

        Map<String, String> hashMap = parseUuid(uuid);
        String fileFullPath = hashMap.get("fileFullPath");
        Path file = Paths.get(fileFullPath);
        String fileName = file.getFileName().toString();
        if (!Files.exists(file)) {
            throw new Exception("file doesn't exit!");
        }
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            InputStream inputStream = Files.newInputStream(file);
            response.setContentLengthLong(Files.size(file));
            String userAgent = request.getHeader("User-Agent").toLowerCase();
            if (userAgent.contains("safari") && !userAgent.contains("chrome")) {
                byte[] bytesName = fileName.getBytes("UTF-8");
                fileName = new String(bytesName, "ISO-8859-1");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            } else {
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20"));
            }
            response.setStatus(HttpStatus.OK.value());
            byte[] bytes = new byte[1024 * 4];
            int readableBytes;
            while ((readableBytes = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, readableBytes);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new Exception("write file failed!");
        }

        result.setStatus(HttpStatus.OK.value());
        result.setMsg("下载成功！");
        result.setData(null);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }

    /**
     * 多文件打包下载
     * @param request
     * @param response
     * @param uuid
     * @return
     * @throws Exception
     */
    @Override
    public InputDto<Void> downloadZip(HttpServletRequest request, HttpServletResponse response, String uuid) throws Exception {
        InputDto<Void> result = new InputDto<>();
        List<String> uuids = BaseUtils.getFileListFromString(uuid);
        List<File> files = new ArrayList<>();
        String groupName = "";
        StringBuilder sb = new StringBuilder();
        //如果是“123_456”格式，
        if ('_' != uuid.charAt(uuid.length()-1)) {
            for (String u : uuids) {
                if (u != null & !"".equals(u)) {
                    Map<String, String> hashMap = parseUuid(u);
                    String fileFullPath = hashMap.get("fileFullPath");
                    sb.append(fileFullPath + ";");
                    files.add(new File(fileFullPath));
                }
            }

            if(groupName == null || "".equals(groupName)){
                groupName = "未知名称";
            }
            downLoadFiles(files, response, request,  "putPictures");
        }

        result.setStatus(HttpStatus.OK.value());
        result.setMsg("下载成功！");
        result.setData(null);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }

    private static HttpServletResponse downLoadFiles(List<File> files, HttpServletResponse response, HttpServletRequest request, String name) throws Exception {

        try {
            // 临时文件夹 最好是放在服务器上，方法最后有删除临时文件的步骤
            String zipFilename = PropertiesReadUtils.PUT_IMG_PATH + name + ".zip";
            File file = new File(zipFilename);
            file.createNewFile();
            if (!file.exists()) {
                file.createNewFile();
            }
            response.reset();
            // response.getWriter()
            // 创建文件输出流
            FileOutputStream fous = new FileOutputStream(file);
            ZipOutputStream zipOut = new ZipOutputStream(fous);
            zipFile(files, zipOut);
            zipOut.close();
            fous.close();
            return downloadZip(file, response, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private static HttpServletResponse downloadZip(File file, HttpServletResponse response, HttpServletRequest request) throws Exception {
        if (file.exists() == false) {
            throw new Exception("待压缩的文件目录：" + file + "不存在.");
        } else {
            try {
                // 以流的形式下载文件。
                InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                // 清空response
                response.reset();

                OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
                String fileName = file.getName();
                String userAgent = request.getHeader("User-Agent").toLowerCase();
                // 如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
                if (userAgent.contains("safari") && !userAgent.contains("chrome")) {
                    byte[] bytesName = fileName.getBytes("UTF-8");
                    fileName = new String(bytesName, "ISO-8859-1");
                    response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                } else {
                    response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20"));
                }

                toClient.write(buffer);
                toClient.flush();
                toClient.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    File f = new File(file.getPath());
                    f.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }



    private HashMap<String, String> parseUuid(String uuid) throws Exception {
        String attributes = redisTemplate.opsForValue().get(uuid) + "";

        //todo 文件属性为空

        if ("".equals(attributes) || "null".equals(attributes)) {
            throw new Exception("文件属性为空");
        }
        HashMap<String, String> hashMap;
        try {
            hashMap = objectMapper.readValue(attributes, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new Exception("解析文件属性时发生错误");
        }
        return hashMap;
    }
}
