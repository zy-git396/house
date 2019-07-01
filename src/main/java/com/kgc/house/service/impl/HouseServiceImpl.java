package com.kgc.house.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kgc.house.entity.House;
import com.kgc.house.entity.HouseCondition;
import com.kgc.house.entity.HouseExample;
import com.kgc.house.mapper.HouseMapper;
import com.kgc.house.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王建兵
 * @Classname HouseServiceImpl
 * @Description TODO
 * @Date 2019/6/24 16:05
 * @Created by Administrator
 */
@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseMapper houseMapper;

    @Override
    public int addHouse(House house) {
        return houseMapper.insertSelective(house);
    }

    @Override
    public PageInfo<House> getUserHouseByPage(Integer page, Integer rows, Integer uid) {
        PageHelper.startPage(page,rows);
        //调用dao层查询出租房
        List<House> list=houseMapper.selectHouseByUserId(uid);
        //创建pageInfo
        return new PageInfo<House>(list);
    }

    @Override
    public House getHouse(String id) {
        return houseMapper.getHouseAndDid(id);
    }

    @Override
    public int updateHouse(House house) {
        return houseMapper.updateByPrimaryKeySelective(house);
    }

    @Override
    public int delHouse(String id) {
        House house=new House();
        //设置主键值
        house.setId(id);
        //设置出租房的状态
        house.setIsdel(new Integer(1));
        return houseMapper.updateByPrimaryKeySelective(house);
    }


    @Override
    public PageInfo<House> getHouseByState(Integer page,Integer row,Integer ispass) {
        PageHelper.startPage(page,row);
        //调用dao层查询出租房
        List<House> list=houseMapper.getHouseByState(ispass);
        //创建pageInfo
        return new PageInfo<House>(list);
    }

    @Override
    public int passHouse(String id) {
        House house=new House();
        house.setId(id);
        house.setIspass(1);  //通过审核
        return houseMapper.updateByPrimaryKeySelective(house);
    }

    @Override
    public PageInfo<House> getHouseByBrowser(HouseCondition condition) {
        PageHelper.startPage(condition.getPage(),condition.getPageSize());
        //调用业务  Example只能实现单表条件搜索查询
        if(condition.getTitle()!=null) {
            condition.setTitle("%" + condition.getTitle() + "%");
        }

        List<House> list=houseMapper.getHouseByBrowser(condition);
        PageInfo<House> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
}
