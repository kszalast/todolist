package com.egnyte.todolist;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todolist")
class TodoListController {
    private final TodoListRepository todoListRepository;

    TodoListController(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
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

    @PostMapping("/delete/{id}")
    public String deleteList(@PathVariable Long id) {
        todoListRepository.deleteById(id);
        return "redirect:/todo";
    }

}