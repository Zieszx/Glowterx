package com.glowterx.glowterx.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    
    public Membership validate(String username, String password) {
        Membership membership= null;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM membership WHERE username = ? AND password = ?")) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setAdminUsername(rs.getString("username"));
                admin.setAdminPass(rs.getString("password"));
                admin.setFirstName(rs.getString("firstname"));
                admin.setLastName(rs.getString("lastname"));
                admin.setAddress(rs.getString("address"));
                admin.setCity(rs.getString("city"));
                admin.setState(rs.getString("state"));
                admin.setZip(rs.getString("zip"));
                admin.setPhone(rs.getString("phone"));
                admin.setEmail(rs.getString("email"));
                admin.setGender(rs.getString("gender"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admin;
    }

}
