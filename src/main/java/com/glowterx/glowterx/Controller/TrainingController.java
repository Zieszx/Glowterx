package com.glowterx.glowterx.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glowterx.glowterx.DOA.TrainingDAO;
import com.glowterx.glowterx.Model.Training;

import jakarta.servlet.http.HttpSession;

@Controller
public class TrainingController {
    @Autowired
    private TrainingDAO trainingDAO;

    @GetMapping("/addTraining")
    public String addTraining() {
        
        return "add training classes";
    }
}
