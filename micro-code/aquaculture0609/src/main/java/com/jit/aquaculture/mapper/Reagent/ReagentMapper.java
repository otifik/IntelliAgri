package com.jit.aquaculture.mapper.Reagent;

import com.jit.aquaculture.domain.Reagent.Reagent;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface ReagentMapper {
    @Select("select * from reagent where username=#{username}")
    List<Reagent> selectreagent(String username);

    @Select("select * from reagent where id=#{id} and username=#{username}")
    Reagent selectById(Integer id,String username);
//Integer id, String batchNumber, String pondNumber, String inputs, Double amount, String date, String remarks
    @Update("update reagent set unit = #{unit}, batch_number = #{batchNumber}, pond = #{pond}, reagent=#{reagent}, amount=#{amount}, time=#{time}, remarks=#{remarks}, username=#{username} where id=#{id}")
    int update(Reagent reagent);

    @Delete("delete from reagent where id = #{id}")
    int deleteById(Integer id);

    @Insert("insert into reagent (unit, batch_number,pond,reagent,amount,time,remarks,username) values (#{unit},#{batchNumber},#{pond},#{reagent},#{amount},#{time},#{remarks}, #{username})")
    int insert(Reagent reagent);







}
