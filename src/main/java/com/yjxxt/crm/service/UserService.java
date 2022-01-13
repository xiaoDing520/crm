package com.yjxxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.sun.source.tree.AssertTree;
import com.yjxxt.crm.base.BaseService;
import com.yjxxt.crm.mapper.UserMapper;
import com.yjxxt.crm.mapper.UserRoleMapper;
import com.yjxxt.crm.model.UserModel;
import com.yjxxt.crm.pojo.Role;
import com.yjxxt.crm.pojo.User;
import com.yjxxt.crm.pojo.UserRole;
import com.yjxxt.crm.query.UserQuery;
import com.yjxxt.crm.utils.AssertUtil;
import com.yjxxt.crm.utils.Md5Util;
import com.yjxxt.crm.utils.PhoneUtil;
import com.yjxxt.crm.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName UserService
 * @Desc 用户业务逻辑
 * @Author xiaoding
 * @Date 2021-12-28 15:14
 * @Version 1.0
 */
@Service
public class UserService extends BaseService<User,Integer> {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private UserRoleMapper userRoleMapper;

    public UserModel userLogin(User user) {
        //判断用户名和密码是否为空
        checkLoginParams(user.getUserName(),user.getUserPwd());
        //判断用户是否存在
        User user1 = userMapper.selectUserByName(user.getUserName());
        AssertUtil.isTrue(user1==null,"用户不存在");
        //判断用户名和密码是否正确
        checkLoginPwd(user.getUserPwd(),user1.getUserPwd());
        //返回对象模型
        return buildUserInfo(user1);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePassword(int userId,String oldPwd,String newPwd,String confirmPwd) {
        //根据id查询用户
        User user = userMapper.selectByPrimaryKey(userId);
        //判断用户是否为空
        AssertUtil.isTrue(user == null,"用户为空或已注销");
        //校验密码
        checkPasswordParams(user,oldPwd,newPwd,confirmPwd);
        //修改密码
        user.setUserPwd(Md5Util.encode(newPwd));
        //判断是否修改成功
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1,"密码修改失败");
    }

    public List<Map<String,Object>> selectAssignMan() {
        return userMapper.selectAssignMan();
    }

