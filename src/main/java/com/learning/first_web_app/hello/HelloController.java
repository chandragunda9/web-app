package com.learning.first_web_app.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@GetMapping("/say-hello")
//	@ResponseBody
	public String sayHello() {
		return "hello";
	}
	
}
