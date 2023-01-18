package com.glowterx.glowterx.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glowterx.glowterx.DOA.AdminDAO;
import com.glowterx.glowterx.Model.Admin;
import com.glowterx.glowterx.Model.Instructor;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
    @Autowired
    AdminDAO adminDAO;

    @RequestMapping(value = "/")
    public String firstpage() {
        return "fitnesslogin";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "fitnesslogin";
    }

    @GetMapping("/register")
    public String register() {
        return "Register";
    }

    @GetMapping("/manageTraining")
    public String adminManageTraining(Model model) {
        List<Instructor> instructors = adminDAO.getAllInstructors();
        model.addAttribute("instructors", instructors);
        return "Admin/CreateTrainingClass";
    }

    @GetMapping("/adminProfile")
    public String adminProfile(Model model, HttpSession session) {
        Admin admin = adminDAO.getInfoAdmin();
        model.addAttribute("admin", admin);
        return "Admin/ProfileDetails";
    }
}
