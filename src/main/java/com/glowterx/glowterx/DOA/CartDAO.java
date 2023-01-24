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

import com.glowterx.glowterx.Model.Cart;
import com.glowterx.glowterx.Model.Product;

import jakarta.servlet.http.HttpSession;

public class CartDAO {
    @Autowired
    private DataSource dataSource;
    @Autowired
    HttpSession session;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addCart(Cart cart) {
        String sql = "INSERT INTO cart (quantity, person_id, product_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, cart.getQuantity(), cart.getPerson_id(), cart.getProduct_id());
    }

    /*public Product getProduct() {
        Product product = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM Product")) {

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setProd_name(rs.getString("prod_name"));
                product.setProd_price(rs.getDouble("prod_price"));
                product.setProd_quantity(rs.getInt("prod_quantity"));
                product.setProd_category(rs.getString("prod_category"));
                product.setProd_status(rs.getString("prod_status"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }*/

    public List <Cart> getAllCart(){
        return jdbcTemplate.query("SELECT * FROM cart", new BeanPropertyRowMapper<>(Cart.class));
    }
}
