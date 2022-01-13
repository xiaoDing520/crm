package com.yjxxt.crm.model;

/**
 * @ClassName UserModel
 * @Desc 用户模型对象
 * @Author xiaoding
 * @Date 2021-12-28 15:12
 * @Version 1.0
 */
public class UserModel {
    private String id;
    private String userName;
    private String trueName;

    public UserModel() {
    }

    public UserModel(String id, String userName, String trueName) {
        this.id = id;
        this.userName = userName;
        this.trueName = trueName;
    }
    public UserModel(String userName, String trueName) {
        this.userName = userName;
        this.trueName = trueName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", trueName='" + trueName + '\'' +
                '}';
    }
}
