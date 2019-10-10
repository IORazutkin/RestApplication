package com.mrEasy.RestApplication.controller;

import com.mrEasy.RestApplication.BreadcrumbsItem;
import com.mrEasy.RestApplication.UserProfile;
import com.mrEasy.RestApplication.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(@AuthenticationPrincipal User user, Model model) {
        Map<Object, Object> data = new HashMap<>();
        if (user != null) {
            data.put("profile", new UserProfile(user.getUserId(), user.getFullName(), user.getApartment(), user.getRoles()));
        }
        model.addAttribute("frontendData", data);
        model.addAttribute("breadcrumbs", new ArrayList<BreadcrumbsItem>() {{ add(new BreadcrumbsItem("Main page", false, "/"));}});

        return "index";
    }
}