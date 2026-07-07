package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@SpringBootApplication(
    exclude = {UserDetailsServiceAutoConfiguration.class}  // ← Desactiva la autoconfiguración por defecto
)
@Import(com.example.demo.Security.SecurityConfig.class)
@ComponentScan(basePackages = {"com.example.demo.Security"})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
