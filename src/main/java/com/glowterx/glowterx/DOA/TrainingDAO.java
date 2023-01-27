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

import com.glowterx.glowterx.Model.Instructor;
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

    public Training getInfoTrainingByID(int id) {
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

    public void updateProfile(Training training, int id) {
        
          String sql =
          "UPDATE training SET  training_name= ?, start_date = ?, end_date = ?, instructor_id = ?, training_session = ?, training_duration = ? WHERE id = ?"
          ;
         try (Connection connection = dataSource.getConnection();
          PreparedStatement statement = connection.prepareStatement(sql)) {
         statement.setString(1, training.getTraining_name());
         statement.setDate(2, training.getStart_date());
          statement.setDate(3, training.getEnd_date());
          statement.setInt(4, id);
          statement.setInt(5, training.getTraining_session());
          statement.setInt(6, training.getTraining_duration());
          statement.setInt(7, training.getId());
           
          statement.executeUpdate();
          } catch (SQLException e) {
          e.printStackTrace();
          }
         

    }

    public List<Training> getTrainingList() {
        String sql = "SELECT * FROM training";
        List<Training> trainingList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Training.class));
        return trainingList;
    }

    public List<Training> getInstructorTraining(Instructor instructor) {
        String sql = "SELECT * FROM training WHERE instructor_id = ?";
        List<Training> trainingList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Training.class),
                instructor.getId());
        return trainingList;
    }
}
