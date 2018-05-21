package com.you.Controller;

import com.alibaba.fastjson.JSON;
import com.you.entity.FlowerCareEntity;
import com.you.service.IFlowerCareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/wx/detail", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public FlowerCareEntity getWXCare(@RequestParam("id") Integer id) {
        return flowerCare.getCare(id);
    }

    @RequestMapping(value = "/wx/list", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<FlowerCareEntity> getWXCares() {

        return flowerCare.getCares();
    }

    @RequestMapping(value = "/list")
    public String getCare(Map<String, Object> model) {

        List<FlowerCareEntity> entityList = flowerCare.getCares();
        model.put("cares", entityList);

        return "index";
    }

}
