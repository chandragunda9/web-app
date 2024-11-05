package com.learning.first_web_app.todo;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

@Controller
//@SessionAttributes("name")
public class ToDoControllerJpa {

	@Autowired
	TodoRepository todoRepository;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/todo-list")
	public String listAllTodos(ModelMap model) {

		String username = getLoggedInUsername();
		List<ToDo> li = todoRepository.findByUsername(username);

		model.put("todos", li);
		logger.info("GET: /todo-list map: {}", model);
		return "todoList";
	}

	@GetMapping("/add-todo")
	public String fetchTodoPage(ModelMap model) {
		model.put("todo", new ToDo());
		logger.info(" GET: map: {}", model);
		return "addTodo";
	}

//	@PostMapping("/add-todo")
//	public String submitTodo(@RequestParam String description, ModelMap model) {
//		String username = getLoggedInUsername();
//		toDoService.addTodo(username, description, LocalDate.now().plusYears(1), false);
//		return "redirect:/todo-list";
//	}

	@PostMapping("/add-todo")
	public String submitTodo(ModelMap model, @ModelAttribute("todo") @Valid ToDo todo, BindingResult res) {
		logger.info("POST: map: {}", model);
		logger.info("POST: todo:{}", todo);
		if (res.hasErrors()) {
			return "addTodo";
		}
		String username = getLoggedInUsername();
		todo.setUsername(username);

		todoRepository.save(todo);
		return "redirect:/todo-list";
	}

	@GetMapping("/delete-todo")
	public String deleteTodo(@RequestParam long id) {
		todoRepository.deleteById(id);
		return "redirect:/todo-list";
	}

	@GetMapping("/update-todo")
	public String updateTodo(@RequestParam long id, ModelMap model) {
		Optional<ToDo> todo = todoRepository.findById(id);
		if (todo.isPresent()) {
			model.put("todo", todo.get());
			logger.info("/update-todo GET map:{}", model);
			return "addTodo";
		}
		return "redirect:/todo-list";
	}

	@PostMapping("/update-todo")
	public String submitUpdateTodo(@ModelAttribute("todo") @Valid ToDo updateTodo, BindingResult res, ModelMap model) {
		logger.info("POST: /update-todo map: {}", model);
		if (res.hasErrors()) {
			return "addTodo";
		}
		updateTodo.setUsername(getLoggedInUsername());
		logger.info("POST: /update-todo todo:{}", updateTodo);

		Optional<ToDo> todoOptional = todoRepository.findById(updateTodo.getId());
		if (todoOptional.isPresent()) {
			todoRepository.save(updateTodo);
		}
		return "redirect:/todo-list";
	}

	private String getLoggedInUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}
