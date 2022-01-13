package com.yjxxt.crm.mapper;

import com.yjxxt.crm.base.BaseMapper;
import com.yjxxt.crm.pojo.User;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;
import java.util.List;

/**
 * @ClassName UserMapper
 * @Desc 用户映射接口
 * @Author xiaoding
 * @Date 2021-12-28 15:12
 * @Version 1.0
 */
public interface UserMapper extends BaseMapper<User,Integer> {
    public User selectUserByName(String userName);
    public List<Map<String,Object>> selectAssignMan();
}