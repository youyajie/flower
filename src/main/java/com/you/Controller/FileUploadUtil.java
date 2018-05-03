package com.you.Controller;

import com.alibaba.fastjson.JSON;
import com.you.entity.FileUploadResponse;
import com.you.utils.DateUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyj on 2018/4/26.
 */
@Controller
public class FileUploadUtil {
    public static final String BASE_FILE = "/Users/yyj/data/img/";

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
        List<FileUploadResponse> list = new ArrayList<>();

        String location = DateUtil.format(LocalDate.now(), "yyyy-MM-dd") + "/";
        File targetFile = new File(BASE_FILE + location);
        if(!targetFile.exists()) {
            targetFile.mkdirs();
        }

        for (MultipartFile file : files) {
            FileUploadResponse rs = new FileUploadResponse();
            String contentType = file.getContentType();
            String fileName = file.getOriginalFilename();
            try {
                Path path = Paths.get(BASE_FILE + location, fileName);
                Files.copy(file.getInputStream(), path,
                        StandardCopyOption.REPLACE_EXISTING);
                rs.setContentType(contentType);
                rs.setFileName(fileName);
                rs.setUrl(location + fileName);
                rs.setType("success");
            } catch (Exception e) {
                rs.setType("fail");
                rs.setMsg("文件上传失败!");
                e.printStackTrace();
            }
            list.add(rs);
        }
        return JSON.toJSONString(list);
    }

    @RequestMapping(value = "/img", method = RequestMethod.GET,
            produces = MediaType.IMAGE_PNG_VALUE)
    public void getImage(HttpServletResponse response, @RequestParam("name") String name)
            throws IOException {
        if(StringUtils.isEmpty(name)) {
            return;
        }

        InputStream imgStream = new FileInputStream(BASE_FILE + name);

        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(imgStream, response.getOutputStream());
    }

}
