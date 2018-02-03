package com.adatafun.base.data.center.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面相关
 *
 * @date: 2018/1/9 上午9:28
 * @author: ironc
 * @version: 1.0
 */
@Controller
public class ViewController {

    /**
     * 跳转首页
     *
     * @return
     */
    @GetMapping(value = "/index")
    public String hello(Model model) {
        JSONObject object = new JSONObject();
        object.put("name", "数据中心");
        object.put("title", "数据中心首页");
        model.addAttribute("app", object);
        return "index";
    }

}
