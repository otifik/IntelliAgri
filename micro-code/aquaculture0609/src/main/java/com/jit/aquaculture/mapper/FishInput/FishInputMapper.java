package com.jit.aquaculture.mapper.FishInput;

import com.jit.aquaculture.domain.FishInput.FishInput;
import com.jit.aquaculture.domain.Input.input;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
@Mapper
public interface FishInputMapper {
    @Select("select * from fishinput where username=#{username}")
    List<FishInput> selectFishInputs(String username);

    @Select("select * from fishinput where id=#{id} and  username=#{username}")
    FishInput selectById(Integer id,String username);

    @Update("update fishinput set type=#{type}, amount=#{amount}, date=#{date}, pond=#{pond}, batch_number = #{batchNumber} where id=#{id}")
    int update(FishInput fishInput);

    @Delete("delete from fishinput where id = #{id}")
    int deleteById(Integer id);

    @Insert("insert into fishinput (type,amount,unit,date,pond,batch_number,username) values (#{type},#{amount},#{unit},#{date},#{pond},#{batchNumber},#{username})")
    int insert(FishInput fishInput);



}
