package com.learning.first_web_app.todo;

import java.util.List;

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

//@Controller
//@SessionAttributes("name")
public class ToDoController {

	@Autowired
	ToDoService toDoService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/todo-list")
	public String listAllTodos(ModelMap model) {
		String username = getLoggedInUsername();
		List<ToDo> li = toDoService.fetchTodosByUsername(username);
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
		toDoService.addTodo(username, todo.getDescription(), todo.getTargetDate(), todo.isDone());
		return "redirect:/todo-list";
	}

	@GetMapping("/delete-todo")
	public String deleteTodo(@RequestParam long id) {
		toDoService.deleteTodoById(id);
		return "redirect:/todo-list";
	}

	@GetMapping("/update-todo")
	public String updateTodo(@RequestParam long id, ModelMap model) {
		ToDo todo = toDoService.findTodoById(id);
		model.put("todo", todo);
		logger.info("/update-todo GET map:{}", model);
		return "addTodo";
	}

	@PostMapping("/update-todo")
	public String submitUpdateTodo(@ModelAttribute("todo") @Valid ToDo updateTodo, BindingResult res, ModelMap model) {
		logger.info("POST: /update-todo map: {}", model);
		if (res.hasErrors()) {
			return "addTodo";
		}
		updateTodo.setUsername(getLoggedInUsername());
		logger.info("POST: /update-todo todo:{}", updateTodo);

		toDoService.updateTodoById(updateTodo);
		return "redirect:/todo-list";
	}
	
	
	private String getLoggedInUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}
