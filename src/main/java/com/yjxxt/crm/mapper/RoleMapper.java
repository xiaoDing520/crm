package com.yjxxt.crm.mapper;

import com.yjxxt.crm.base.BaseMapper;
import com.yjxxt.crm.pojo.Role;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;
import java.util.List;

public interface RoleMapper extends BaseMapper<Role,Integer> {
    @MapKey("")
    public List<Map<String,Object>> selectRoleAll(Integer userId);
    public Role selectByName(String roleName);
}