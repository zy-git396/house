package com.kgc.house.protal.controller;

import com.github.pagehelper.PageInfo;
import com.kgc.house.entity.*;
import com.kgc.house.service.DistrictService;
import com.kgc.house.service.HouseService;
import com.kgc.house.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * @author 王建兵
 * @Classname HouseController
 * @Description TODO
 * @Date 2019/6/24 13:50
 * @Created by Administrator
 */
@Controller
@RequestMapping("/page/")
public class HouseController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private HouseService houseService;


    @RequestMapping("goFaBu")
    public String goFaBu(Model model) throws  Exception{
        //查询所有类型
        List<Type> types=typeService.getAllType();
        //查询所有域名域
        List<District> Districts=districtService.getAllDistrict();

        //使用model将数据传递到页面
        model.addAttribute("types",types);
        model.addAttribute("districts",Districts);
        return "fabu";  //跳转页面
    }


    @RequestMapping("addHouse")
    public String addHouse(House house, @RequestParam(value = "pfile",required = false) CommonsMultipartFile pfile, HttpSession session) throws  Exception{
        //将文件保存在服务器中  D:\\images

        String fname=pfile.getOriginalFilename();
        String expName=fname.substring(fname.lastIndexOf("."));
        String saveName=System.currentTimeMillis()+expName; //保存文件名
        File file=new File("D:\\images\\"+saveName);
        pfile.transferTo(file);  //保存


        //保存数据库的记录  house已经接收部分表单数据
        //设置编号
        house.setId(System.currentTimeMillis()+"");
        //设置用户编号
        Users user=(Users)session.getAttribute("user");
        house.setUserId(user.getId());
        //设置审核状态 0  如果表中有默认值 可不设
        house.setIspass(0);
        //设置是否删除  0
        house.setIsdel(0);
        //设置图片路径
        house.setPath(saveName);

        if(houseService.addHouse(house)>0){ //保存数据
            //调用业务
            //houseService.addHouse(house); //添加信息到数据库
            return "redirect:goFaBu";  //跳转页面
        }
        else{
             //成功上传的图片删除
            file.delete();
            return "redirect:goFaBu";  //跳转页面
        }

    }


    //查询出租房   用户id在session中
    //page 表示页面传的页码
    @RequestMapping("getUserHouse")
    public String getUserHouse(Integer page,HttpSession session,Model model) throws  Exception{
       //调用业务
        Users user=(Users)session.getAttribute("user");
        PageInfo<House> pageInfo=houseService.getUserHouseByPage(page==null?1:page,10,user.getId());
        //将分页信息填充到作用域
        model.addAttribute("pageInfo",pageInfo);
        return "guanli";
    }


    //修改显示某个出租房信息
    @RequestMapping("getouse")
    public String getHouse(String id,Model model) throws  Exception{
         //查询所有类型
         List<Type> types=typeService.getAllType();
         //查询所有区域
         List<District> districts=districtService.getAllDistrict();
         //查询出租房信息  调用查询单个出租房的业务（dao）
         House house=houseService.getHouse(id);
         //将数据设置到域
        model.addAttribute("types",types);
        model.addAttribute("districts",districts);
        model.addAttribute("house",house);
        return "upfabu";
    }


    //修改出租房
    @RequestMapping("upHouse")
    public String upHouse(String oldPic,House house, @RequestParam(value = "pfile",required = false) CommonsMultipartFile pfile, HttpSession session) throws  Exception{
       //1.修改时判断用户有没有修改图片
        File file=null;
        if(pfile.getOriginalFilename().equals("")){
             //System.out.println("不修改图片");
             //不需要实现文件上传，同时house实体的path属性无需设置属性
        }else {
            //System.out.println("修改图片");
            //上传新的图片，删除旧的图片，设置path为上传新的图片名称
            file=new File("D:\\images\\"+oldPic);
            pfile.transferTo(file);  //保存
            //设置图片名称
            house.setPath(oldPic);
        }

        //保存数据库的记录  house已经接收部分表单数据
        //设置编号  从表单获取
        //设置审核状态 0  如果表中有默认值 可不设
        //house.setIspass(0);
        //设置是否删除  0
        //house.setIsdel(0);
        //设置图片路径
        // house.setPath(saveName);
        if(houseService.updateHouse(house)<=0){
            //成功上传的图片删除
            if(file!=null) file.delete();
        }

        return "redirect:getUserHouse";  //跳转到查询用户出租房
    }

    @RequestMapping("delHouse")
    @ResponseBody
    public String delHouse(String id) throws  Exception{
       //调用业务
        int temp=houseService.delHouse(id);
        return "{\"result\":"+temp+"}";
        //return "redirect:getUserHouse";  //跳转到查询用户出租房
    }


    //查询所有浏览出租房信息
    @RequestMapping("goList")
    public String goList(HouseCondition condition, Model model){  //传页码
        //调用业务获取出租房
        PageInfo<House> pageInfo=houseService.getHouseByBrowser(condition);
        //将分页信息设置到作用域中
        model.addAttribute("pageInfo",pageInfo);

        if(condition.getTitle()!=null)
        condition.setTitle(condition.getTitle().replaceAll("%",""));
        model.addAttribute("condition",condition);  //回显查询条
        return "list";
    }

}
