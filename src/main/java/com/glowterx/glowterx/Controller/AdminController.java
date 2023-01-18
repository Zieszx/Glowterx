package com.glowterx.glowterx.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import com.glowterx.glowterx.Model.Admin;

public class AdminController {
    @Autowired
    HttpSession session;

}