    public Map<String,Object> selectByParams(UserQuery userQuery) {
        //判断对象是否为空
        AssertUtil.isTrue(userQuery == null,"查询参数为空");
        //创建Map对象
        Map<String,Object> map = new HashMap<>();
        //添加分页
        PageHelper.startPage(userQuery.getPage(),userQuery.getLimit());
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectByParams(userQuery));
        //添加数据
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        //返回map对象
        return map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user) {
        //判断对象是否为空
        checkUser(user);
        //数据的校验
        checkParams(user);
        //设置默认值
        //创建时间和修改时间
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        //设置用户状态
        user.setIsValid(1);
        //设置密码
        user.setUserPwd(Md5Util.encode("123456"));
        //判断是否添加成功
        AssertUtil.isTrue(userMapper.insertSelective(user) < 1,"添加失败");
        //添加用户_角色中间表
        addOrUpdateUserRole(user.getId(),user.getRoleId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {
        System.out.println(user);
        //判断对象是否为空
        checkUser(user);
        //查询用户是否存在
        AssertUtil.isTrue(userMapper.selectByPrimaryKey(user.getId()) == null,"代修改的记录不存在");
        //数据校验
        checkParams(user);
        //修改时间
        user.setUpdateDate(new Date());
        //判断是否修改成功
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1,"修改失败");
        //修改用户角色
        addOrUpdateUserRole(user.getId(),user.getRoleId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteParams(Integer[] id) {
        //判断数组是否为空
        AssertUtil.isTrue(id == null,"参数不能为空");
        //调用删除的方法
        userMapper.deleteBatch(id);
    }

    /*
     * @Method buildUserInfo
     * @Description 根据参数，返回用户消息模型对象
     * @Date 19:32 2021/12/28
     * @Param [userName, trueName]
     * @return com.yjxxt.crm.model.UserModel
     */
    private UserModel buildUserInfo(User user) {
        //创建用户消息模型对象
        UserModel model = new UserModel(UserIDBase64.encoderUserID(user.getId()),user.getUserName(),user.getTrueName());
        //返回消息对象
        return model;
    }

    /*
     * @Method checkLoginParams
     * @Description 校验用户名和密码是否为空
     * @Date 15:29 2021/12/28
     * @Param [userName, userPwd]
     * @return void
     */
    private void checkLoginParams(String userName,String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName) || StringUtils.isBlank(userPwd),"用户名和密码不能为空");
    }

    /*
     * @Method checkLoginPwd
     * @Description 判断密码是否正确
     * @Date 15:32 2021/12/28
     * @Param [userPwd, md5Pwd]
     * @return void
     */
    private void checkLoginPwd(String userPwd,String md5Pwd) {
        //使用md5加密数据
        userPwd = Md5Util.encode(userPwd);
        //判断密码是否正确
        AssertUtil.isTrue(!userPwd.equals(md5Pwd),"用户名或密码不正确");
    }

    /*
     * @Method checkPasswordParams
     * @Description 修改密码校验
     * @Date 16:26 2021/12/30
     * @Param [user, oldPwd, newPwd, confirmPwd]
     * @return void
     */
    private void checkPasswordParams(User user,String oldPwd,String newPwd,String confirmPwd) {

        //原始密码不能为空
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd),"原始密码不能为空");
        //原始密码是否和数据库中查询到的密码一致
        AssertUtil.isTrue(!(Md5Util.encode(oldPwd).equals(user.getUserPwd())),"原始密码不对");
        //新密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(newPwd),"新密码不能为空");
        //新密码是否和原密码是否一致
        AssertUtil.isTrue(oldPwd.equals(newPwd),"原始密码不能和新密码一致");
        //确定密码是不不能为空
        AssertUtil.isTrue(StringUtils.isBlank(confirmPwd),"确认密码不能为空");
        //新密码是否和确定密码一致
        AssertUtil.isTrue(!newPwd.equals(confirmPwd),"新密码必须和确认密码保持一致");
    }

    /*
     * @Method checkParams
     * @Description 校验添加和修改参数
     * @Date 16:37 2021/12/30
     * @Param [user]
     * @return void
     */
    private void checkParams(User user) {
        //判断用户名是否为空
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()),"用户名不能为空");
        //根据用户名查询
        User temp = userMapper.selectUserByName(user.getUserName());
        //判断是否是添加还是修改
        if (user.getId() == null) {
            //判断用户是否以存在
            AssertUtil.isTrue(temp != null,"用户已存在");
        }else {
            //查询修改的用户是否存在
            AssertUtil.isTrue(temp != null && !temp.getId().equals(user.getId()),"用户名重复");
        }
        //判断邮箱是否为空
        AssertUtil.isTrue(StringUtils.isBlank(user.getEmail()),"邮箱不能Wie空");
        //判断手机号是否合法
        AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone()),"请输入正确的手机号");
    }

    /*
     * @Method checkUser
     * @Description 判断用户对象是否存在
     * @Date 17:55 2021/12/30
     * @Param [user]
     * @return void
     */
    private void checkUser(User user) {
        AssertUtil.isTrue(user == null,"参数不能为空");
    }

    /*
     * @Method addOrUpdateUserRole
     * @Description 添加和删除用户角色业务
     * @Date 2:32 2021/12/31
     * @Param [id, roleId]
     * @return void
     */
    private void addOrUpdateUserRole(Integer id, String roleId) {
        //判断是否为空
        AssertUtil.isTrue(StringUtils.isBlank(roleId),"请选择角色Id");
        //统计个数
        int count = userRoleMapper.selectUserRoleCount(id);
        //判断个数，是否进行删除，删除是否成功
        if (count > 0) {
            AssertUtil.isTrue(userRoleMapper.deleteById(id) != count,"角色删除失败");
        }
        //将字符串解析成数组,使用List添加
        String[] arry = roleId.split(",");
        List<UserRole> list = new ArrayList<>();
        //循环遍历添加
        for (int i = 0;i<arry.length;i++) {
            //创建用户_角色对象
            UserRole userRole = new UserRole();
            //添加数据
            userRole.setUserId(id);
            userRole.setRoleId(Integer.parseInt(arry[i]));
            //设置默认值
            userRole.setCreateDate(new Date());
            userRole.setUpdateDate(new Date());
            //list添加数据
            list.add(userRole);
        }
        //判断是否添加成功
        AssertUtil.isTrue(userRoleMapper.insertBatch(list) != arry.length,"角色添加失败");
    }
}
