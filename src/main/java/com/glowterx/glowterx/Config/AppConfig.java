package com.glowterx.glowterx.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.glowterx.glowterx.DOA.AdminDOA;
import com.glowterx.glowterx.DOA.InstructorDOA;
import com.glowterx.glowterx.DOA.TraineeDOA;

@Configuration
public class AppConfig {

    @Bean
    public AdminDOA adminDAO() {
        return new AdminDOA();
    }

    @Bean
    public InstructorDOA instructorDOA() {
        return new InstructorDOA();
    }

    @Bean
    public TraineeDOA traineeDOA() {
        return new TraineeDOA();
    }
}
