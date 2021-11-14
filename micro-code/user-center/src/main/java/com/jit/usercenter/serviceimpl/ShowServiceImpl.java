package com.jit.usercenter.serviceimpl;

import com.jit.usercenter.domain.Role;
import com.jit.usercenter.dto.ShowDto;
import com.jit.usercenter.mapper.RoleMapper;
import com.jit.usercenter.mapper.UserMapper;
import com.jit.usercenter.serviceinterface.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<ShowDto> getShowData() {
        List<ShowDto> showDtos = new ArrayList<>();
        List<Role> roleList = roleMapper.findAllRoles();
        for (Role role: roleList){
            ShowDto showDto = new ShowDto();
            showDto = userMapper.selectRoleNum(role.getName());
            if (showDto!=null){
                showDto.setDescribe(role.getDescription());
                showDtos.add(showDto);
            }
        }
        return showDtos;
    }
}
