package com.kgc.house.service;

import com.github.pagehelper.PageInfo;
import com.kgc.house.entity.House;
import com.kgc.house.entity.HouseCondition;

/**
 * @author 王建兵
 * @Classname HouseService
 * @Description TODO
 * @Date 2019/6/24 16:05
 * @Created by Administrator
 */
public interface HouseService {
    //添加出租房
    public  int addHouse(House house);

    //查询用户的出租房
    public PageInfo<House> getUserHouseByPage(Integer page,Integer rows,Integer uid);

    //查询单条
    public House getHouse(String id);

    //修改出租房
    public int updateHouse(House house);

    //删除出租房
    public int delHouse(String id);

    //查询审核的出租房
    public PageInfo<House> getHouseByState(Integer page,Integer row,Integer ispass);

    //审核出租房
    public int passHouse(String id);

    //查询浏览的出租房
    public PageInfo<House> getHouseByBrowser(HouseCondition condition);
}
