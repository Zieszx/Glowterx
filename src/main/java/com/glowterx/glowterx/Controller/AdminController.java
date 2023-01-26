package com.glowterx.glowterx.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

import com.glowterx.glowterx.DOA.AdminDAO;
import com.glowterx.glowterx.DOA.InstructorDAO;
import com.glowterx.glowterx.DOA.TraineeDAO;
import com.glowterx.glowterx.DOA.TrainingDAO;
import com.glowterx.glowterx.Model.Admin;
import com.glowterx.glowterx.Model.Instructor;
import com.glowterx.glowterx.Model.Trainee;
import com.glowterx.glowterx.Model.Training;

@Controller
public class AdminController {
    @Autowired
    HttpSession session;

    @Autowired
    private AdminDAO adminDAO;
    @Autowired
    private TrainingDAO trainingDAO;
    @Autowired
    private InstructorDAO instructorDAO;
    @Autowired
    private TraineeDAO traineeDAO;

    @GetMapping("/adminProfilePicture")
    public ResponseEntity<byte[]> getProfilePicture() {
        String username = (String) session.getAttribute("username");
        byte[] image = adminDAO.getProfilePicture(username);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @GetMapping("/adminProfilePicturebyUsername")
    public ResponseEntity<byte[]> getProfilePicturebyUsername(@RequestParam("username") String username) {
        byte[] image = adminDAO.getProfilePicture(username);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @PostMapping("/uploadAdminProfilePicture")
    public String uploadProfilePicture(@RequestParam("file") MultipartFile file, Model model) {
        Admin admin = adminDAO.getInfoAdmin();
        model.addAttribute("admin", admin);
        String username = (String) session.getAttribute("username");
        if (!file.isEmpty()) {
            try {
                adminDAO.uploadProfilePicture(username, file.getBytes());
                model.addAttribute("message", "Successfully uploaded image");
                return "Admin/EditProfileDetails";
            } catch (IOException e) {
                model.addAttribute("message", "Failed to upload image");
                return "Admin/EditProfileDetails";
            }
        } else {
            model.addAttribute("message", "File is empty");
            return "Admin/EditProfileDetails";
        }
    }

    @PostMapping("/updateAdminProfile")
    public String updateProfile(@ModelAttribute("admin") Admin admin, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        adminDAO.updateProfile(admin);
        Admin RefAdmin = adminDAO.getInfoAdmin();
        model.addAttribute("admin", RefAdmin);
        return "Admin/ProfileDetails";
    }

    @GetMapping("/AdmineditProfile")
    public String EditProfileAdmin(Model model, HttpSession session) {
        Admin admin = adminDAO.getInfoAdmin();
        model.addAttribute("admin", admin);
        return "Admin/EditProfileDetails";
    }

    @PostMapping("/addManageUser")
    public String AddManageUserAdmin(@RequestParam("username") String username,
            @RequestParam("password") String password, @RequestParam("role") String role,
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname, @RequestParam("gender") String gender,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone, Model model) {
        adminDAO.addManageUser(username, password, role, firstname, lastname, gender, email, phone);
        List<Trainee> trainee = adminDAO.getAllTrainee();
        List<Instructor> instructor = adminDAO.getAllInstructors();
        List<Admin> admin = adminDAO.getAllAdmin();
        model.addAttribute("admindata", admin);
        model.addAttribute("instructordata", instructor);
        model.addAttribute("traineedata", trainee);
        return "Admin/ManageUser";
    }

    @GetMapping("/deleteadmin")
    public String deleteAdmin(@RequestParam("username") String username, Model model) {
        adminDAO.deleteAdmin(username);
        List<Trainee> trainee = adminDAO.getAllTrainee();
        List<Instructor> instructor = adminDAO.getAllInstructors();
        List<Admin> admin = adminDAO.getAllAdmin();
        model.addAttribute("admindata", admin);
        model.addAttribute("instructordata", instructor);
        model.addAttribute("traineedata", trainee);
        return "Admin/ManageUser";
    }

    @GetMapping("/deleteinstructor")
    public String deleteInstructor(@RequestParam("username") String username, @RequestParam("id") int id, Model model) {
        adminDAO.deleteInstructor(username, id);
        List<Trainee> trainee = adminDAO.getAllTrainee();
        List<Instructor> instructor = adminDAO.getAllInstructors();
        List<Admin> admin = adminDAO.getAllAdmin();
        model.addAttribute("admindata", admin);
        model.addAttribute("instructordata", instructor);
        model.addAttribute("traineedata", trainee);
        return "Admin/ManageUser";
    }

    @GetMapping("/deletetrainee")
    public String deleteTrainee(@RequestParam("username") String username, @RequestParam("id") int id, Model model) {
        adminDAO.deleteTrainee(username, id);
        List<Trainee> trainee = adminDAO.getAllTrainee();
        List<Instructor> instructor = adminDAO.getAllInstructors();
        List<Admin> admin = adminDAO.getAllAdmin();
        model.addAttribute("admindata", admin);
        model.addAttribute("instructordata", instructor);
        model.addAttribute("traineedata", trainee);
        return "Admin/ManageUser";
    }

    @GetMapping("/deletetraining")
    public String deleteTraining(@RequestParam("id") int id, Model model) {
        adminDAO.deleteTraining(id);
        List<Training> training = adminDAO.getAllTraining();
        model.addAttribute("training", training);
        return "Admin/ManageTrainingClass";
    }

    @GetMapping("/editTraining")
    public String EditTraining(@RequestParam("id") int id, Model model, HttpSession session) {
        session.setAttribute("training_id", id);
        Training training = trainingDAO.getInfoTraining();
        Instructor instructor = instructorDAO.getInstructorID(training.getInstructor_id());
        List<Instructor> instructors = adminDAO.getAllInstructors();
        model.addAttribute("training", training);
        model.addAttribute("instructors", instructors);
        model.addAttribute("currInstructor", instructor);

        return "Admin/EditTrainingClass";
    }

    @PostMapping("/updateTraining")
    public String updateTraining(@ModelAttribute("training") Training training, @RequestParam("instructor_id") int id,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        trainingDAO.updateProfile(training, id);
        List<Training> t = adminDAO.getAllTraining();
        List<Instructor> instructor = new ArrayList<Instructor>();
        for (Training train : t) {
            instructor.add(instructorDAO.getInstructorID(train.getInstructor_id()));
        }
        model.addAttribute("instructor", instructor);
        model.addAttribute("training", t);
        return "Admin/ManageTrainingClass";
    }

    @GetMapping("/viewAdminDetails")
    public String viewAdmin(@RequestParam("username") String username, Model model) {
        Admin admin = adminDAO.getInfoAdminbyUsername(username);
        model.addAttribute("admin", admin);
        return "Admin/viewDetailsAdmin";
    }

    @GetMapping("/viewInstructorDetails")
    public String viewInstructor(@RequestParam("username") String username, Model model) {
        Instructor instructor = instructorDAO.getInfoinstructorbyUsername(username);
        model.addAttribute("instructor", instructor);
        return "Admin/viewDetailsInstructor";
    }

    @GetMapping("/viewTraineeDetails")
    public String viewTrainee(@RequestParam("username") String username, Model model) {
        Trainee trainee = traineeDAO.getInfoTraineebyUsername(username);
        model.addAttribute("trainee", trainee);
        return "Admin/viewDetailsTrainee";
    }
}
