package com.jit.aquaculture.mapper.pond;

import com.jit.aquaculture.domain.pond.Pond;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface PondMapper {

    @Insert("insert into pond (name,length,width,depth,orientation,longitude,latitude,address,product,username)values (#{name},#{length},#{width},#{depth},#{orientation},#{longitude},#{latitude},#{address},#{product},#{username})")
    void addpond (Pond pond);
    @Select("select * from pond where id = #{id} and username=#{username}")
    Pond selectById(Integer id,String username);
    @Select("select * from pond where username=#{username}")
    List<Pond> selectAll(String username);
    @Delete("delete from pond where id =#{id}")
    void deletepond (Integer id);
    @Update("update pond set name=#{name},length=#{length},width=#{width},depth=#{depth},orientation=#{orientation},longitude=#{longitude},latitude=#{latitude},address=#{address},product=#{product},username=#{username} where id = #{id}")
    void updateappond(Pond pond);

}
