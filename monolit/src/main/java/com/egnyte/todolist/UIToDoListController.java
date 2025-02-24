package com.egnyte.todolist;


import org.apache.coyote.BadRequestException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todolists")
public class UIToDoListController {

    private final TodoListRepository todoListRepository;
    private final TaskRepository taskRepository;

    public UIToDoListController(TodoListRepository todoListRepository, TaskRepository taskRepository) {
        this.todoListRepository = todoListRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<TodoList> getAllLists() {
        return todoListRepository.findAll();
    }

    @PostMapping
    public TodoList createList(@RequestBody Command name) throws BadRequestException {
        if (ObjectUtils.isEmpty(name)) {
            throw new BadRequestException("TodoList name cannot be empty");
        }
        TodoList todoList = new TodoList(name.getName());
        return todoListRepository.save(todoList);
    }

    @DeleteMapping("/{id}")
    public void deleteList(@PathVariable("id") Long id) {
        todoListRepository.deleteById(id);
    }

    @GetMapping("/{id}/tasks")
    public List<Task> getAllLists(@PathVariable("id")Long id) {
        return todoListRepository.findById(id).get().getTasks();
    }

    @PostMapping("/{id}/tasks")
    public Task createTask(@PathVariable("id") Long id, @RequestBody Command name) throws BadRequestException {
        TodoList todoList = todoListRepository.findById(id).orElseThrow();
        Task task = new Task(name.name, todoList);
        return taskRepository.save(task);
    }

    @DeleteMapping("/{id}/tasks/{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public static class Command {
        private String name;

        public String getName() {
            return name;
        }
    }
}
