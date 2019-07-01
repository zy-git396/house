package com.kgc.house.protal.controller;

import com.kgc.house.entity.Type;
import com.kgc.house.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 王建兵
 * @Classname TypeController
 * @Description TODO
 * @Date 2019/6/29 13:59
 * @Created by Administrator
 */
@Controller(value = "typeController2")
@RequestMapping("/page/")
public class TypeController {

    //调用业务
    @Autowired
    private TypeService typeService;

    //发请求获取类型的异步数据
    @RequestMapping("getType")
    @ResponseBody
    public List<Type> getType(){
      return  typeService.getAllType();
    }
}
