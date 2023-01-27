package com.glowterx.glowterx.Controller;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glowterx.glowterx.DOA.AdminDAO;
import com.glowterx.glowterx.DOA.AttendanceDAO;
import com.glowterx.glowterx.DOA.CartDAO;
import com.glowterx.glowterx.DOA.InstructorDAO;
import com.glowterx.glowterx.DOA.ProductDAO;
import com.glowterx.glowterx.DOA.TraineeDAO;
import com.glowterx.glowterx.DOA.TrainingDAO;
import com.glowterx.glowterx.Model.Admin;
import com.glowterx.glowterx.Model.Attendance;
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

    @Autowired
    AttendanceDAO attendanceDAO;

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

    @GetMapping("/reportTrainee")
    public String adminGenerateReportTrainee(Model model) {
        List<Trainee> trainee = adminDAO.getAllTrainee();
        List<Instructor> instructor = adminDAO.getAllInstructors();
        List<Admin> admin = adminDAO.getAllAdmin();
        model.addAttribute("admindata", admin);
        model.addAttribute("instructordata", instructor);
        model.addAttribute("traineedata", trainee);
        return "Admin/ReportTrainee";
    }

    @GetMapping("/reportInstructor")
    public String adminGenerateReportInstructor(Model model) {
        List<Trainee> trainee = adminDAO.getAllTrainee();
        List<Instructor> instructor = adminDAO.getAllInstructors();
        List<Admin> admin = adminDAO.getAllAdmin();
        model.addAttribute("admindata", admin);
        model.addAttribute("instructordata", instructor);
        model.addAttribute("traineedata", trainee);
        return "Admin/ReportInstructor";
    }

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
        boolean check = true;
        Trainee trainee = traineeDAO.getInfoTrainee();
        List<Attendance> tempAttendance = attendanceDAO.getTraineeAttendance(trainee);
        if (trainee.getMembershipStatus().equals("Free Trial")) {
            if (tempAttendance.size() == 1) {
                model.addAttribute("message", "You need to Subscribe to Enroll More!");
                List<Training> trainings = adminDAO.getAllTraining();
                List<Instructor> instructor = new ArrayList<Instructor>();
                for (Training t : trainings) {
                    instructor.add(instructorDAO.getInstructorID(t.getInstructor_id()));
                }
                model.addAttribute("instructor", instructor);
                model.addAttribute("training", trainings);
                return "/Trainee/TraineeListTC";
            }
        } else if (trainee.getMembershipStatus().equals("Customize Plan")) {
            if (tempAttendance.size() == 2) {
                model.addAttribute("message", "You need to Subscribe to Unlimited Access to Enroll More!");
                List<Training> trainings = adminDAO.getAllTraining();
                List<Instructor> instructor = new ArrayList<Instructor>();
                for (Training t : trainings) {
                    instructor.add(instructorDAO.getInstructorID(t.getInstructor_id()));
                }
                model.addAttribute("instructor", instructor);
                model.addAttribute("training", trainings);
                return "/Trainee/TraineeListTC";
            }
        }
        if (trainee.getMembershipStatus().equals("Unlimited Access")) {
            List<Training> training = adminDAO.getAllTraining();
            for (Training t : training) {
                if (attendanceDAO.enrollCheck(trainee, t)) {
                    model.addAttribute("message", "You are already enrolled in all training classes.");
                    check = false;
                    model.addAttribute("check", check);
                    break;
                } else {
                    attendanceDAO.enrollTraining(trainee, t);
                }
            }
            model.addAttribute("message", "You have been enrolled in all training classes!");
            check = false;
            model.addAttribute("check", check);
        }
        model.addAttribute("check", check);
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
        Instructor instructor2 = instructorDAO.getInfoinstructor();
        model.addAttribute("instructor", instructor2);
        List<Training> training = trainingDAO.getInstructorTraining(instructor2);
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
        List<Product> product = productDAO.getAllProduct();
        model.addAttribute("product", product);
        return "Trainee/AddToCart";
    }

    @GetMapping("/AttendanceTC")
    public String attendanceTC(Model model, HttpSession session) {
        Trainee trainee = traineeDAO.getInfoTrainee();
        List<Attendance> attendance = attendanceDAO.getTraineeAttendance(trainee);
        List<Training> training = new ArrayList<Training>();
        List<Instructor> instructor = new ArrayList<Instructor>();
        for (Attendance a : attendance) {
            training.add(trainingDAO.getInfoTrainingByID(a.getTraining_id()));
        }
        for (Training t : training) {
            instructor.add(instructorDAO.getInstructorID(t.getInstructor_id()));
        }
        model.addAttribute("attendance", attendance);
        model.addAttribute("instructor", instructor);
        model.addAttribute("training", training);
        return "Trainee/AttendanceUser";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        return "Trainee/CustomerCheckout";
    }

    @GetMapping("/InstructorEditProfile")
    public String EditProfileInstructor(Model model, HttpSession session) {
        Instructor instructor = instructorDAO.getInfoinstructor();
        model.addAttribute("instructor", instructor);
        System.out.println(instructor.getInstructorUsername());
        return "Instructor/EditProfileDetails";
    }

}
