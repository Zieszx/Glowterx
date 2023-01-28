package com.glowterx.glowterx.DOA;

import java.sql.Connection;
import java.sql.Date;
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
                        .prepareStatement("SELECT * FROM trainee WHERE TraineeUsername = ? AND TraineePass = ?")) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                trainee = new Trainee();
                trainee.setId(rs.getInt("id"));
                trainee.setTraineeUsername(rs.getString("TraineeUsername"));
                trainee.setTraineePass(rs.getString("TraineePass"));
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
                        .prepareStatement("SELECT * FROM trainee WHERE TraineeUsername = ?")) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                trainee = new Trainee();
                trainee.setId(rs.getInt("id"));
                trainee.setTraineeUsername(rs.getString("TraineeUsername"));
                trainee.setTraineePass(rs.getString("TraineePass"));
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

    public Trainee getInfoTraineebyUsername(String username) {
        Trainee trainee = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM trainee WHERE TraineeUsername = ?")) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                trainee = new Trainee();
                trainee.setId(rs.getInt("id"));
                trainee.setTraineeUsername(rs.getString("TraineeUsername"));
                trainee.setTraineePass(rs.getString("TraineePass"));
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

    public Trainee getInfoTraineebyId(int id) {
        Trainee trainee = null;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT * FROM trainee WHERE id = ?")) {

            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                trainee = new Trainee();
                trainee.setId(rs.getInt("id"));
                trainee.setTraineeUsername(rs.getString("TraineeUsername"));
                trainee.setTraineePass(rs.getString("TraineePass"));
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

    public void uploadProfilePicture(String username, byte[] image) {
        String sql = "UPDATE trainee SET profle_images = ? WHERE TraineeUsername = ?";
        jdbcTemplate.update(sql, new Object[] { image, username });
    }

    public byte[] getProfilePicture(String username) {
        String sql = "SELECT profle_images FROM trainee WHERE TraineeUsername = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] { username }, byte[].class);
    }

    public void insertTrainee(Trainee trainee) {
        String sql = "INSERT INTO trainee (firstname, lastname, TraineeUsername, TraineePass, phone, email, gender, address, city, zip, state) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, trainee.getFirstName(), trainee.getLastName(), trainee.getTraineeUsername(),
                trainee.getTraineePass(), trainee.getPhone(), trainee.getEmail(),
                trainee.getGender(),
                trainee.getAddress(), trainee.getCity(), trainee.getZip(), trainee.getState());
    }

    public void updateProfile(Trainee trainee) {
        String sql = "UPDATE trainee SET firstName = ?, lastName = ?, gender = ?, TraineeUsername = ?, TraineePass = ?, phone = ?, address = ?, email = ?, state = ?, city = ?, zip = ?, MembershipStatus = ? WHERE TraineeUsername = ?";
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
            statement.setString(11, trainee.getZip());
            statement.setString(12, trainee.getMembershipStatus());
            statement.setString(13, trainee.getTraineeUsername());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
