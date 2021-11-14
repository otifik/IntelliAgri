package com.jit.usercenter.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.usercenter.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository(value="userRoleMapper")
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
