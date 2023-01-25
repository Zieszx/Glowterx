package com.glowterx.glowterx.DOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.glowterx.glowterx.Model.Training;

import jakarta.servlet.http.HttpSession;

public class TrainingDAO {
    @Autowired
    private DataSource dataSource;
    @Autowired
    HttpSession session;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addTraining(Training training) {
        String sql = "INSERT INTO training (training_name, start_date, end_date, training_duration, training_session, instructor_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, training.getTraining_name(), training.getStart_date(), training.getEnd_date(),
                training.getTraining_duration(), training.getTraining_session(), training.getInstructor_id());
    }

    public Training getInfoTraining() {
        int id = (Integer) session.getAttribute("training_id");
        Training training = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM training WHERE id = ?")) {

            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                training = new Training();
                training.setId(rs.getInt("id"));
                training.setTraining_name(rs.getString("training_name"));
                training.setStart_date(rs.getDate("start_date"));
                training.setEnd_date(rs.getDate("end_date"));
                training.setInstructor_id(rs.getInt("instructor_id"));
                training.setTraining_session(rs.getInt("training_session"));
                training.setTraining_duration(rs.getInt("training_duration"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return training;
    }

    public void updateProfile(Training training) {
        /*
         * String sql =
         * "UPDATE training SET firstName = ?, lastName = ?, gender = ?, adminUsername = ?, adminPass = ?, phone = ?, address = ?, email = ?, state = ?, city = ? WHERE adminUsername = ?"
         * ;
         * try (Connection connection = dataSource.getConnection();
         * PreparedStatement statement = connection.prepareStatement(sql)) {
         * statement.setString(1, admin.getFirstName());
         * statement.setString(2, admin.getLastName());
         * statement.setString(3, admin.getGender());
         * statement.setString(4, admin.getAdminUsername());
         * statement.setString(5, admin.getAdminPass());
         * statement.setString(6, admin.getPhone());
         * statement.setString(7, admin.getAddress());
         * statement.setString(8, admin.getEmail());
         * statement.setString(9, admin.getState());
         * statement.setString(10, admin.getCity());
         * statement.setString(11, admin.getAdminUsername());
         * 
         * statement.executeUpdate();
         * } catch (SQLException e) {
         * e.printStackTrace();
         * }
         */

    }
}
