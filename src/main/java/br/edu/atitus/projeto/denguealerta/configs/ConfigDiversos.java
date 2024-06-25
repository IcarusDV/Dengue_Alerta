package br.edu.atitus.projeto.denguealerta.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ConfigDiversos {
	
	@Bean
	PasswordEncoder getEnconder() {
		return new BCryptPasswordEncoder();
	}

}