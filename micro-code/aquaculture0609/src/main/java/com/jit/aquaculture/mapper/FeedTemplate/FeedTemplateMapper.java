package com.jit.aquaculture.mapper.FeedTemplate;

import com.jit.aquaculture.domain.FeedTemplate.FeedTemplate;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface FeedTemplateMapper {
        @Select("select * from feedtemplate where username=#{username}")
        List<FeedTemplate> selectFeedTemplate(String username);

        @Select("select * from feedtemplate where id = #{id} and username=#{username}")
        FeedTemplate selectFeedTemplateById(Integer id ,String username);

        @Select("select * from feedtemplate where name = #{name} ")
        FeedTemplate selectFeedTemplateByName(String name);

        @Insert("insert into feedtemplate (name,batch_number,pond,food, amount,unit,time,remarks,username) values (#{name}, #{batchNumber}, #{pond}, #{food},#{amount},#{unit},#{time},#{remarks},#{username})")
        int insert(FeedTemplate feedTemplate);
    @Delete("delete from feedtemplate where id = #{id}")
    int deleteById(Integer id);

    @Update("update feedtemplate set name = #{name}, batch_number = #{batchNumber}, pond = #{pond}, food = #{food}, amount = #{amount}, unit = #{unit}, time = #{time}, remarks = #{remarks}, username = #{username} where id = #{id}")
    int update(FeedTemplate feedTemplate);



    }
