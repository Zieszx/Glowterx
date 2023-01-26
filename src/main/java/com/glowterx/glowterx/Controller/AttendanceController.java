package com.glowterx.glowterx.Controller;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;

import com.glowterx.glowterx.Model.Attendance;
import com.glowterx.glowterx.Model.Trainee;
import com.glowterx.glowterx.Model.Training;
import com.glowterx.glowterx.Model.Instructor;
import com.glowterx.glowterx.Model.Admin;
import com.glowterx.glowterx.DOA.AttendanceDAO;
import com.glowterx.glowterx.DOA.TraineeDAO;
import com.glowterx.glowterx.DOA.TrainingDAO;
import com.glowterx.glowterx.DOA.InstructorDAO;
import com.glowterx.glowterx.DOA.AdminDAO;

import jakarta.servlet.http.HttpSession;

@Controller
public class AttendanceController {
    @Autowired
    private AttendanceDAO attendanceDAO;

    @Autowired
    private TraineeDAO traineeDAO;

    @Autowired
    private TrainingDAO trainingDAO;

    @Autowired
    private InstructorDAO instructorDAO;

    @Autowired
    private AdminDAO adminDAO;

    @GetMapping("/enrollTraining")
    public String enrollTraining(@RequestParam("id") int training_id, Model model, HttpSession session) {
        Trainee trainee = traineeDAO.getInfoTrainee();
        Training training = trainingDAO.getInfoTrainingByID(training_id);
        attendanceDAO.enrollTraining(trainee, training);
        model.addAttribute("message", "You have successfully enrolled for this training!");
        
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
