package com.jit.authority.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.authority.commons.util.DynamicSql;

import com.jit.authority.domain.User;
import com.jit.authority.dto.DriverInfo;
import com.jit.authority.dto.DriverNameTel;
import com.jit.authority.dto.UserDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Mapper
@Component(value = "userRepository")
public interface UserMapper extends BaseMapper<User> {

//    @Select("select u.* from user u left join user_role ur on u.id = ur.user_id left join role r on r.id = ur.role_id where username = #{username}")
//    User findByUsername(@Param("username") String username);

    @Select("select u.*, r.name as role from user u left join user_role ur on u.id = ur.user_id left join role r on r.id = ur.role_id where u.username = #{username} or u.tel= #{username}")
    User findByUsernameOrTel(@Param("username") String username);

    @Select("select * from user  where role =#{role} and tel=#{tel}")
    User findByTelAndRole(@Param("tel") String tel,@Param("role")String role);

    @Insert("insert into user_role(user_id,role_id) values(#{user_id},#{role_id})")
    int insertUserRole(@Param("user_id") Integer user_id, @Param("role_id") Integer role_id);

    @Select("SELECT image FROM user WHERE username = #{username}")
    String getUserImage(@Param("username") String username);

    @Update("update user set image = #{image} where username = #{username}")
    void updateUserImage(@Param("username") String username, @Param("image") String image);


    @DeleteProvider(type = DynamicSql.class, method = "deleteUserBatch")
    int deleteUserBatch(@Param("usernames") String usernames);

    @Select("select username from user where id=#{id}")
    String getUsername(@Param("id") Integer id);

    @Select("SELECT video_state FROM user WHERE username = #{username}")
    Boolean getVideoState(@Param("username") String username);

    @Select("UPDATE user SET video_state = #{videoState} WHERE username = #{username}")
    void updateVideoState(@Param("username") String username, @Param("videoState") Boolean videoState);

    //修改密码
    @Update("update user set password = #{password} where username=#{username}")
    int updatePassword(@Param("password") String password, @Param("username") String username);

    @Select("<script>" +
            "select u.id AS id,u.username,u.real_name AS realName,u.image,u.register_time AS registerTime ," +
            "ur.role_id as roleId , r.name as roleName " +
            "FROM user u" +
            " left join user_role ur on ur.user_id = u.id " +
            "left join role r on r.id = ur.role_id " +
            "<if test='username!=null'> where  username=#{username}</if>" +
            "</script>")
    List<UserDto> selectLists(@Param("username") String username);

    @Select("select u.id AS id,u.username,u.real_name AS realName,u.image,u.register_time AS registerTime, ur.role_id as roleId , r.name as roleName  from user u left join user_role ur on ur.user_id=u.id left join role r on r.id=ur.role_id where username=#{username}")
    UserDto selectUser(@Param("username") String username);

    @Select("select u.*, r.name as role from user u left join user_role ur on u.id = ur.user_id left join role r on r.id = ur.role_id ")
    List<UserDto> selectAllUser();

    @Select("select u.*, r.name as role from user u left join user_role ur on u.id = ur.user_id left join role r on r.id = ur.role_id where r.id=#{role_id} ")
    List<UserDto> selectAllUserByRole(@Param(("role_id")) Integer role_id);


    /***
     * 司机管理
     */

    @Select("SELECT u.*,u.username as name FROM user u WHERE u.belong_user_id = #{belongUserId} AND u.tel = #{tel}")
    DriverInfo getByDriverTel(@Param("belongUserId") Integer belongUserId, @Param("tel") String tel);

    @Select("SELECT u.*,u.username as name FROM user  u WHERE belong_user_id = #{belongUserId} AND name = #{driverName}")
    DriverInfo getByDriverName(@Param("belongUserId") Integer belongUserId, @Param("driverName") String driverName);

    @Select("select count(id) from user WHERE belong_user_id = #{belongUserId}")
    int getTotalNumber(@Param("belongUserId") Integer belongUserId);

    @Select("SELECT u.*,u.username as name FROM user u WHERE belong_user_id = #{belongUserId} ORDER BY license_num DESC")
    List<DriverInfo> getAllDriverByPage(@Param("belongUserId") Integer belongUserId);

    @Select("SELECT username as name,tel FROM user WHERE belong_user_id = #{belongUserId} ORDER BY license_num DESC")
    List<DriverNameTel> getAllNameTel(@Param("belongUserId") Integer belongUserId);

    @Select("SELECT * FROM user WHERE belong_user_id = #{belongUserId} AND username = #{name}")
    List<DriverInfo> findDriverList(@Param("name") String name, @Param("belongUserId") Integer belongUserId);

}


