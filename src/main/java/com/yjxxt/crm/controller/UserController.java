package com.yjxxt.crm.controller;

import com.yjxxt.crm.annotation.RequirePermission;
import com.yjxxt.crm.base.BaseController;
import com.yjxxt.crm.base.ResultInfo;
import com.yjxxt.crm.model.UserModel;
import com.yjxxt.crm.pojo.User;
import com.yjxxt.crm.query.UserQuery;
import com.yjxxt.crm.service.UserService;
import com.yjxxt.crm.utils.AssertUtil;
import com.yjxxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.List;

/**
 * @ClassName UserController
 * @Desc 用户控制层
 * @Author xiaoding
 * @Date 2021-12-28 15:44
 * @Version 1.0
 */
@Controller
@RequestMapping("/userController")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    //跳转到修改密码的页面
    @RequestMapping("/toPasswordPage")
    public String toPasswordPage() {
        return "user/password";
    }

    //跳转到基本信息
    @RequestMapping("/toSettingPage")
    public String toSettingPage(HttpServletRequest request) {
        //获取cookie
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //根据id查询信息
        User user = userService.selectByPrimaryKey(userId);
        //存储数据
        request.setAttribute("user",user);
        //返回视图
        return "user/setting";
    }

    //跳转到首页
    @RequestMapping("/index")
    public String index() {
        return "user/user";
    }

    //添加和修改页面跳转
    @RequestMapping("/addOrUpdate")
    public String addOrUpdate(Integer id, Model model) {
        //判断是添加还是修改
        if (id != null) {
            System.out.println("用户");
            //根据id查询用户
            model.addAttribute("user",userService.selectByPrimaryKey(id));
        }
        return "user/add_update";
    }

    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo userLogin(HttpServletRequest request,User user) {
        //创建消息模型对象
        ResultInfo result = new ResultInfo();
        //调用查询的方法
        UserModel userModel = userService.userLogin(user);
        result.setResult(userModel);

        //返回消息模型对象
        return result;
    }

    @PostMapping("/updatePwd")
    @ResponseBody
    public ResultInfo updatePwd(HttpServletRequest request,String oldPwd,String newPwd,String confirmPwd) {
        //创建消息模型对象
        ResultInfo result = new ResultInfo();
        //根据cookei获取id
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //调用修改密码的方法
        userService.updatePassword(userId,oldPwd,newPwd,confirmPwd);
        //操作成功
        result.setMsg("修改密码成功");

        //返回消息对象模型
        return result;
    }

    @PostMapping("/updateUser")
    @ResponseBody
    public ResultInfo updateUser(User user) {
        //创建ResultInfo对象
        ResultInfo result = new ResultInfo();
        //调用查询
        int num = userService.updateByPrimaryKeySelective(user);
        //判断是否修改成功
        AssertUtil.isTrue(num < 0,"修改失败");
        result.setMsg("修改成功");
        //返回消息对象
        return result;
    }

    @PostMapping("/selectAssignMan")
    @ResponseBody
    public List<Map<String,Object>> selectAssignMan() {
        return userService.selectAssignMan();
    }

    @RequirePermission(code = "601002")
    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> querySaleChanceByParams(UserQuery userQuery) {
        return userService.selectByParams(userQuery);
    }

    //定义添加方法
    @PostMapping("/addUser")
    @ResponseBody
    public ResultInfo addUser(User user) {
        //调用添加的方法
        userService.addUser(user);
        //返回消息对象模型
        return success("添加成功");
    }

    //定义修改的方法
    @PostMapping("/updateUser2")
    @ResponseBody
    public ResultInfo updateUser2(User user) {
        //调用修改的方法
        userService.updateUser(user);
        //返回消息模型对象
        return success("添加成功");
    }

    //定义删除的方法
    @PostMapping("/deleteParams")
    @ResponseBody
    public ResultInfo deleteParams(Integer[] id) {
        System.out.println(Arrays.toString(id));
        //调用删除的方法
        userService.deleteParams(id);
        //返回消息对象模型
        return success("删除成功");
    }
}
