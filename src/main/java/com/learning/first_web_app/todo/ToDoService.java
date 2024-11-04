package com.learning.first_web_app.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

@Service
public class ToDoService {

	private static List<ToDo> todoList = new ArrayList<>();

	private static int todoCount = 0;

	static {
		todoList.add(new ToDo(++todoCount, "chandra", "learn spring boot", LocalDate.now().plusWeeks(3), false));
		todoList.add(new ToDo(++todoCount, "chandra", "learn aws", LocalDate.now().plusWeeks(3), false));
		todoList.add(
				new ToDo(++todoCount, "chandra", "learn full stack development", LocalDate.now().plusYears(3), false));
	}

	public List<ToDo> fetchTodosByUsername(String username) {
		return todoList;
	}

	public List<ToDo> addTodo(String username, String description, LocalDate date, boolean isDone) {
		ToDo todo = new ToDo(++todoCount, username, description, date, isDone);
		todoList.add(todo);
		return todoList;
	}

	public void deleteTodoById(long id) {
		Predicate<? super ToDo> predicate = todo -> todo.id == id;
		todoList.removeIf(predicate);
	}

	public ToDo findTodoById(long id) {
		Predicate<? super ToDo> predicate = todo -> todo.id == id;
		ToDo todo = todoList.stream().filter(predicate).findFirst().get();
		return todo;
	}

	public void updateTodoById(ToDo updateTodo) {
//		ToDo todo = findTodoById(updateTodo.id);
//		todo.setDescription(updateTodo.getDescription());

		deleteTodoById(updateTodo.getId());
		todoList.add(updateTodo);
	}

}
