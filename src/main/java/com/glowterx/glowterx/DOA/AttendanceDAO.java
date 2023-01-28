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
import com.glowterx.glowterx.Model.Instructor;
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

    public void enrollTraining(Trainee trainee, Training training) {
        String sql = "INSERT INTO attendance (person_id, training_id, attendance_status, attendance_checkins) VALUES (?, ?, ?, ?)";
        String status = "Enrolled";
        int checkins = 0;
        jdbcTemplate.update(sql, trainee.getId(), training.getId(), status, checkins);
    }

    public boolean enrollCheck(Trainee trainee, Training training) {
        Attendance attendance = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM attendance WHERE person_id = ? AND training_id = ?")) {

            statement.setInt(1, trainee.getId());
            statement.setInt(2, training.getId());

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                attendance = new Attendance();
                attendance.setId(rs.getInt("id"));
                attendance.setPerson_id(rs.getInt("person_id"));
                attendance.setTraining_id(rs.getInt("training_id"));
                attendance.setAttendance_status(rs.getString("attendance_status"));
                attendance.setAttendance_checkins(rs.getInt("attendance_checkins"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (attendance != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<Attendance> getTraineeAttendance(Trainee trainee) {
        String sql = "SELECT * FROM attendance WHERE person_id = ?";
        List<Attendance> attendance = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Attendance>(Attendance.class),
                trainee.getId());
        return attendance;
    }

    public Attendance getAttendancebyID(int attendance_id) {
        String sql = "SELECT * FROM attendance WHERE id = ?";
        Attendance attendance = jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<Attendance>(Attendance.class), attendance_id);
        return attendance;
    }

    public List<Attendance> getAttendancebyTraining(int training_id) {
        String sql = "SELECT * FROM attendance WHERE training_id = ?";
        List<Attendance> attendance = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Attendance>(Attendance.class),
                training_id);
        return attendance;
    }

    public void checkIn(int attendance_id, Trainee trainee, Training training) {
        String sql = "SELECT * FROM attendance WHERE id = ?";
        Attendance attendance = jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<Attendance>(Attendance.class), attendance_id);
        int checkins = attendance.getAttendance_checkins();
        sql = "UPDATE attendance SET attendance_status = ?, attendance_checkins = ? WHERE person_id = ? AND id = ?";
        if (checkins == training.getTraining_session()) {
            String status = "Completed";
            jdbcTemplate.update(sql, status, checkins, trainee.getId(), attendance_id);
        } else {
            checkins++;
            if (checkins == training.getTraining_session()) {
                String status = "Completed";
                jdbcTemplate.update(sql, status, checkins, trainee.getId(), attendance_id);
            }
            String status = "Ongoing";
            jdbcTemplate.update(sql, status, checkins, trainee.getId(), attendance_id);
        }
    }
}
