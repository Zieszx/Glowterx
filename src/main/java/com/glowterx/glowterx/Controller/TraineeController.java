package com.glowterx.glowterx.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;

import com.glowterx.glowterx.DOA.TraineeDAO;
import com.glowterx.glowterx.Model.Trainee;

import jakarta.servlet.http.HttpSession;

@Controller
public class TraineeController {
    @Autowired
    HttpSession session;

    @Autowired
    private TraineeDAO traineeDAO;

    @PostMapping("/registerTrainee")
    public String registerTrainee(@RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname, @RequestParam("username") String username,
            @RequestParam("password") String password, @RequestParam("phonenum") String phonenum,
            @RequestParam("email") String email, @RequestParam("gender") String gender,
            @RequestParam("address") String address, @RequestParam("city") String city,
            @RequestParam("state") String state, @RequestParam("zip") String zip,
            @RequestParam("file") MultipartFile file, Model model) {

        Trainee trainee = new Trainee();
        trainee.setFirstName(firstname);
        trainee.setLastName(lastname);
        trainee.setTraineeUsername(username);
        trainee.setTraineePass(password);
        trainee.setPhone(phonenum);
        trainee.setEmail(email);
        trainee.setGender(gender);
        trainee.setAddress(address);
        trainee.setCity(city);
        trainee.setZip(zip);
        trainee.setState(state);

        // save the trainee to the database
        traineeDAO.insertTrainee(trainee);

        try {
            // get the bytes of the image file
            byte[] imageBytes = file.getBytes();

            // set the imageBytes to the trainee object
            traineeDAO.uploadProfilePicture(username, imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "fitnesslogin";
    }
    @PostMapping("/membershipPayment")
    public String registerMembership(@RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname, @RequestParam("username") String username,
            @RequestParam("password") String password, @RequestParam("phonenum") String phonenum,
            @RequestParam("email") String email, @RequestParam("gender") String gender,
            @RequestParam("address") String address, @RequestParam("city") String city,
            @RequestParam("state") String state, @RequestParam("zip") String zip,
            @RequestParam("file") MultipartFile file, Model model) {

        Trainee trainee = new Trainee();
        trainee.setFirstName(firstname);
        trainee.setLastName(lastname);
        trainee.setTraineeUsername(username);
        trainee.setTraineePass(password);
        trainee.setPhone(phonenum);
        trainee.setEmail(email);
        trainee.setGender(gender);
        trainee.setAddress(address);
        trainee.setCity(city);
        trainee.setZip(zip);
        trainee.setState(state);

        // save the trainee to the database
        traineeDAO.insertTrainee(trainee);

        try {
            // get the bytes of the image file
            byte[] imageBytes = file.getBytes();

            // set the imageBytes to the trainee object
            traineeDAO.uploadProfilePicture(username, imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Trainee/Subscribe";
    }

    @GetMapping("/Subscribe")
    public String register() {
        return "Trainee/Subscribepayment";
    }

  

}
