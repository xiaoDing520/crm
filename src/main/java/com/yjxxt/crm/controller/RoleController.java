package com.yjxxt.crm.controller;

import com.yjxxt.crm.base.BaseController;
import com.yjxxt.crm.base.ResultInfo;
import com.yjxxt.crm.pojo.Role;
import com.yjxxt.crm.query.RoleQuery;
import com.yjxxt.crm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;
import java.util.List;

/**
 * @ClassName RoleController
 * @Desc 角色控制层
 * @Author xiaoding
 * @Date 2021-12-30 23:19
 * @Version 1.0
 */
@Controller
@RequestMapping("/roleController")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/index")
    public String index() {
        return "role/role";
    }

    @RequestMapping("/addOrUpdate")
    public String addOrUpdate(Integer id, Model model) {
        //判断用户id是否为空
        if (id != null) {
            model.addAttribute("role",roleService.selectByPrimaryKey(id));
        }
        //返回页面
        return "role/add_update";
    }

    @PostMapping("/selectRoleAll")
    @ResponseBody
    public List<Map<String,Object>> selectRoleAll(Integer userId) {
        return roleService.selectRoleAll(userId);
    }

    @RequestMapping("/roleMandate")
    public String roleMandate(int roleId,Model model) {
        //添加作用域
        model.addAttribute("roleId",roleId);
        //返回视图
        return "role/grant";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> list(RoleQuery roleQuery) {
        return roleService.selectByParams(roleQuery);
    }

    @PostMapping("/addRole")
    @ResponseBody
    public ResultInfo addRole(Role role) {
        //调用添加方法
        roleService.addRole(role);
        //返回消息模型对象
        return success("添加成功");
    }

    @PostMapping("/updateRole")
    @ResponseBody
    public ResultInfo updateRole(Role role) {
        //调用修改的方法
        roleService.updateRole(role);
        //返回消息模型对象
        return success("修改成功");
    }

    @PostMapping("/deleteParams")
    @ResponseBody
    public ResultInfo deleteParams(Integer[] id) {
        //调用删除的方法
        roleService.deleteParams(id);
        //返回消息模型对象
        return success("删除成功");
    }

    @PostMapping("/addRoleModel")
    @ResponseBody
    public ResultInfo addRoleModel(Integer roleId,Integer[] modelId) {
        //调用添加方法
        roleService.addRoleGrant(roleId,modelId);
        //返回消息对象模型
        return success("角色授权成功");
    }
}
