package com.glowterx.glowterx.DOA;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.glowterx.glowterx.Model.Admin;

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

    public void saveImage(String username, MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            String sql = "UPDATE admin SET image = ? WHERE username = ?";
            jdbcTemplate.update(sql, bytes, username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
