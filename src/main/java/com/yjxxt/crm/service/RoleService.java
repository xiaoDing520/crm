package com.yjxxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjxxt.crm.base.BaseService;
import com.yjxxt.crm.mapper.ModelMapper;
import com.yjxxt.crm.mapper.PermissionMapper;
import com.yjxxt.crm.mapper.RoleMapper;
import com.yjxxt.crm.pojo.Permission;
import com.yjxxt.crm.pojo.Role;
import com.yjxxt.crm.query.RoleQuery;
import com.yjxxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName RoleService
 * @Desc 角色逻辑业务层
 * @Author xiaoding
 * @Date 2021-12-30 23:18
 * @Version 1.0
 */
@Service
public class RoleService extends BaseService<Role,Integer> {
    @Autowired(required = false)
    private RoleMapper roleMapper;
    @Autowired(required = false)
    private ModelMapper modelMapper;
    @Autowired(required = false)
    private PermissionMapper permissionMapper;

    public List<Map<String,Object>> selectRoleAll(Integer userId) {
        return roleMapper.selectRoleAll(userId);
    }

    public Map<String,Object> selectByParams(RoleQuery roleQuery) {
        //创建Map对象
        Map<String,Object> map = new HashMap<>();
        //创建分页
        PageHelper.startPage(roleQuery.getPage(),roleQuery.getLimit());
        PageInfo<Role> pageInfo = new PageInfo<>(roleMapper.selectByParams(roleQuery));
        //添加数据
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        //返回Map对象
        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addRole(Role role) {
        //判断对象是否为空
        checkRole(role);
        //数据校验
        checkAddOrUpdateParams(role);
        //设置默认值
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        //判断是否添加成功
        AssertUtil.isTrue(roleMapper.insertSelective(role) < 1,"角色添加失败");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRole(Role role) {
        //判断对象是否为空
        checkRole(role);
        //根据id查询用户
        AssertUtil.isTrue(roleMapper.selectByPrimaryKey(role.getId())==null,"待修改的记录不存在");
        //参数校验
        checkAddOrUpdateParams(role);
        //设置默认值
        role.setUpdateDate(new Date());
        //判断是否修改成功
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role) < 1,"修改失败");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteParams(Integer[] id) {
        //判断数组是否为空
        AssertUtil.isTrue(id == null,"参数为空");
        //调用删除的方法
        AssertUtil.isTrue(roleMapper.deleteBatch(id) != id.length,"删除失败");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addRoleGrant(Integer roleId,Integer[] modelId) {
        //判断角色id是否为空
        AssertUtil.isTrue(roleId == null,"请选择角色id");
        //判断数组不能为空
        AssertUtil.isTrue(modelId == null,"请选择授权权限");
        //根据角色id查询资源
        int count = permissionMapper.selectRoleModelCount(roleId);
        //判断是否要删除
        if (count > 0) {
            AssertUtil.isTrue(permissionMapper.deleteByRoleId(roleId) != count,"删除资源失败");
        }
        //定义List集合
        List<Permission> list = new ArrayList<>();
        //循环遍历数组
        for (int i = 0;i<modelId.length;i++) {
            //创建资源对象
            Permission permission = new Permission();
            //添加数据
            permission.setRoleId(roleId);
            permission.setModuleId(modelId[i]);
            permission.setAclValue(modelMapper.selectByPrimaryKey(modelId[i]).getOptValue());
            permission.setCreateDate(new Date());
            permission.setUpdateDate(new Date());
            list.add(permission);
        }
        //判断是否添加成功
        AssertUtil.isTrue(permissionMapper.insertBatch(list)!=modelId.length,"角色资源添加失败");
    }


    /*
     * @Method checkRole
     * @Description 判断对象是否为空
     * @Date 16:19 2021/12/31
     * @Param [role]
     * @return void
     */
    private void checkRole(Role role) {
        AssertUtil.isTrue(role == null,"参数不能为空");
    }

    private void checkAddOrUpdateParams(Role role) {
        //判断角色名是否为空
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名不能为空");
        //根据名称查询角色
        Role temp = roleMapper.selectByName(role.getRoleName());
        //判断是添加还是修改
        if (role.getId() == null) {
            AssertUtil.isTrue(temp!=null,"角色名已存在");
        }else {
            AssertUtil.isTrue(temp!=null && !temp.getId().equals(role.getId()),"角色名已存在");
        }
    }
}
