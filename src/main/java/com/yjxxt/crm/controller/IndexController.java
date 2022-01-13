package com.yjxxt.crm.controller;

import com.yjxxt.crm.base.BaseController;
import com.yjxxt.crm.pojo.User;
import com.yjxxt.crm.service.PermissionService;
import com.yjxxt.crm.service.UserService;
import com.yjxxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName IndexController
 * @Desc 首页控制层
 * @Author xiaoding
 * @Date 2021-12-28 11:05
 * @Version 1.0
 */
@Controller
public class IndexController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    /*
     * @Method index
     * @Description 访问首页
     * @Date 11:06 2021/12/28
     * @Param []
     * @return java.lang.String
     */
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /*
     * @Method main
     * @Description 访问后台首页
     * @Date 11:06 2021/12/28
     * @Param []
     * @return java.lang.String
     */
    @RequestMapping("/main")
    public String main(HttpServletRequest request) {
        //获取cookei的id
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //根据id查询用户
        User user = userService.selectByPrimaryKey(userId);
        //存储作用域
        request.setAttribute("user",user);
        //获取用户的权限信息
        request.getSession().setAttribute("permission",permissionService.selectByUserIdRoleModelCode(userId));
        return "main";
    }

    /*
     * @Method welcome
     * @Description 访问欢迎页面
     * @Date 11:08 2021/12/28
     * @Param []
     * @return java.lang.String
     */
    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
