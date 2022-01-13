package com.yjxxt.crm.controller;

import com.yjxxt.crm.base.BaseController;
import com.yjxxt.crm.base.ResultInfo;
import com.yjxxt.crm.pojo.SaleChance;
import com.yjxxt.crm.pojo.User;
import com.yjxxt.crm.query.SaleChanceQuery;
import com.yjxxt.crm.service.SaleChanceService;
import com.yjxxt.crm.service.UserService;
import com.yjxxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.Map;

/**
 * @ClassName SaleChanceController
 * @Desc 营销机会控制层
 * @Author xiaoding
 * @Date 2021-12-29 11:31
 * @Version 1.0
 */
@Controller
@RequestMapping("/saleChanceController")
public class SaleChanceController extends BaseController {
    @Autowired
    private SaleChanceService saleChanceService;
    @Autowired
    private UserService userService;

    //访问首页
    @RequestMapping("/index")
    public String index() {
        return "saleChance/sale_chance";
    }

    //访问添加和修改的页面
    @RequestMapping("/addOrUpdate")
    public String addOrUpdate(@RequestParam(defaultValue = "0") int id, Model model) {
        //判断是添加还是修改
        if (id > 0) {
            //根据id查询用户,并添加作用域
            model.addAttribute("saleChance",saleChanceService.selectByPrimaryKey(id));
        }
        return "saleChance/add_update";
    }


    //查询数据
    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery) {
        //调用查询方法
        return saleChanceService.selectByParams(saleChanceQuery);
    }

    //添加数据
    @PostMapping("/addSaleChance")
    @ResponseBody
    public ResultInfo addSaleChance(HttpServletRequest request, SaleChance saleChance) {
        //根据cookie获取id
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //根据id查询用户
        User user = userService.selectByPrimaryKey(userId);
        //设置创建人
        saleChance.setCreateMan(user.getUserName());
        //调用添加方法
        saleChanceService.addSaleChance(saleChance);
        //消息模型对象
        return success("添加成功");
    }

    //修改数据方法
    @PostMapping("/updateSaleChance")
    @ResponseBody
    public ResultInfo updateSaleChance(SaleChance saleChance) {
        //调用修改方法
        saleChanceService.updateByPrimaryKeySelective(saleChance);
        //返回数据模型
        return success("修改成功");
    }

    //删除多条数据
    @RequestMapping("/deleteParams")
    @ResponseBody
    public ResultInfo deleteParams(Integer[] id) {
        System.out.println(Arrays.toString(id));
        //调用删除数据的方法
        saleChanceService.deleteParams(id);
        //返回消息对象模型
        return success("数据删除成功");
    }
}
