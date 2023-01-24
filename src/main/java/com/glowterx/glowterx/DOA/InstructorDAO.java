package com.glowterx.glowterx.DOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.glowterx.glowterx.Model.Instructor;

import jakarta.servlet.http.HttpSession;

public class InstructorDAO {
    @Autowired
    private DataSource dataSource;
    @Autowired
    HttpSession session;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Instructor validate(String username, String password) {
        Instructor instructor = null;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(
                                "SELECT * FROM instructor WHERE InstructorUsername = ? AND InstructorPass = ?")) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                instructor = new Instructor();
                instructor.setId(rs.getInt("id"));
                instructor.setInstructorUsername(rs.getString("InstructorUsername"));
                instructor.setInstructorPass(rs.getString("InstructorPass"));
                instructor.setFirstName(rs.getString("firstname"));
                instructor.setLastName(rs.getString("lastname"));
                instructor.setAddress(rs.getString("address"));
                instructor.setCity(rs.getString("city"));
                instructor.setState(rs.getString("state"));
                instructor.setZip(rs.getString("zip"));
                instructor.setPhone(rs.getString("phone"));
                instructor.setEmail(rs.getString("email"));
                instructor.setGender(rs.getString("gender"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instructor;
    }

    public Instructor getInfoinstructor() {
        String username = (String) session.getAttribute("username");
        Instructor instructor = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM instructor WHERE InstructorUsername = ?")) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                instructor = new Instructor();
                instructor.setId(rs.getInt("id"));
                instructor.setInstructorUsername(rs.getString("InstructorUsername"));
                instructor.setInstructorPass(rs.getString("InstructorPass"));
                instructor.setFirstName(rs.getString("firstname"));
                instructor.setLastName(rs.getString("lastname"));
                instructor.setAddress(rs.getString("address"));
                instructor.setCity(rs.getString("city"));
                instructor.setState(rs.getString("state"));
                instructor.setZip(rs.getString("zip"));
                instructor.setPhone(rs.getString("phone"));
                instructor.setEmail(rs.getString("email"));
                instructor.setGender(rs.getString("gender"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instructor;
    }

    public void uploadProfilePicture(String username, byte[] image) {
        String sql = "UPDATE instructor SET profle_images = ? WHERE InstructorUsername = ?";
        jdbcTemplate.update(sql, new Object[] { image, username });
    }

    public byte[] getProfilePicture(String username) {
        String sql = "SELECT profle_images FROM instructor WHERE InstructorUsername = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { username }, byte[].class);
    }

    public void insertInstructor(Instructor instructor) {
        String sql = "INSERT INTO instructor (firstname, lastname, InstructorUsername, InstructorPass, phone, email, gender, address, city, zip, state) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, instructor.getFirstName(), instructor.getLastName(),
                instructor.getInstructorUsername(),
                instructor.getInstructorPass(), instructor.getPhone(), instructor.getEmail(),
                instructor.getGender(),
                instructor.getAddress(), instructor.getCity(), instructor.getZip(), instructor.getState());
    }

    public void updateProfile(Instructor instructor) {
        String sql = "UPDATE instructor SET firstName = ?, lastName = ?, gender = ?, InstructorUsername = ?, InstructorPass = ?, phone = ?, address = ?, email = ?, state = ?, city = ? WHERE InstructorUsername = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, instructor.getFirstName());
            statement.setString(2, instructor.getLastName());
            statement.setString(3, instructor.getGender());
            statement.setString(4, instructor.getInstructorUsername());
            statement.setString(5, instructor.getInstructorPass());
            statement.setString(6, instructor.getPhone());
            statement.setString(7, instructor.getAddress());
            statement.setString(8, instructor.getEmail());
            statement.setString(9, instructor.getState());
            statement.setString(10, instructor.getCity());
            statement.setString(11, instructor.getInstructorUsername());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
