package com.learning.first_web_app.login.service;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	public boolean checkUser(String username, String pwd) {
		return "chandra".equals(username) && "gvn".equals(pwd);
	}

}
