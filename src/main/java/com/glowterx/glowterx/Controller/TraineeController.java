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

import com.glowterx.glowterx.DOA.TraineeDAO;
import com.glowterx.glowterx.Model.Payment;
import com.glowterx.glowterx.Model.Trainee;
import com.glowterx.glowterx.Model.Membership;
import com.glowterx.glowterx.DOA.MembershipDAO;
import com.glowterx.glowterx.DOA.PaymentDAO;

import jakarta.servlet.http.HttpSession;

@Controller
public class TraineeController {
    @Autowired
    HttpSession session;

    @Autowired
    private TraineeDAO traineeDAO;

    @Autowired
    private MembershipDAO membershipDAO;

    @Autowired
    private PaymentDAO paymentDAO;

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

    @GetMapping("/TraineeProfilePicture")
    public ResponseEntity<byte[]> getProfilePicture() {
        String username = (String) session.getAttribute("username");
        byte[] image = traineeDAO.getProfilePicture(username);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @GetMapping("/TraineeProfilePicturebyUsername")
    public ResponseEntity<byte[]> getProfilePicturebyUsername(@RequestParam("username") String username) {
        byte[] image = traineeDAO.getProfilePicture(username);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @PostMapping("/uploadTraineeProfilePicture")
    public String uploadProfilePicture(@RequestParam("file") MultipartFile file, Model model) {
        Trainee trainee = traineeDAO.getInfoTrainee();
        model.addAttribute("trainee", trainee);
        String username = (String) session.getAttribute("username");
        if (!file.isEmpty()) {
            try {
                traineeDAO.uploadProfilePicture(username, file.getBytes());
                model.addAttribute("message", "Successfully uploaded image");
                return "Trainee/EditProfileDetails";
            } catch (IOException e) {
                model.addAttribute("message", "Failed to upload image");
                return "Trainee/EditProfileDetails";
            }
        } else {
            model.addAttribute("message", "File is empty");
            return "Trainee/EditProfileDetails";
        }
    }

    @PostMapping("/updateTraineeProfile")
    public String updateProfile(@ModelAttribute("trainee") Trainee Trainee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        traineeDAO.updateProfile(Trainee);
        Trainee RefTrainee = traineeDAO.getInfoTrainee();
        model.addAttribute("trainee", RefTrainee);
        return "Trainee/ProfileDetails";
    }

    @GetMapping("/TraineeEditProfile")
    public String EditProfileTrainee(Model model, HttpSession session) {
        Trainee trainee = traineeDAO.getInfoTrainee();
        model.addAttribute("trainee", trainee);
        return "Trainee/EditProfileDetails";
    }

    @PostMapping("/membershipPayment")
    public String registerMembership(@RequestParam("Plan") String Category,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date d,
            @RequestParam("paymentMethod") String paymentMethod,
            Model model, HttpSession session) throws SQLException {
        Trainee trainee = traineeDAO.getInfoTrainee();
        // System.out.println(d);
        if (trainee != null) {
            Payment payment = new Payment();
            Membership membership = new Membership();
            double amount = 0;
            if (Category.equals("Free Trial")) {
                amount = 0;
            } else if (Category.equals("Customize Plan")) {
                amount = 155.00;
            } else if (Category.equals("Unlimited Access")) {
                amount = 250.00;
            }

            payment.setAmount(amount);
            payment.setPayment_category(paymentMethod);
            payment.setPerson_id(trainee.getId());
            payment.setPayment_status("PAID");
            payment.setPayment_date(d);
            int payment_id = paymentDAO.createPayment(payment);
            membership.setPayment_id(payment_id);
            membership.setPerson_id(trainee.getId());
            membership.setstart_date(d);
            membership.setmembership_category(Category);
            trainee.setMembershipStatus(Category);
            membershipDAO.createMembership(membership, trainee);
            model.addAttribute("trainee", trainee);
            List<Membership> members = membershipDAO.getAllMembershipbyID(trainee.getId());
            List<Payment> payments = new ArrayList<Payment>();
            for (Membership m : members) {
                payments.add(paymentDAO.getPaymentbyID(m.getPayment_id()));
            }
            model.addAttribute("members", members);
            model.addAttribute("payments", payments);
            return "Trainee/Subscribe";

        }

        return "Trainee/Subscribe";
    }

    @GetMapping("/SubscribeP")
    public String register() {
        return "Trainee/Subscribepayment";
    }

    @GetMapping("/Subscribe")
    public String subscribe(Model model, HttpSession session) {
        Trainee trainee = traineeDAO.getInfoTrainee();
        model.addAttribute("trainee", trainee);
        List<Membership> members = membershipDAO.getAllMembershipbyID(trainee.getId());
        List<Payment> payments = new ArrayList<Payment>();

        for (Membership m : members) {
            payments.add(paymentDAO.getPaymentbyID(m.getPayment_id()));
        }
        model.addAttribute("members", members);
        model.addAttribute("payments", payments);

        return "Trainee/Subscribe";
    }

}
