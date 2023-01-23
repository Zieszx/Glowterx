package com.glowterx.glowterx.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;

import com.glowterx.glowterx.DOA.TraineeDAO;
import com.glowterx.glowterx.Model.Payment;
import com.glowterx.glowterx.Model.Trainee;
import com.glowterx.glowterx.Model.Membership;
import com.glowterx.glowterx.DOA.MembershipDAO;
import jakarta.servlet.http.HttpSession;

@Controller
public class TraineeController {
    @Autowired
    HttpSession session;

    @Autowired
    private TraineeDAO traineeDAO;
    @Autowired
    private TraineeDAO MembershipDAO;

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
    public String registerMembership(@RequestParam("Plan") String Category,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date d, @RequestParam("paymentMethod") String paymentMethod,
             Model model, HttpSession session) throws SQLException {

                if(session.getAttribute("trainee")!=null){
                    Trainee trainee = (Trainee) session.getAttribute("trainee");
                    Payment payment = new Payment();
                    Membership membership = new Membership ();
                    if (Category == "Free")
                   { payment.setAmount(0.00);}
                   else if ( Category == "Customize")
                   { payment.setAmount(155.00);}
                   else if ( Category == "Unlimited")
                   { payment.setAmount(250.00);}
                  else
                  { payment.setAmount(0.00);}
                    payment.setPayment_category(Category);
                    payment.setPerson_id(trainee.getId());
                    payment.setPayment_status(paymentMethod);
                    payment.setPayment_date(d);
                    membership.setPerson_id(trainee.getId());
                    membership.setstartdate(d);
                    membership.setCategory(Category);
        
                    traineeDAO.createMembership(payment,membership);
                    }

        return "Trainee/Subscribe";
    }

    @GetMapping("/Subscribe")
    public String register() {
        return "Trainee/Subscribepayment";
    }

  

}
