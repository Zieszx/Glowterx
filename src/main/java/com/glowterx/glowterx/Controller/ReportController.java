package com.glowterx.glowterx.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glowterx.glowterx.DOA.AdminDAO;
import com.glowterx.glowterx.DOA.InstructorDAO;
import com.glowterx.glowterx.DOA.TraineeDAO;
import com.glowterx.glowterx.DOA.TrainingDAO;
import com.glowterx.glowterx.DOA.MembershipDAO;
import com.glowterx.glowterx.DOA.ReportDAO;
import com.glowterx.glowterx.DOA.AttendanceDAO;
import com.glowterx.glowterx.Model.Admin;
import com.glowterx.glowterx.Model.Instructor;
import com.glowterx.glowterx.Model.Trainee;
import com.glowterx.glowterx.Model.Training;
import com.glowterx.glowterx.Model.Membership;
import com.glowterx.glowterx.Model.Attendance;

@Controller
public class ReportController {
    @Autowired
    private AttendanceDAO attendanceDAO;

    @Autowired
    private TraineeDAO traineeDAO;

    @Autowired
    private TrainingDAO trainingDAO;

    @Autowired
    private InstructorDAO instructorDAO;
    

    @GetMapping("/gReportInstructor")
    public String adminGeneratedReportInstructor(@RequestParam("username") String username, Model model) {
        Instructor instructor = instructorDAO.getInfoinstructorbyUsername(username);
        model.addAttribute("instructor", instructor);
        return "Admin/ReportInID";
    }

    @GetMapping("/gReportTrainee")
    public String adminGeneratedReportTrainee(@RequestParam("username") String username, @RequestParam("id") int attendance_id, Model model) {
        Trainee trainee = traineeDAO.getInfoTraineebyUsername(username);
        model.addAttribute("trainee", trainee);
        Trainee trainees = traineeDAO.getInfoTrainee();
        Attendance tempAttendance = attendanceDAO.getAttendancebyID(attendance_id);
        Training tempTraining = trainingDAO.getInfoTrainingByID(tempAttendance.getTraining_id());
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
        return "Admin/ReportTrID";
    }
}
