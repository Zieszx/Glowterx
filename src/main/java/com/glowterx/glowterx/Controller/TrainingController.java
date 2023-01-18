package com.glowterx.glowterx.Controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glowterx.glowterx.DOA.TrainingDAO;
import com.glowterx.glowterx.Model.Training;

@Controller
public class TrainingController {
    @Autowired
    private TrainingDAO trainingDAO;

    @PostMapping("/Admin/addTraining")
    public String addTraining(@RequestParam("className") String className,
            @RequestParam("startdate") Date startDate,
            @RequestParam("enddate") Date endDate,
            @RequestParam("duration") int duration,
            @RequestParam("session") int session,
            @RequestParam("instructor_id") int instructorId, Model model) {
        Training training = new Training(className, startDate, endDate, duration, session, instructorId);
        trainingDAO.addTraining(training);
        model.addAttribute("message", "Training class added successfully!");
        return "/Admin/CreateTrainingClass";
    }
}
