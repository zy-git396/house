package com.kgc.house.protal.controller;

import com.kgc.house.entity.District;
import com.kgc.house.entity.Type;
import com.kgc.house.service.DistrictService;
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
@Controller(value = "districtController2")
@RequestMapping("/page/")
public class DistrictController {

    //调用业务
    @Autowired
    private DistrictService districtService;

    //发请求获取类型的异步数据
    @RequestMapping("getDistrict")
    @ResponseBody
    public List<District> getDistrict(){
      return  districtService.getAllDistrict();
    }
}
