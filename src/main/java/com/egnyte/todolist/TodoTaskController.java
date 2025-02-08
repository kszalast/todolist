package com.egnyte.todolist;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
class TodoTaskController {
    private final TodoListRepository todoListRepository;
    private final TaskRepository taskRepository;

    TodoTaskController(TodoListRepository todoListRepository, TaskRepository taskRepository) {
        this.todoListRepository = todoListRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public String getLists(Model model) {
        model.addAttribute("todoLists", todoListRepository.findAll());
        return "index";
    }

//    curl -X POST  http://localhost:8080/todo/add -H 'Content-Type: application/json' -d '{ "name": "pierwszy task"  }'
    @PostMapping("/add")
    public String addList(@RequestBody TodoListDto name) {
        todoListRepository.save(new TodoList(name.name()));
        return "redirect:/todo";
    }

    public record TodoListDto(String name){}

    @PostMapping("/delete/{id}")
    public String deleteList(@PathVariable Long id) {
        todoListRepository.deleteById(id);
        return "redirect:/todo";
    }

    @PostMapping("/{id}/task/add")
    public String addTask(@PathVariable Long id, @RequestParam String description) {
        TodoList todoList = todoListRepository.findById(id).orElseThrow();
        taskRepository.save(new Task(description, todoList));
        return "redirect:/todo";
    }

    @PostMapping("/{listId}/task/delete/{taskId}")
    public String deleteTask(@PathVariable Long taskId) {
        taskRepository.deleteById(taskId);
        return "redirect:/todo";
    }
}