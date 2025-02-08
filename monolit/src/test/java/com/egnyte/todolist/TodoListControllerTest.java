package com.egnyte.todolist;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;

class TodoListControllerTest {

    @Test
    void GIVEN_empty_name_WHEN_add_THEN_throw_an_exception() {
        TodoListController underTest = new TodoListController(mock(TodoListRepository.class));

        assertThatThrownBy(() -> underTest.addList(""))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("TodoList name cannot be empty");
    }

}