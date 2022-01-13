package com.yjxxt.crm;

import com.alibaba.fastjson.JSON;
import com.yjxxt.crm.base.ResultInfo;
import com.yjxxt.crm.exception.NoAccessException;
import com.yjxxt.crm.exception.ParamsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName GlobalException
 * @Desc 全局异常处理
 * @Author xiaoding
 * @Date 2021-12-29 9:03
 * @Version 1.0
 */
@Component
public class GlobalException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //判断是否是登录异常
        if (ex instanceof LoginException) {
            ModelAndView model = new ModelAndView();
            //设置数据
            model.setViewName("redirect:/index");
            return model;
        }

        //创建Model对象
        ModelAndView model = new ModelAndView("error");
        //添加数据
        model.addObject("code","500");
        model.addObject("msg","服务器代码错误");
        //判断handler方法，是否为空
        if (handler instanceof HandlerMethod) {
            //类型转换
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //通过反射判断方法上面有没有，注解
            ResponseBody responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            //判断responseBody是否存在
            if (responseBody != null) {
                //创建ResultInfo
                ResultInfo result = new ResultInfo();
                //判断异常类型
                if (ex instanceof ParamsException) {
                    //类型转换
                    ParamsException par = (ParamsException) ex;
                    //添加报错信息
                    result.setCode(par.getCode());
                    result.setMsg(par.getMsg());
                    //发送消息
                    response.setContentType("application/json:charset=utf-8");
                    PrintWriter out = null;
                    try {
                        out = response.getWriter();
                        //将对象数据转换成JSON格式发送
                        out.write(JSON.toJSONString(result));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        if (out != null) {
                            out.close();
                        }
                    }
                }
                if (ex instanceof NoAccessException) {
                    //类型转换
                    NoAccessException no = (NoAccessException)ex;
                    //添加报错信息
                    result.setCode(no.getCode());
                    result.setMsg(no.getMsg());
                    //发送消息
                    response.setContentType("application/json:charset=utf-8");
                    PrintWriter out = null;
                    try {
                        System.out.println(JSON.toJSONString(result));
                        out = response.getWriter();
                        //将对象数据转换成JSON格式发送
                        out.write(JSON.toJSONString(result));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        if (out != null) {
                            out.close();
                        }
                    }
                }
                return null;
            }else {
                //判断异常
                if (ex instanceof ParamsException) {
                    //类型转换
                    ParamsException pe = (ParamsException) ex;
                    //添加报错信息
                    model.addObject("code",pe.getCode());
                    model.addObject("msg",pe.getMsg());
                }
                if (ex instanceof NoAccessException) {
                    //类型转换
                    NoAccessException no = (NoAccessException)ex;
                    //添加报错信息
                    model.addObject("code",no.getCode());
                    model.addObject("msg",no.getMsg());
                }
            }
        }
        return model;
    }
}
