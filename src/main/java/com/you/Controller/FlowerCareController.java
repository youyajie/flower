package com.you.Controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.you.entity.FlowerCareEntity;
import com.you.service.IFlowerCareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yyj on 2018/4/26.
 */
@Controller
@RequestMapping("/care")
public class FlowerCareController {

    @Autowired
    private IFlowerCareService flowerCare;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addCare(FlowerCareEntity entity) {
        if(entity != null) {
            if(!CollectionUtils.isEmpty(entity.getImgList())) {
                entity.setImg(JSON.toJSONString(entity.getImgList()));
            }
            flowerCare.insertCare(entity);
        }

        return "redirect:/care/list";
    }

    @RequestMapping(value = "/wx/list/")
    public String getWXCare() {
        String care = "";

        try {
            FlowerCareEntity entity = flowerCare.getCare();
            ObjectMapper mapper = new ObjectMapper();
            care = mapper.writeValueAsString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return care;
    }

    @RequestMapping(value = "/list")
    public String getCare(Map<String, Object> model) {

        List<FlowerCareEntity> entityList = flowerCare.getCares();

        if(!CollectionUtils.isEmpty(entityList)) {
            for (FlowerCareEntity care : entityList) {
                if(care == null || StringUtils.isEmpty(care.getImg())) {
                    continue;
                }

                List<String> imgList = JSON.parseArray(care.getImg(), String.class);
                care.setImgList(imgList);
            }
        }

        model.put("cares", entityList);

        return "index";
    }

}
