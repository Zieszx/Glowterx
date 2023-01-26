package com.glowterx.glowterx.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.glowterx.glowterx.DOA.InstructorDAO;
import com.glowterx.glowterx.Model.Instructor;

import jakarta.servlet.http.HttpSession;

public class InstructorController {
    @Autowired
    HttpSession session;

    @Autowired
    private InstructorDAO instructorDAO;

    @GetMapping("/InstructorProfilePicture")
    public ResponseEntity<byte[]> getProfilePicture() {
        String username = (String) session.getAttribute("username");
        byte[] image = instructorDAO.getProfilePicture(username);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @GetMapping("/instructorProfilePicturebyUsername")
    public ResponseEntity<byte[]> getProfilePicturebyUsername(@RequestParam("username") String username) {
        byte[] image = instructorDAO.getProfilePicture(username);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @PostMapping("/uploadInstructorProfilePicture")
    public String uploadProfilePicture(@RequestParam("file") MultipartFile file, Model model) {
        Instructor Instructor = instructorDAO.getInfoinstructor();
        model.addAttribute("instructor", Instructor);
        String username = (String) session.getAttribute("username");
        if (!file.isEmpty()) {
            try {
                instructorDAO.uploadProfilePicture(username, file.getBytes());
                model.addAttribute("message", "Successfully uploaded image");
                return "Instructor/EditProfileDetails";
            } catch (IOException e) {
                model.addAttribute("message", "Failed to upload image");
                return "Instructor/EditProfileDetails";
            }
        } else {
            model.addAttribute("message", "File is empty");
            return "Instructor/EditProfileDetails";
        }
    }

    @PostMapping("/updateInstructorProfile")
    public String updateProfile(@ModelAttribute("instructor") Instructor Instructor, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        instructorDAO.updateProfile(Instructor);
        Instructor RefInstructor = instructorDAO.getInfoinstructor();
        model.addAttribute("instructor", RefInstructor);
        return "Instructor/ProfileDetails";
    }

    @GetMapping("/InstructorEditProfile")
    public String EditProfileInstructor(Model model, HttpSession session) {
        Instructor instructor = instructorDAO.getInfoinstructor();
        model.addAttribute("instructor", instructor);
        System.out.println(instructor.getInstructorUsername());
        return "Instructor/EditProfileDetails";
    }

}
