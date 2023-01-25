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
import org.springframework.stereotype.Repository;

import com.glowterx.glowterx.Model.Attendance;
import com.glowterx.glowterx.Model.Trainee;
import com.glowterx.glowterx.Model.Training;

import jakarta.servlet.http.HttpSession;

@Repository
public class AttendanceDAO {
    @Autowired
    private DataSource dataSource;

    @Autowired
    HttpSession session;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void enrollTraining (Trainee trainee, Training training){
        String sql = "INSERT INTO attendance (person_id, training_id, attendance_status, attendance_checkins) VALUES (?, ?, ?, ?)";
        String status = "Enrolled";
        int checkins = 0;
        jdbcTemplate.update(sql, trainee.getId(), training.getId(), status, checkins);
    }
}
