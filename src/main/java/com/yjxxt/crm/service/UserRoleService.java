package com.yjxxt.crm.service;

import com.yjxxt.crm.base.BaseService;
import com.yjxxt.crm.mapper.UserRoleMapper;
import com.yjxxt.crm.pojo.UserRole;
import com.yjxxt.crm.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserRoleService
 * @Desc 用户_角色中间业务逻辑类
 * @Author xiaoding
 * @Date 2021-12-31 0:39
 * @Version 1.0
 */
@Service
public class UserRoleService extends BaseService<UserRole,Integer> {
    @Autowired(required = false)
    private UserRoleMapper userRoleMapper;

    public void deleteById(int userId) {
        AssertUtil.isTrue(userRoleMapper.deleteById(userId)<1,"删除失败");
    }

    public int selectUserRoleCount(int userId) {
        return userRoleMapper.selectUserRoleCount(userId);
    }

}
