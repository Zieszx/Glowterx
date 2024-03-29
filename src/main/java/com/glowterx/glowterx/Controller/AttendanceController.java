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
        boolean check = false;
        int tempSize = 0;
        Trainee trainee = traineeDAO.getInfoTrainee();
        Training training = trainingDAO.getInfoTrainingByID(training_id);
        List<Attendance> tempAttendance = attendanceDAO.getTraineeAttendance(trainee);
        tempSize = tempAttendance.size();
        if (trainee.getMembershipStatus().equals("NONE")) {
            model.addAttribute("message", "You need to Subscribe to Enroll!");
            List<Training> trainings = adminDAO.getAllTraining();
            List<Instructor> instructor = new ArrayList<Instructor>();
            for (Training t : trainings) {
                instructor.add(instructorDAO.getInstructorID(t.getInstructor_id()));
            }
            model.addAttribute("instructor", instructor);
            model.addAttribute("training", trainings);
            return "/Trainee/TraineeListTC";
        } else if (trainee.getMembershipStatus().equals("Free Trial")) {
            if (tempAttendance.size() >= 1) {
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
            if (tempAttendance.size() >= 2) {
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
        if (attendanceDAO.enrollCheck(trainee, training)) {
            model.addAttribute("message", "You have already enrolled for this training!");
            List<Training> trainings = adminDAO.getAllTraining();
            List<Instructor> instructor = new ArrayList<Instructor>();
            for (Training t : trainings) {
                instructor.add(instructorDAO.getInstructorID(t.getInstructor_id()));
            }
            model.addAttribute("instructor", instructor);
            model.addAttribute("training", trainings);
            return "/Trainee/TraineeListTC";
        }
        attendanceDAO.enrollTraining(trainee, training);
        model.addAttribute("message", "You have successfully enrolled for this training!");
        tempAttendance = attendanceDAO.getTraineeAttendance(trainee);
        if (trainee.getMembershipStatus().equals("Free Trial")) {
            if (tempAttendance.size() == 1) {
                model.addAttribute("message",
                        "You have Enrolled this training, and You need to Subscribe to Enroll More!");
            }
        } else if (trainee.getMembershipStatus().equals("Customize Plan")) {
            if (tempAttendance.size() == 2) {
                model.addAttribute("message",
                        "You have Enrolled this training, and You need to Subscribe to Unlimited Access to Enroll More!");
            } else {
                check = true;
                model.addAttribute("check", check);
            }
        }
        List<Training> trainings = adminDAO.getAllTraining();
        List<Instructor> instructor = new ArrayList<Instructor>();
        for (Training t : trainings) {
            instructor.add(instructorDAO.getInstructorID(t.getInstructor_id()));
        }
        model.addAttribute("instructor", instructor);
        model.addAttribute("training", trainings);

        return "/Trainee/TraineeListTC";
    }

    @GetMapping("/checkinsAttendance")
    public String checkinsAttendance(@RequestParam("id") int attendance_id, Model model, HttpSession session) {
        Trainee trainee = traineeDAO.getInfoTrainee();
        Attendance tempAttendance = attendanceDAO.getAttendancebyID(attendance_id);
        Training tempTraining = trainingDAO.getInfoTrainingByID(tempAttendance.getTraining_id());
        attendanceDAO.checkIn(attendance_id, trainee, tempTraining);
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
}
