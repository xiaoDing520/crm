package com.yjxxt.crm.controller;

import com.yjxxt.crm.base.BaseController;
import com.yjxxt.crm.dto.TreeDto;
import com.yjxxt.crm.pojo.Model;
import com.yjxxt.crm.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ModelController
 * @Desc 资源控制层
 * @Author xiaoding
 * @Date 2022-01-01 17:17
 * @Version 1.0
 */
@Controller
@RequestMapping("/modelController")
public class ModelController extends BaseController {
    @Autowired
    private ModelService modelService;

    //访问首页
    @RequestMapping("/index")
    public String index() {
        return "model/model";
    }

    @PostMapping("/selectModelAll")
    @ResponseBody
    public List<TreeDto> selectModelAll(Integer roleId){
        return modelService.selectModelAll(roleId);
    }

//    @RequestMapping("/selectAllModel")
//    @ResponseBody
//    public List<Model> selectAllModel() {
//        return modelService.selectAllModel();
//    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> list () {
        return modelService.selectAllModel();
    }
}
