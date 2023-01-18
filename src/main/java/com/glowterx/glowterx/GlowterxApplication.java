package com.glowterx.glowterx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.glowterx.glowterx", "com.glowterx.glowterx.config" })
public class GlowterxApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlowterxApplication.class, args);
	}

}
