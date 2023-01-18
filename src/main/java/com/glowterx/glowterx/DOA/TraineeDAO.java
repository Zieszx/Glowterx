package com.glowterx.glowterx.DOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.glowterx.glowterx.Model.Trainee;

import jakarta.servlet.http.HttpSession;

public class TraineeDAO {
    @Autowired
    private DataSource dataSource;
    @Autowired
    HttpSession session;

    public Trainee validate(String username, String password) {
        Trainee trainee = null;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM trainee WHERE username = ? AND password = ?")) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                trainee = new Trainee();
                trainee.setId(rs.getInt("id"));
                trainee.setTraineeUsername(rs.getString("username"));
                trainee.setTraineePass(rs.getString("password"));
                trainee.setFirstName(rs.getString("firstname"));
                trainee.setLastName(rs.getString("lastname"));
                trainee.setAddress(rs.getString("address"));
                trainee.setCity(rs.getString("city"));
                trainee.setState(rs.getString("state"));
                trainee.setZip(rs.getString("zip"));
                trainee.setPhone(rs.getString("phone"));
                trainee.setEmail(rs.getString("email"));
                trainee.setGender(rs.getString("gender"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trainee;
    }

    public Trainee getInfoTrainee() {
        String username = (String) session.getAttribute("username");
        Trainee trainee = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM trainee WHERE username = ?")) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                trainee = new Trainee();
                trainee.setId(rs.getInt("id"));
                trainee.setTraineeUsername(rs.getString("username"));
                trainee.setTraineePass(rs.getString("password"));
                trainee.setFirstName(rs.getString("firstname"));
                trainee.setLastName(rs.getString("lastname"));
                trainee.setAddress(rs.getString("address"));
                trainee.setCity(rs.getString("city"));
                trainee.setState(rs.getString("state"));
                trainee.setZip(rs.getString("zip"));
                trainee.setPhone(rs.getString("phone"));
                trainee.setEmail(rs.getString("email"));
                trainee.setGender(rs.getString("gender"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainee;
    }
}
