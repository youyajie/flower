package com.you.Controller;

import com.you.entity.FileUploadResponse;
import com.you.utils.DateUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyj on 2018/4/26.
 */
@Controller
public class FileUploadUtil {
    private static final String BASE_FILE = "/data/img/";

    @RequestMapping("/upload")
    @ResponseBody
    public List<FileUploadResponse> upload(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
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
                Files.copy(file.getInputStream(), Paths.get(BASE_FILE + location, fileName),
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
        return list;
    }

}