package com.yjxxt.crm.query;

import com.yjxxt.crm.base.BaseQuery;

/**
 * @ClassName RoleQuery
 * @Desc 角色查询
 * @Author xiaoding
 * @Date 2021-12-30 23:21
 * @Version 1.0
 */
public class RoleQuery extends BaseQuery {
    private String roleName;

    public RoleQuery() {
    }
    public RoleQuery(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "RoleQuery{" +
                "roleName='" + roleName + '\'' +
                '}';
    }
}
