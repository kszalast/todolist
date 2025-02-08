package com.egnyte.todolist;

import org.apache.coyote.BadRequestException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleBadRequestException(BadRequestException ex, Model model) {
        model.addAttribute("exception", ex.getClass().getCanonicalName());
        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}

