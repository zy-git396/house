package com.kgc.house.mapper;

import com.kgc.house.entity.House;
import com.kgc.house.entity.HouseCondition;
import com.kgc.house.entity.HouseExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseMapper {
    int deleteByPrimaryKey(String id);

    int insert(House record);

    int insertSelective(House record);

    List<House> selectByExample(HouseExample example);

    House selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(House record);

    int updateByPrimaryKey(House record);


    //查询用户发布的出租房   底层是连接查询,修改实体类
    List<House> selectHouseByUserId(Integer uid);

    //查询出租房信息（带区域id）
    House getHouseAndDid(String id);



    /**
     * 查询审核出租房信息
     * @param state state=0 表示查询所有未审核  1 表示查询所有已审核
     * @return
     */
    List<House> getHouseByState(Integer state);


    //查询浏览的出租房信息
    List<House> getHouseByBrowser(HouseCondition condition);
}