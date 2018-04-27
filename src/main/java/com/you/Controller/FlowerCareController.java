package com.you.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.you.entity.FlowerCareEntity;
import com.you.service.IFlowerCareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by yyj on 2018/4/26.
 */
@RestController
@RequestMapping("/care")
public class FlowerCareController {

    @Autowired
    private IFlowerCareService flowerCare;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addCare(@RequestBody FlowerCareEntity entity) {
        flowerCare.insertCare(entity);
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
        String cares = "";

        try {
            List<FlowerCareEntity> entityList = flowerCare.getCares();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, entityList);
            byte[] data = out.toByteArray();
            cares = new String(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.put("cares", cares);

        return "index";
    }

}
