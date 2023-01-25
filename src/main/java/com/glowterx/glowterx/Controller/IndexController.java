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
import com.glowterx.glowterx.DOA.TrainingDAO;
import com.glowterx.glowterx.Model.Admin;
import com.glowterx.glowterx.Model.Instructor;
import com.glowterx.glowterx.Model.Product;
import com.glowterx.glowterx.Model.Trainee;
import com.glowterx.glowterx.Model.Training;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
    @Autowired
    AdminDAO adminDAO;

    @Autowired
    TraineeDAO traineeDAO;

    @Autowired
    TrainingDAO trainingDAO;

    @Autowired
    InstructorDAO instructorDAO;

    @Autowired
    ProductDAO productDAO;

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

    // Huda - Admin Generate Report Trainee & Instructor begins here
    @GetMapping("/reportTrainee")
    public String adminGenerateReportTrainee (Model model) {
        //List<Instructor> instructors = adminDAO.getAllInstructors();
        //model.addAttribute("instructors", instructors);
        return "Admin/ReportTrainee";
    }

    @GetMapping("/reportInstructor")
    public String adminGenerateReportInstructor (Model model) {
        //List<Instructor> instructors = adminDAO.getAllInstructors();
        //model.addAttribute("instructors", instructors);
        return "Admin/ReportInstructor";
    }

    @GetMapping("/gReportTrainee")
    public String adminGeneratedReportTrainee (Model model) {
        //List<Instructor> instructors = adminDAO.getAllInstructors();
        //model.addAttribute("instructors", instructors);
        return "Admin/ReportTrID";
    }

    @GetMapping("/gReportInstructor")
    public String adminGeneratedReportInstructor (Model model) {
        //List<Instructor> instructors = adminDAO.getAllInstructors();
        //model.addAttribute("instructors", instructors);
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
    @GetMapping ("/manageTrainingClass")
    public String viewListTraining(Model model)
    {   List <Training> training = trainingDAO.getAllTraining();
        model.addAttribute("training",training);
        return "/Admin/ManageTrainingClass";
    }
}
