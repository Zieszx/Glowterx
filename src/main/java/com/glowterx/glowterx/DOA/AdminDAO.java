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
                        .prepareStatement("SELECT * FROM admin WHERE username = ? AND password = ?")) {

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

    public Admin getInfoAdmin() {
        String username = (String) session.getAttribute("username");
        Admin admin = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM Admin WHERE username = ?")) {

            statement.setString(1, username);

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

    public void uploadProfilePicture(String username, byte[] image) {
        String sql = "UPDATE admin SET profle_images = ? WHERE username = ?";
        jdbcTemplate.update(sql, new Object[] { image, username });
    }

    public byte[] getProfilePicture(String username) {
        String sql = "SELECT profle_images FROM admin WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { username }, byte[].class);
    }

    public List<Instructor> getAllInstructors() {
        String sql = "SELECT * FROM instructor";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Instructor.class));
    }

    public void updateProfile(Admin admin) {
        String sql = "UPDATE admin SET firstName = ?, lastName = ?, gender = ?, username = ?, password = ?, phone = ?, address = ?, email = ?, state = ?, city = ? WHERE username = ?";
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

}
