package com.glowterx.glowterx.DOA;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.glowterx.glowterx.Model.Membership;
import com.glowterx.glowterx.Model.Trainee;
import com.glowterx.glowterx.Model.Payment;
import jakarta.servlet.http.HttpSession;

public class TraineeDAO {
    @Autowired
    private DataSource dataSource;
    @Autowired
    HttpSession session;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Trainee validate(String username, String password) {
        Trainee trainee = null;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM trainee WHERE username = ? AND password = ?")) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                trainee = new Trainee();
                trainee.setId(rs.getInt("id"));
                trainee.setTraineeUsername(rs.getString("username"));
                trainee.setTraineePass(rs.getString("password"));
                trainee.setFirstName(rs.getString("firstname"));
                trainee.setLastName(rs.getString("lastname"));
                trainee.setAddress(rs.getString("address"));
                trainee.setCity(rs.getString("city"));
                trainee.setState(rs.getString("state"));
                trainee.setZip(rs.getString("zip"));
                trainee.setPhone(rs.getString("phone"));
                trainee.setEmail(rs.getString("email"));
                trainee.setGender(rs.getString("gender"));
                trainee.setMembershipStatus(rs.getString("MembershipStatus"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trainee;
    }

    public Trainee getInfoTrainee() {
        String username = (String) session.getAttribute("username");
        Trainee trainee = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM trainee WHERE username = ?")) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                trainee = new Trainee();
                trainee.setId(rs.getInt("id"));
                trainee.setTraineeUsername(rs.getString("username"));
                trainee.setTraineePass(rs.getString("password"));
                trainee.setFirstName(rs.getString("firstname"));
                trainee.setLastName(rs.getString("lastname"));
                trainee.setAddress(rs.getString("address"));
                trainee.setCity(rs.getString("city"));
                trainee.setState(rs.getString("state"));
                trainee.setZip(rs.getString("zip"));
                trainee.setPhone(rs.getString("phone"));
                trainee.setEmail(rs.getString("email"));
                trainee.setGender(rs.getString("gender"));
                trainee.setMembershipStatus(rs.getNString("MembershipStatus"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainee;
    }

    public void uploadProfilePicture(String username, byte[] image) {
        String sql = "UPDATE trainee SET profle_images = ? WHERE username = ?";
        jdbcTemplate.update(sql, new Object[] { image, username });
    }

    public byte[] getProfilePicture(String username) {
        String sql = "SELECT profle_images FROM trainee WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { username }, byte[].class);
    }

    public void insertTrainee(Trainee trainee) {
        trainee.setMembershipStatus("Free");
        String sql = "INSERT INTO trainee (firstname, lastname, username, password, MembershipStatus, phone, email, gender, address, city, zip, state) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, trainee.getFirstName(), trainee.getLastName(), trainee.getTraineeUsername(),
                trainee.getTraineePass(), trainee.getMembershipStatus(), trainee.getPhone(), trainee.getEmail(),
                trainee.getGender(),
                trainee.getAddress(), trainee.getCity(), trainee.getZip(), trainee.getState());
    }

    public void updateProfile(Trainee trainee) {
        String sql = "UPDATE trainee SET firstName = ?, lastName = ?, gender = ?, username = ?, password = ?, phone = ?, address = ?, email = ?, state = ?, city = ? WHERE username = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trainee.getFirstName());
            statement.setString(2, trainee.getLastName());
            statement.setString(3, trainee.getGender());
            statement.setString(4, trainee.getTraineeUsername());
            statement.setString(5, trainee.getTraineePass());
            statement.setString(6, trainee.getPhone());
            statement.setString(7, trainee.getAddress());
            statement.setString(8, trainee.getEmail());
            statement.setString(9, trainee.getState());
            statement.setString(10, trainee.getCity());
            statement.setString(11, trainee.getTraineeUsername());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createMembership(Trainee  trainee,Payment payment, Membership membership) throws SQLException {
        try (Connection connection = dataSource.getConnection();) {

            String sql1 = "INSERT INTO payment (person_id,amount, payment_date, payment_status, payment_category) VALUES (?,?,?,?,?)";
            jdbcTemplate.update(sql1, payment.getPerson_id(), payment.getAmount(), payment.getPayment_date(),
                    payment.getPayment_status(), payment.getPayment_category());
            String sql2 = "INSERT INTO membership (person_id, start_date, membership_category) VALUES (?,?,?)";
            jdbcTemplate.update(sql2, membership.getPerson_id(),membership.startdate(), membership.getCategory());

            String sql3="UPDATE trainee set MembershipStatus = 'PAID' WHERE username = ?";
            jdbcTemplate.update(sql3, trainee.getTraineeUsername());
        }
    }
    /*public Payment getPaymentInfo ()
    {
       Date d= (Date) session.getAttribute("date");
       java.sql.Date sqlDate = (java.sql.Date) d;
       Trainee trainee = (Trainee) session.getAttribute("trainee");
        Payment payment = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM payment WHERE person_id = ? and payment_date = ?")) {

            statement.setInt(1, trainee.getId());
            statement.setDate(2, sqlDate);              
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                payment = new Payment();
                payment.setId(rs.getInt("id"));
                payment.setPerson_id(rs.getInt("person_id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPayment_date(rs.getDate("payment_date"));
                payment.setPayment_status(rs.getString("payment_status"));
                payment.setPayment_category(rs.getString("payment_category"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payment;
    }
   

    public void updatePaymentID(Payment payment, Trainee trainee) throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            String sql = "UPDATE membership SET payment_id = ? WHERE person_id = ? AND payment_date = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, payment.getId());
            ps.setInt(2, trainee.getId());
            java.util.Date utilDate = payment.getPayment_date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            ps.setDate(3, sqlDate);
             ps.executeUpdate();
             }
         catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    
}
