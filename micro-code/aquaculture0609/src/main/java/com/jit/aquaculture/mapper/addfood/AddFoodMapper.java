package com.jit.aquaculture.mapper.addfood;

import com.jit.aquaculture.domain.FeedTemplate.FeedTemplate;
import com.jit.aquaculture.domain.addfood.AddFood;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AddFoodMapper {
    @Select("select * from add_food where username=#{username}")
    List<AddFood> selectAddFood(String username);

    @Select("select * from add_food where id = #{id} and username=#{username}")
    AddFood selectAddFoodById(Integer id,String username);

    @Select("select * from add_food where name = #{name} and username=#{username}")
    AddFood selectAddFoodByName(String name,String username);

    @Insert("insert into add_food (name,batch_number,pond,food, amount,unit,time,remarks,username) values (#{name}, #{batchNumber}, #{pond}, #{food},#{amount},#{unit},#{time},#{remarks},#{username})")
    int insert(AddFood addFood);
    @Delete("delete from add_food where id = #{id}")
    int deleteById(Integer id);

    @Update("update add_food set name = #{name}, batch_number = #{batchNumber}, pond = #{pond}, food = #{food}, amount = #{amount}, unit = #{unit}, time = #{time}, remarks = #{remarks}, username = #{username} where id = #{id}")
    int update(AddFood addFood);
}
