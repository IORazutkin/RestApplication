package com.mrEasy.RestApplication;

import com.mrEasy.RestApplication.entity.Apartment;
import com.mrEasy.RestApplication.entity.Role;

import java.util.Set;

public class UserProfile {
    private Long userId;
    private String fullName;
    private Apartment apartment;
    private Set<Role> roles;

    public UserProfile(Long userId, String fullName, Apartment apartment, Set<Role> roles) {
        this.userId = userId;
        this.fullName = fullName;
        this.apartment = apartment;
        this.roles = roles;
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
