package com.yjxxt.crm.query;

import com.yjxxt.crm.base.BaseQuery;

/**
 * @ClassName SaleChanceQuery
 * @Desc 营销模块消息查询对象
 * @Author xiaoding
 * @Date 2021-12-29 13:02
 * @Version 1.0
 */
public class SaleChanceQuery extends BaseQuery {
    private String customerName;
    private String createMan;
    private String state;

    public SaleChanceQuery(String customerName, String createMan, String state) {
        this.customerName = customerName;
        this.createMan = createMan;
        this.state = state;
    }

    public SaleChanceQuery() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SaleChanceQuery{" +
                "customerName='" + customerName + '\'' +
                ", createMan='" + createMan + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
