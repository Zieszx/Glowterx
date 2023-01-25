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

    /*public Cart getInfoAdmin() {
        String username = (String) session.getAttribute("username");
        Admin admin = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM Admin WHERE adminUsername = ?")) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setAdminUsername(rs.getString("adminUsername"));
                admin.setAdminPass(rs.getString("adminPass"));
                admin.setFirstName(rs.getString("firstname"));
                admin.setLastName(rs.getString("lastname"));
                admin.setAddress(rs.getString("address"));
                admin.setCity(rs.getString("city"));
                admin.setState(rs.getString("state"));
                admin.setZip(rs.getString("zip"));
                admin.setPhone(rs.getString("phone"));
                admin.setEmail(rs.getString("email"));
                admin.setGender(rs.getString("gender"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }*/

    public List <Cart> getAllCart(){
        return jdbcTemplate.query("SELECT * FROM cart", new BeanPropertyRowMapper<>(Cart.class));
    }
}
