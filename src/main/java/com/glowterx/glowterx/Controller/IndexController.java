package com.glowterx.glowterx.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glowterx.glowterx.DOA.AdminDAO;
import com.glowterx.glowterx.DOA.InstructorDAO;
import com.glowterx.glowterx.DOA.ProductDAO;
import com.glowterx.glowterx.DOA.TraineeDAO;
import com.glowterx.glowterx.Model.Admin;
import com.glowterx.glowterx.Model.Instructor;
import com.glowterx.glowterx.Model.Product;
import com.glowterx.glowterx.Model.Trainee;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
    @Autowired
    HttpSession session;
    @Autowired
    AdminDAO adminDAO;

    @Autowired
    TraineeDAO traineeDAO;

    @Autowired
    InstructorDAO instructorDAO;

    @Autowired
    ProductDAO productDAO;

    @RequestMapping(value = "/")
    public String firstpage(Model model) {
        boolean check = false;
        // Check if user is already logged in
        if (session.getAttribute("role") == "admin") {
            Admin admin = adminDAO.getInfoAdmin();
            model.addAttribute("admin", admin);
            return "Admin/ProfileDetails";
        } else {
            check = true;
        }
        if (session.getAttribute("role") == "instructor") {
            Instructor instructor = instructorDAO.getInfoinstructor();
            model.addAttribute("instructor", instructor);
            return "Instructor/ProfileDetails";
        } else {
            check = true;
        }
        if (session.getAttribute("role") == "trainee") {
            Trainee trainee = traineeDAO.getInfoTrainee();
            model.addAttribute("trainee", trainee);
            return "Trainee/ProfileDetails";
        } else {
            check = true;
        }
        if (check) {
            return "fitnesslogin";
        }
        return "fitnesslogin";
    }

    @RequestMapping(value = "/index")
    public String index(Model model) {
        boolean check = false;
        // Check if user is already logged in
        if (session.getAttribute("role") == "admin") {
            Admin admin = adminDAO.getInfoAdmin();
            model.addAttribute("admin", admin);
            return "Admin/ProfileDetails";
        } else {
            check = true;
        }
        if (session.getAttribute("role") == "instructor") {
            Instructor instructor = instructorDAO.getInfoinstructor();
            model.addAttribute("instructor", instructor);
            return "Instructor/ProfileDetails";
        } else {
            check = true;
        }
        if (session.getAttribute("role") == "trainee") {
            Trainee trainee = traineeDAO.getInfoTrainee();
            model.addAttribute("trainee", trainee);
            return "Trainee/ProfileDetails";
        } else {
            check = true;
        }
        if (check) {
            return "fitnesslogin";
        }
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

    // Huda - Admin Generate Report Trainee & Instructor begins here
    @GetMapping("/reportTrainee")
    public String adminGenerateReportTrainee(Model model) {
        // List<Instructor> instructors = adminDAO.getAllInstructors();
        // model.addAttribute("instructors", instructors);
        return "Admin/ReportTrainee";
    }

    @GetMapping("/reportInstructor")
    public String adminGenerateReportInstructor(Model model) {
        // List<Instructor> instructors = adminDAO.getAllInstructors();
        // model.addAttribute("instructors", instructors);
        return "Admin/ReportInstructor";
    }

    @GetMapping("/gReportTrainee")
    public String adminGeneratedReportTrainee(Model model) {
        // List<Instructor> instructors = adminDAO.getAllInstructors();
        // model.addAttribute("instructors", instructors);
        return "Admin/ReportTrID";
    }

    @GetMapping("/gReportInstructor")
    public String adminGeneratedReportInstructor(Model model) {
        // List<Instructor> instructors = adminDAO.getAllInstructors();
        // model.addAttribute("instructors", instructors);
        return "Admin/ReportInID";
    }

    // Huda - Admin Generate Report Trainee & Instructor end here sadaqaAllahu adzim

    @GetMapping("/adminProfile")
    public String adminProfile(Model model, HttpSession session) {
        Admin admin = adminDAO.getInfoAdmin();
        model.addAttribute("admin", admin);
        return "Admin/ProfileDetails";
    }

    @GetMapping("/traineeProfile")
    public String traineeProfile(Model model, HttpSession session) {
        Trainee trainee = traineeDAO.getInfoTrainee();
        model.addAttribute("trainee", trainee);
        return "Trainee/ProfileDetails";
    }

    @GetMapping("/instructorProfile")
    public String instructorProfile(Model model, HttpSession session) {
        Instructor instructor = instructorDAO.getInfoinstructor();
        model.addAttribute("instructor", instructor);
        return "Instructor/ProfileDetails";
    }

    @GetMapping("/manageShop")
    public String manageShop(Model model, HttpSession session) {
        List<Product> product = productDAO.getAllProduct();
        model.addAttribute("product", product);
        return "Admin/ManageShop";
    }

    @GetMapping("/manageUser")
    public String manageUser(Model model, HttpSession session) {
        List<Trainee> trainee = adminDAO.getAllTrainee();
        List<Instructor> instructor = adminDAO.getAllInstructors();
        List<Admin> admin = adminDAO.getAllAdmin();
        model.addAttribute("admindata", admin);
        model.addAttribute("instructordata", instructor);
        model.addAttribute("traineedata", trainee);
        return "Admin/ManageUser";
    }
}
