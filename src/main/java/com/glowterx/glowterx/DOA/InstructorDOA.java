package com.glowterx.glowterx.DOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.glowterx.glowterx.Model.Instructor;

import jakarta.servlet.http.HttpSession;

public class InstructorDOA {
    @Autowired
    private DataSource dataSource;
    @Autowired
    HttpSession session;

    public Instructor validate(String username, String password) {
        Instructor instructor = null;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM instructor WHERE username = ? AND password = ?")) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                instructor = new Instructor();
                instructor.setId(rs.getInt("id"));
                instructor.setInstructorUsername(rs.getString("username"));
                instructor.setInstructorPass(rs.getString("password"));
                instructor.setFirstName(rs.getString("firstname"));
                instructor.setLastName(rs.getString("lastname"));
                instructor.setAddress(rs.getString("address"));
                instructor.setCity(rs.getString("city"));
                instructor.setState(rs.getString("state"));
                instructor.setZip(rs.getString("zip"));
                instructor.setPhone(rs.getString("phone"));
                instructor.setEmail(rs.getString("email"));
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
                        .prepareStatement("SELECT * FROM instructor WHERE username = ?")) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                instructor = new Instructor();
                instructor.setId(rs.getInt("id"));
                instructor.setInstructorUsername(rs.getString("username"));
                instructor.setInstructorPass(rs.getString("password"));
                instructor.setFirstName(rs.getString("firstname"));
                instructor.setLastName(rs.getString("lastname"));
                instructor.setAddress(rs.getString("address"));
                instructor.setCity(rs.getString("city"));
                instructor.setState(rs.getString("state"));
                instructor.setZip(rs.getString("zip"));
                instructor.setPhone(rs.getString("phone"));
                instructor.setEmail(rs.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instructor;
    }
}
