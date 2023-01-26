package com.glowterx.glowterx.DOA;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.glowterx.glowterx.Model.Admin;
import com.glowterx.glowterx.Model.Instructor;
import com.glowterx.glowterx.Model.Membership;
import com.glowterx.glowterx.Model.Trainee;
import com.glowterx.glowterx.Model.Training;

import jakarta.servlet.http.HttpSession;

public class MembershipDAO {
    @Autowired
    private DataSource dataSource;

    @Autowired
    HttpSession session;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createMembership(Membership membership) throws SQLException {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(
                                "insert into Membership(person_id, start_date, membership_category, payment_id) values (?, ?, ?, ?)")) {
            preparedStatement.setInt(1, membership.getPerson_id());
            preparedStatement.setDate(2, (Date) membership.getstart_date());
            preparedStatement.setString(3, membership.getmembership_category());
            System.out.print(membership.getPayment_id());
            preparedStatement.setInt(4, membership.getPayment_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Membership> getAllMembershipbyID(int id) {
        return jdbcTemplate.query("SELECT * FROM MEMBERSHIP WHERE person_id= ?",
                new BeanPropertyRowMapper<>(Membership.class), id);
    }

}
