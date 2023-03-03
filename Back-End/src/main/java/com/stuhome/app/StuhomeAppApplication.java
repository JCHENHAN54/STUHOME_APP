package com.stuhome.app;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class StuhomeAppApplication implements WebMvcConfigurer{
	public static void main(String[] args){
		SpringApplication.run(StuhomeAppApplication.class, args);
	}
}
