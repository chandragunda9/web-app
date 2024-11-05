package com.learning.first_web_app.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

	@Bean
	UserDetailsManager configureUserDetails() {
//		UserDetails userDetails = User.withDefaultPasswordEncoder().username("chandra").password("gvn")
//				.roles("USER", "ADMIN").build();

		UserDetails userDetails1 = createUserDetails("chandra", "gvn");
		UserDetails userDetails2 = createUserDetails("venkata", "gvn");

		return new InMemoryUserDetailsManager(userDetails1, userDetails2);
	}

	private UserDetails createUserDetails(String username, String password) {
		Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);
		UserDetails userDetails = User.builder().passwordEncoder(passwordEncoder).username(username).password(password)
				.roles("USER", "ADMIN").build();
		return userDetails;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// all urls are protected
	// login form is shown for unauthorized requests
	// by default securityfilterchain handles above two

	// now we need to redefine securityfilterchain bcoz of
	// 1.cors issue when accessing h2 console
	// 2. frames in h2

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

		http.formLogin(withDefaults());

		http.csrf().disable();

		http.headers().frameOptions().disable();

		return http.build();
	}
}
