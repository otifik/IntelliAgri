package com.jit.aquaculture.mapper.Input;
import com.jit.aquaculture.domain.Input.input;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface InputMapper {
    @Select("select * from input where username=#{username} ")
    List<input> selectAll(String username);
    @Select("select * from input where id = #{id} and  username=#{username}")
    input selectById(Integer id,String username);
    @Insert("insert into input (type,name,manufacture,pictures,remarks,username) values (#{type},#{name},#{manufacture},#{pictures},#{remarks},#{username})")
    void addinput (input input);
    @Delete("delete from input where id =#{id}")
    void deleteinput (Integer id);

    @Update("update input set type = #{type}, name = #{name}, manufacture = #{manufacture}, pictures = #{pictures}, remarks = #{remarks}, username = #{username} where id = #{id}")
    void update(input input);
}
