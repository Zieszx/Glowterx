package com.glowterx.glowterx.DOA;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.glowterx.glowterx.Model.Membership;
import com.glowterx.glowterx.Model.Payment;
import com.glowterx.glowterx.Model.Trainee;
import jakarta.servlet.http.HttpSession;

public class PaymentDAO {
    @Autowired
    private DataSource dataSource;

    @Autowired
    HttpSession session;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createPayment(Payment payment) throws SQLException {
        int id = 0;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(
                                "insert into Payment(amount, payment_category, person_id, payment_status, payment_date) values (?, ?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, payment.getAmount());
            preparedStatement.setString(2, payment.getPayment_category());
            preparedStatement.setInt(3, payment.getPerson_id());
            preparedStatement.setString(4, payment.getPayment_status());
            preparedStatement.setDate(5, new java.sql.Date(payment.getPayment_date().getTime()));
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);

            }

        }

        catch (SQLException e) {
            e.printStackTrace();
        }
        return id;

    }

    public Payment getPaymentbyID(int id) {
        String sql = "SELECT * FROM Payment WHERE id = ?";
        Payment payment = jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<Payment>(Payment.class), id);
        return payment;
    }

    public List<Payment> getMembershipPaymentbyID(int id) {
        return jdbcTemplate.query("SELECT * FROM payment WHERE id= ?",
                new BeanPropertyRowMapper<>(Payment.class), id);
    }
}
