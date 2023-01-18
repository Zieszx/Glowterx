package com.glowterx.glowterx.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glowterx.glowterx.DOA.AdminDAO;
import com.glowterx.glowterx.DOA.InstructorDAO;
import com.glowterx.glowterx.DOA.TraineeDAO;
import com.glowterx.glowterx.Model.Admin;
import com.glowterx.glowterx.Model.Instructor;
import com.glowterx.glowterx.Model.Trainee;

import jakarta.servlet.http.HttpSession;

@Controller
public class loginController {
    @Autowired
    private AdminDAO adminDAO;

    @Autowired
    private InstructorDAO instructorDAO;

    @Autowired
    private TraineeDAO traineeDAO;

    @GetMapping("/login")
    public String login(HttpSession session) {
        boolean check = false;
        // Check if user is already logged in
        if (session.getAttribute("admin") != null) {
            return "ProfileDetails";
        } else {
            check = true;
        }
        if (session.getAttribute("instructor") != null) {
            return "redirect:/instructor/home";
        } else {
            check = true;
        }
        if (session.getAttribute("trainee") != null) {
            return "redirect:/trainee/home";
        } else {
            check = true;
        }
        if (check) {
            return "login";
        }
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password,
            Model model, HttpSession session) {
        Admin admin = adminDAO.validate(username, password);
        if (admin != null) {
            model.addAttribute("admin", admin);
            session.setAttribute("admin", admin);
            session.setAttribute("role", "admin");
            session.setAttribute("username", admin.getAdminUsername());
            return "Admin/ProfileDetails";
        }
        Instructor instructor = instructorDAO.validate(username, password);
        if (instructor != null) {
            model.addAttribute("instructor", instructor);
            session.setAttribute("instructor", instructor);
            session.setAttribute("role", "instructor");
            session.setAttribute("username", instructor.getInstructorUsername());
            return "instructor_welcome";
        }
        Trainee trainee = traineeDAO.validate(username, password);
        if (trainee != null) {
            model.addAttribute("trainee", trainee);
            session.setAttribute("trainee", trainee);
            session.setAttribute("role", "trainee");
            session.setAttribute("username", trainee.getTraineeUsername());
            return "trainee_welcome";
        } else {
            model.addAttribute("errorMessage", "Invalid credentials");
            return "login";
        }
    }
}
