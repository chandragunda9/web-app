package com.learning.first_web_app.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
//@SessionAttributes("name")
public class WelcomeController {
//
//	@Autowired
//	AuthenticationService authService;

	private Logger logger = LoggerFactory.getLogger(getClass());

//	@GetMapping("/login")
//	public String fetchLoginPage(@RequestParam String name, ModelMap modelMap) {
//		modelMap.put("name", name);
//		logger.info("info level logger");
//		logger.debug("debug level logger");
//		return "login";
//	}

//	@GetMapping("/login")
//	public String fetchLoginPage() {
//		return "login";
//	}

//	@PostMapping("/login")
//	public String submitLogin(@RequestParam String username, @RequestParam String password, ModelMap model) {
//		if (authService.checkUser(username, password)) {
//			model.put("name", username);
//			return "welcome";
//		}
//		model.put("error", "Invalid Credentials!!");
//		return "login";
//	}

	@GetMapping("/")
	public String fetchHome(ModelMap map) {
//		map.put("name", "chandra");
		map.put("name", getLoggedInUsername());
		return "welcome";
	}

	private String getLoggedInUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}
