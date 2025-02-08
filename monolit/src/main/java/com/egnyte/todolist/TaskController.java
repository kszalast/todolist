package com.egnyte.todolist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/todo")
class TaskController {

    private final static Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TodoListRepository todoListRepository;
    private final TaskRepository taskRepository;

    TaskController(TodoListRepository todoListRepository, TaskRepository taskRepository) {
        this.todoListRepository = todoListRepository;
        this.taskRepository = taskRepository;
    }

    @PostMapping("/{todoListId}/task/add")
    public String addTask(@PathVariable("todoListId") Long todoListId, @RequestParam("description") String description) {
        TodoList todoList = todoListRepository.findById(todoListId).orElseThrow();
        Task task = new Task(description, todoList);
        taskRepository.save(task);
        log.info("Task added: {}", task);
        return "redirect:/";
    }

    @PostMapping("/{todoListId}/task/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") Long taskId) {
        taskRepository.deleteById(taskId);
        log.info("Task with id {} deleted", taskId);
        return "redirect:/";
    }

}