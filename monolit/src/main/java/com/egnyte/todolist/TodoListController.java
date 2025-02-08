package com.egnyte.todolist;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
class TodoListController {

    private final static Logger log = LoggerFactory.getLogger(TodoListController.class);

    private final TodoListRepository todoListRepository;

    TodoListController(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    @GetMapping
    public String getLists(Model model) {
        model.addAttribute("todoLists", todoListRepository.findAll());
        return "index";
    }

    @PostMapping("/add")
    public String addList(@RequestParam("name") String name) throws BadRequestException {
        if (ObjectUtils.isEmpty(name)) {
            throw new BadRequestException("TodoList name cannot be empty");
        }
        TodoList todoList = new TodoList(name);
        todoListRepository.save(todoList);
        log.info("TodoList added: {}", todoList);
        return "redirect:/";
    }

    @PostMapping("/delete/{todoListId}")
    public String deleteList(@PathVariable("todoListId") Long todoListId) {
        todoListRepository.deleteById(todoListId);
        log.info("TodoList with id {} deleted", todoListId);
        return "redirect:/";
    }

}