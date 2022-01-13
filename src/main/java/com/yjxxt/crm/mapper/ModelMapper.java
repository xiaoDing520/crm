package com.yjxxt.crm.mapper;

import com.yjxxt.crm.base.BaseMapper;
import com.yjxxt.crm.dto.TreeDto;
import com.yjxxt.crm.pojo.Model;

import java.util.List;

public interface ModelMapper extends BaseMapper<Model,Integer>{
    public List<TreeDto> selectModelAll();
    public List<Model> selectAllModel();
}