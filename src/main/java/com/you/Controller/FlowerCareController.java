package com.you.Controller;

import com.alibaba.fastjson.JSON;
import com.you.entity.FlowerCareEntity;
import com.you.entity.UserEntity;
import com.you.service.IFlowerCareService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by yyj on 2018/4/26.
 */
@Controller
@RequestMapping("/care")
public class FlowerCareController {

    private final String username = "youyajie";
    private final String password = "6yhn7UJM";

    @Autowired
    private IFlowerCareService flowerCare;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginSubmit(UserEntity user, HttpServletRequest request) {
        if(user == null) {
            return "login";
        }

        if(!username.equals(user.getUsername()) || !password.equals(user.getPassword())) {
            return "login";
        }

        HttpSession session = request.getSession();
        session.setAttribute("username", user.getUsername());
        session.setAttribute("password", user.getPassword());
        return "redirect:/care/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addCare(FlowerCareEntity entity, HttpSession session) {
        if(checkSession(session)) {
            return "redirect:/care/login";
        }

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
    public String getCare(Map<String, Object> model, HttpSession session) {
        if(checkSession(session)) {
            return "redirect:/care/login";
        }

        List<FlowerCareEntity> entityList = flowerCare.getCares();
        model.put("cares", entityList);

        return "index";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=UTF-8")
    public String deleteCare(@PathVariable Integer id, HttpSession session) {
        if(checkSession(session)) {
            return "false";
        }

        flowerCare.deleteCare(id);
        return "true";
    }

    private Boolean checkSession(HttpSession session) {
        String username = session.getAttribute("username") != null ?
                session.getAttribute("username").toString() : "";
        String password = session.getAttribute("password") != null ?
                session.getAttribute("password").toString() : "";
        if(!username.equals(this.username) || !password.equals(this.password)) {
            return false;
        }

        return true;
    }
}
