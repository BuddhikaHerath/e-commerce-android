package com.example.my_store.Model;

import com.google.gson.annotations.SerializedName;

public class Role {
    @SerializedName("roleId")
    private int roleId;
    @SerializedName("role")
    private String role;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
