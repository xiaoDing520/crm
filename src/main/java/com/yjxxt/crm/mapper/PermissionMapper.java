package com.yjxxt.crm.mapper;

import com.yjxxt.crm.base.BaseMapper;
import com.yjxxt.crm.pojo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {
    public int selectRoleModelCount(Integer roleId);
    public int deleteByRoleId(Integer roleId);
    public List<Integer> selectByRoleId(Integer roleId);
    public List<String> selectByUserIdRoleModelCode(Integer userId);
}