package com.glowterx.glowterx.DOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.glowterx.glowterx.Model.Product;

import jakarta.servlet.http.HttpSession;

public class ProductDAO {
    @Autowired
    private DataSource dataSource;
    @Autowired
    HttpSession session;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addProduct(Product product) {
        String sql = "INSERT INTO product (prod_name, prod_price, prod_quantity, prod_category, prod_status) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getProd_name(), product.getProd_price(), product.getProd_quantity(),product.getProd_category(), product.getProd_status());
    }

}
