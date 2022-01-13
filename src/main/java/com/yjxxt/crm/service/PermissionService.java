package com.yjxxt.crm.service;

import com.yjxxt.crm.base.BaseService;
import com.yjxxt.crm.mapper.PermissionMapper;
import com.yjxxt.crm.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PermissionService
 * @Desc 资源_角色业务逻辑层
 * @Author xiaoding
 * @Date 2022-01-01 17:19
 * @Version 1.0
 */
@Service
public class PermissionService extends BaseService<Permission,Integer> {
    @Autowired(required = false)
    private PermissionMapper permissionMapper;

    public List<String> selectByUserIdRoleModelCode(Integer userId) {
        return permissionMapper.selectByUserIdRoleModelCode(userId);
    }
}
