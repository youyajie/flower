package com.you.Controller;

import com.you.entity.FlowerCareEntity;
import com.you.service.IFlowerCareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
