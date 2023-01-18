package com.glowterx.glowterx.DOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.glowterx.glowterx.Model.Training;

import jakarta.servlet.http.HttpSession;

public class TrainingDAO {
    @Autowired
    private DataSource dataSource;
    @Autowired
    HttpSession session;

    
}
