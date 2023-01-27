package com.glowterx.glowterx.DOA;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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

import com.glowterx.glowterx.Model.Trainee;
import com.glowterx.glowterx.Model.Payment;
import com.glowterx.glowterx.Model.OrderUser;

import jakarta.servlet.http.HttpSession;

public class OrderDAO {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createOrder(int person_id, Payment payment, int order_quantity) {
        String status = "PENDING";
        String sql = "INSERT INTO orderUser(person_id, payment_id, order_quantity, order_date, order_status) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, person_id, payment.getId(), order_quantity,
                new java.sql.Date(payment.getPayment_date().getTime()), status);
    }

}
