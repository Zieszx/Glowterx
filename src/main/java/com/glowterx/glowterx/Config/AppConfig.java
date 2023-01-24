package com.glowterx.glowterx.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.glowterx.glowterx.DOA.AdminDAO;
import com.glowterx.glowterx.DOA.CartDAO;
import com.glowterx.glowterx.DOA.InstructorDAO;
import com.glowterx.glowterx.DOA.ProductDAO;
import com.glowterx.glowterx.DOA.TraineeDAO;
import com.glowterx.glowterx.DOA.TrainingDAO;

@Configuration
public class AppConfig {

    @Bean
    public AdminDAO adminDAO() {
        return new AdminDAO();
    }

    @Bean
    public InstructorDAO instructorDOA() {
        return new InstructorDAO();
    }

    @Bean
    public TraineeDAO traineeDOA() {
        return new TraineeDAO();
    }

    @Bean
    public TrainingDAO trainingDOA() {
        return new TrainingDAO();
    }

    @Bean
    public ProductDAO productDOA() {
        return new ProductDAO();
    }

    @Bean
    public CartDAO cartDOA() {
        return new CartDAO();
    }
}
