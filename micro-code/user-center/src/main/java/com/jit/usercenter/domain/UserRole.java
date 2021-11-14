package com.jit.usercenter.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
@TableName("user_role")
public class UserRole {
    private Integer id;
    private Integer userId;
    private Integer roleId;
}
