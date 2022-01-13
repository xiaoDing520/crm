package com.yjxxt.crm.controller;

import com.yjxxt.crm.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName PermissionController
 * @Desc 资源_角色控制层
 * @Author xiaoding
 * @Date 2022-01-01 17:20
 * @Version 1.0
 */
@Controller
@RequestMapping("/permissionController")
public class PermissionController {
    @Autowired(required = false)
    private PermissionService permissionService;
}
