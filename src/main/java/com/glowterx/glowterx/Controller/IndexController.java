package com.glowterx.glowterx.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glowterx.glowterx.DOA.AdminDAO;
import com.glowterx.glowterx.DOA.CartDAO;
import com.glowterx.glowterx.DOA.InstructorDAO;
import com.glowterx.glowterx.DOA.ProductDAO;
import com.glowterx.glowterx.DOA.TraineeDAO;
import com.glowterx.glowterx.DOA.TrainingDAO;
import com.glowterx.glowterx.Model.Admin;
import com.glowterx.glowterx.Model.Cart;
import com.glowterx.glowterx.Model.Instructor;
import com.glowterx.glowterx.Model.Product;
import com.glowterx.glowterx.Model.Trainee;
import com.glowterx.glowterx.Model.Training;

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
    TrainingDAO trainingDAO;

    @Autowired
    InstructorDAO instructorDAO;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    CartDAO cartDAO;

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

    // Profile begins here
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
    // Profile ends here

    // Shop begins here
    @GetMapping("/manageShop")
    public String manageShop(Model model, HttpSession session) {
        List<Product> product = productDAO.getAllProduct();
        model.addAttribute("product", product);
        return "Admin/ManageShop";
    }
    // Shop ends here

    // Training Class begins here
    @GetMapping("/manageTraining")
    public String adminManageTraining(Model model) {
        List<Instructor> instructors = adminDAO.getAllInstructors();
        model.addAttribute("instructors", instructors);
        return "Admin/CreateTrainingClass";
    }

    @GetMapping("/manageTrainingClass")
    public String viewListTraining(Model model) {
        List<Training> training = adminDAO.getAllTraining();
        List<Instructor> instructor = new ArrayList<Instructor>();
        for (Training t : training) {
            instructor.add(instructorDAO.getInstructorID(t.getInstructor_id()));
        }
        model.addAttribute("instructor", instructor);
        model.addAttribute("training", training);
        return "Admin/ManageTrainingClass";
    }

    @GetMapping("/listTraineeTC")
    public String traineeEnrollTC(Model model) {
        List<Training> training = adminDAO.getAllTraining();
        List<Instructor> instructor = new ArrayList<Instructor>();
        for (Training t : training) {
            instructor.add(instructorDAO.getInstructorID(t.getInstructor_id()));
        }
        model.addAttribute("instructor", instructor);
        model.addAttribute("training", training);
        return "Trainee/TraineeListTC";
    }

    @GetMapping("/listInstructorTC")
    public String instructorEnrollTC(Model model) {
        List<Training> training = adminDAO.getAllTraining();
        List<Instructor> instructor = new ArrayList<Instructor>();
        for (Training t : training) {
            instructor.add(instructorDAO.getInstructorID(t.getInstructor_id()));
        }
        model.addAttribute("instructor", instructor);
        model.addAttribute("training", training);
        return "Instructor/InstructListTC";
    }
    // Training Class ends here


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



    @GetMapping("/addCart")
    public String addCart(Model model, HttpSession session) {
        /*
         * List<Cart> cart = cartDAO.getAllCart();
         * model.addAttribute("cart", cart);
         */
        return "Trainee/ListCart";
    }

    @GetMapping("/shop")
    public String shop(Model model, HttpSession session) {
        return "Trainee/AddToCart";
    }
}
