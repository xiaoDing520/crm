package com.yjxxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.source.tree.AssertTree;
import com.yjxxt.crm.base.BaseService;
import com.yjxxt.crm.mapper.SaleChanceMapper;
import com.yjxxt.crm.mapper.UserMapper;
import com.yjxxt.crm.pojo.SaleChance;
import com.yjxxt.crm.pojo.User;
import com.yjxxt.crm.query.SaleChanceQuery;
import com.yjxxt.crm.utils.AssertUtil;
import com.yjxxt.crm.utils.PhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @ClassName SaleChanceService
 * @Desc 营销机会业务逻辑类
 * @Author xiaoding
 * @Date 2021-12-29 11:29
 * @Version 1.0
 */
@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {
    @Autowired(required = false)
    private SaleChanceMapper saleChanceMapper;

    public Map<String,Object> selectByParams(SaleChanceQuery saleChanceQuery) {
        //判断对象是否为空
        AssertUtil.isTrue(saleChanceQuery == null,"查询参数为空");
        //创建Map对象
        Map<String,Object> map = new HashMap<>();
        //添加分页数据
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getLimit());
        //添加数据
        List<SaleChance> list = saleChanceMapper.selectByParams(saleChanceQuery);
        //创建分页对象
        PageInfo<SaleChance> pageInfo = new PageInfo<>(list);
        //添加数据
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        //返回map对象
        return map;
    }

    //添加数据
    @Transactional(propagation = Propagation.REQUIRED)
    public void addSaleChance(SaleChance saleChance) {
        //数据校验
        checkSalChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        //分配给谁
        saleChance.setState(0);
        saleChance.setDevResult(0);
        if (!StringUtils.isBlank(saleChance.getAssignMan())) {
            saleChance.setState(1);
            saleChance.setDevResult(1);
            saleChance.setAssignTime(new Date());
        }
        //创建和修改时间以及分配时间
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());

        //判断是否添加成功
        AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance) < 1,"添加失败");
    }

    //修改数据
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChance(SaleChance saleChance) {
        //判断用户是否存在
        SaleChance saleChance1 = saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(saleChance1 == null,"代修改的用户不存在");
        //数据校验
        checkSalChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        //state -> 分配 -> 谁负责 -> 分配时间
        if (StringUtils.isBlank(saleChance.getAssignMan()) && StringUtils.isNotBlank(saleChance1.getAssignMan())) {
            saleChance.setState(0);
            saleChance.setDevResult(0);
            saleChance.setAssignTime(null);
            saleChance.setAssignMan(null);
        }
        if (StringUtils.isNotBlank(saleChance.getAssignMan()) && StringUtils.isNotBlank(saleChance1.getAssignMan())) {
            saleChance.setState(1);
            saleChance.setDevResult(1);
            saleChance.setAssignTime(new Date());
        }
        //判断是否修改成功
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance) < 1,"修改失败");
    }

    //多条数据删除
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteParams(Integer[] id) {
        //判断数组是否空
        AssertUtil.isTrue(id == null || id.length == 0,"请选择要删除的数据");
        //判断是否删除成功
        AssertUtil.isTrue(saleChanceMapper.deleteBatch(id) < 1,"数据删除失败");
    }

    /*
     * @Method checkSalChanceParams
     * @Description  校验：用户名、练习人、练习电话是否非空
     * @Date 17:18 2021/12/29
     * @Param [saleChance]
     * @return void
     */
    private void checkSalChanceParams(String customerName,String linkMan,String linkPhone){
        //判断用户名是否为空
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"用户名不能为空");
        //联系人是否为空
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"练习人不能为空");
        //练习电话是否为空
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"；练习电话不能为空");
        //判断手机格式是否正确
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone),"请输入正确的手机号");
    }
}
