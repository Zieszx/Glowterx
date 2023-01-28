package com.glowterx.glowterx.DOA;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.glowterx.glowterx.Model.Admin;
import com.glowterx.glowterx.Model.Instructor;
import com.glowterx.glowterx.Model.Membership;
import com.glowterx.glowterx.Model.Trainee;
import com.glowterx.glowterx.Model.Training;

import jakarta.servlet.http.HttpSession;

public class AdminDAO {
    @Autowired
    private DataSource dataSource;

    @Autowired
    HttpSession session;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Admin validate(String username, String password) {
        Admin admin = null;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM admin WHERE adminUsername = ? AND adminPass = ?")) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setAdminUsername(rs.getString("adminUsername"));
                admin.setAdminPass(rs.getString("adminPass"));
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

    public Admin getInfoAdmin() {
        String username = (String) session.getAttribute("username");
        Admin admin = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM Admin WHERE adminUsername = ?")) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setAdminUsername(rs.getString("adminUsername"));
                admin.setAdminPass(rs.getString("adminPass"));
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

    public Admin getInfoAdminbyUsername(String username) {
        Admin admin = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM Admin WHERE adminUsername = ?")) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setAdminUsername(rs.getString("adminUsername"));
                admin.setAdminPass(rs.getString("adminPass"));
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

    public void uploadProfilePicture(String username, byte[] image) {
        String sql = "UPDATE admin SET profle_images = ? WHERE adminUsername = ?";
        jdbcTemplate.update(sql, new Object[] { image, username });
    }

    public byte[] getProfilePicture(String username) {
        String sql = "SELECT profle_images FROM admin WHERE adminUsername = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { username }, byte[].class);
    }

    public List<Admin> getAllAdmin() {
        String username = (String) session.getAttribute("username");
        String sql = "SELECT * FROM admin WHERE adminUsername != ?";
        return jdbcTemplate.query(sql, new Object[] { username }, new BeanPropertyRowMapper<>(Admin.class));
    }

    public List<Training> getAllTraining() {
        String sql = "SELECT * FROM training";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Training.class));
    }

    public List<Instructor> getAllInstructors() {
        String sql = "SELECT * FROM instructor";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Instructor.class));
    }

    public List<Trainee> getAllTrainee() {
        String sql = "SELECT * FROM trainee";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Trainee.class));
    }

    public void updateProfile(Admin admin) {
        String sql = "UPDATE admin SET firstName = ?, lastName = ?, gender = ?, adminUsername = ?, adminPass = ?, phone = ?, address = ?, email = ?, state = ?, city = ? WHERE adminUsername = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getGender());
            statement.setString(4, admin.getAdminUsername());
            statement.setString(5, admin.getAdminPass());
            statement.setString(6, admin.getPhone());
            statement.setString(7, admin.getAddress());
            statement.setString(8, admin.getEmail());
            statement.setString(9, admin.getState());
            statement.setString(10, admin.getCity());
            statement.setString(11, admin.getAdminUsername());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // adminDAO.addManageUser(username, password, role, firstname, lastname, email,
    // phone)
    public void addManageUser(String username, String password, String role, String firstname,
            String lastname, String gender, String email, String phone) {
        String sql = "";
        String membership = "Free Trial";
        if (role.equals("admin")) {
            sql = "INSERT INTO admin (adminUsername, adminPass, firstname, lastname, gender, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, username, password, firstname, lastname, gender, email, phone);
        } else if (role.equals("instructor")) {
            sql = "INSERT INTO instructor (InstructorUsername, InstructorPass, firstname, lastname, gender, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, username, password, firstname, lastname, gender, email, phone);
        } else if (role.equals("trainee")) {
            sql = "INSERT INTO trainee (TraineeUsername, TraineePass, MembershipStatus, firstname, lastname, gender, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, username, password, membership, firstname, lastname, gender, email, phone);
        }
    }

    public void deleteAdmin(String username) {
        String sql = "DELETE FROM admin WHERE adminUsername = ?";
        jdbcTemplate.update(sql, username);
    }

    public void deleteInstructor(String username, int id) {
        // String sql = "DELETE FROM training WHERE person_id = ?";
        // jdbcTemplate.update(sql, id);
        String sql = "DELETE FROM instructor WHERE InstructorUsername = ?";
        jdbcTemplate.update(sql, username);
    }

    // if have association with other table, need to delete the association first
    public void deleteTrainee(String username, int id) {
        // String sql = "SELECT * FROM membership WHERE person_id = ?";
        // List<Membership> membership = jdbcTemplate.query(sql, new Object[] { username
        // },
        // new BeanPropertyRowMapper<>(Membership.class));
        // if (!membership.isEmpty()) {
        // sql = "DELETE FROM membership WHERE person_id = ?";
        // jdbcTemplate.update(sql, id);
        // }

        // sql = "DELETE FROM payment WHERE person_id = ?";
        // jdbcTemplate.update(sql, id);
        // sql = "DELETE FROM cart WHERE person_id = ?";
        // jdbcTemplate.update(sql, id);
        // sql = "DELETE FROM order WHERE person_id = ?";
        // jdbcTemplate.update(sql, id);
        // sql = "DELETE FROM attendance WHERE person_id = ?";
        // jdbcTemplate.update(sql, id);
        String sql = "DELETE FROM trainee WHERE TraineeUsername = ?";
        jdbcTemplate.update(sql, username);
    }

    public void deleteTraining(int id) {
        // String sql = "DELETE FROM training WHERE person_id = ?";
        // jdbcTemplate.update(sql, id);
        String sql = "DELETE FROM training WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
