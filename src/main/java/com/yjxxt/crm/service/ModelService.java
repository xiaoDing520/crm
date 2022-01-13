package com.yjxxt.crm.service;

import com.yjxxt.crm.base.BaseService;
import com.yjxxt.crm.dto.TreeDto;
import com.yjxxt.crm.mapper.ModelMapper;
import com.yjxxt.crm.mapper.PermissionMapper;
import com.yjxxt.crm.pojo.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ModelService
 * @Desc 资源模块业务逻辑类
 * @Author xiaoding
 * @Date 2022-01-01 17:15
 * @Version 1.0
 */
@Service
public class ModelService extends BaseService<Model,Integer> {
    @Autowired(required = false)
    private ModelMapper modelMapper;
    @Autowired(required = false)
    private PermissionMapper permissionMapper;

    public List<TreeDto> selectModelAll(Integer roleId) {
        //查询全部数据
        List<TreeDto> list = modelMapper.selectModelAll();
        //根据角色id查询对应的资源id
        List<Integer> arry = permissionMapper.selectByRoleId(roleId);
        //循环判断，设置选中状态
        for (int i = 0;i<list.size();i++) {
            if (arry.contains(list.get(i).getId())) {
                list.get(i).setChecked(true);
            }
        }
        //返回数据
        return list;
    }

    public Map<String,Object> selectAllModel() {
        //创建Map对象
        Map<String,Object> map = new HashMap<>();
        //创建List
        List<Model> list = modelMapper.selectAllModel();
        map.put("code",0);
        map.put("msg","success");
        map.put("count",list.size());
        map.put("data",list);
        //返回数据
        return map;
    }
}
