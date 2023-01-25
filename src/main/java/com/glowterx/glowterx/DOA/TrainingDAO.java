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
        jdbcTemplate.update(sql, training.getName(), training.getStart_date(), training.getEnd_date(),
                training.getDuration(), training.getSessionNum(), training.getInstructorId());
    }
    
   
}
