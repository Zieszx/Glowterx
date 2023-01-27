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

import com.glowterx.glowterx.Model.Admin;
import com.glowterx.glowterx.Model.Attendance;
import com.glowterx.glowterx.Model.Instructor;
import com.glowterx.glowterx.Model.Trainee;
import com.glowterx.glowterx.Model.Training;
import com.glowterx.glowterx.Model.Membership;
import com.glowterx.glowterx.Model.Attendance;

import jakarta.servlet.http.HttpSession;
@Repository
public class ReportDAO {
    @Autowired
    private DataSource dataSource;
    @Autowired
    HttpSession session;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
     * REPORT TRAINEE
     * List >> username - name - email - phone 
     * Report Card  >> image - email
     *              >> username - name - phone - address - membership
     *              >> training name - start date - end date - duration - numOfSession - attendance
     * 
     * REPORT INSTRUCTOR
     * List >> username - name - email - phone
     * Report Card  >> image - email
     *              >> username - name - phone - address
     *              >> training name - start date - end date - duration - numOfSession - numOfTrainee
     */

    public List <Training> getAllTraining(){
        String sql="SELECT * FROM training";
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

    
}
