package com.web.book.entity;

/**
 * @anthor sily
 * @date 2019/12/24 - 20:12
 */
public class Admin {
    private String adminId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    private  String adminPassword;

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                '}';
    }
}
