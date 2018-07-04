package com.todo.todo.service;

import com.todo.todo.co.TodoCo;
import com.todo.todo.dao.repo.TodoRepository;
import com.todo.todo.dao.repo.UserRepository;
import com.todo.todo.domain.Todo;
import com.todo.todo.domain.User;
import com.todo.todo.enums.TodoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TodoService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpSession httpSession;

    @Autowired
    TodoRepository todoRepository;

    public void addList(TodoCo todoCo) {
        Todo todo = new Todo();
        User user = userRepository.findById((Long) httpSession.getAttribute("user")).get();
        todo.setUser(user);
        todo.setTask(todoCo.getTask());
        todo.setDescription(todoCo.getDescription());
        todoRepository.save(todo);
    }

    public List<Todo> getTodoLists() {
        List<Todo> todoList = new ArrayList<>();
        if (Objects.nonNull(httpSession.getAttribute("user"))) {
            User user = userRepository.findById((Long) httpSession.getAttribute("user")).get();
            todoList = todoRepository.findAllByUser(user);
        }
        return todoList;
    }

    public Boolean update(Long id, String status) {
        Boolean result = true;
        try {
            if (Objects.nonNull(httpSession.getAttribute("user"))) {
                User user = userRepository.findById((Long) httpSession.getAttribute("user")).get();
                Todo todo = todoRepository.findByIdAndUser(id, user);
                todo.setTodoStatus(TodoStatus.valueOf(status));
                todoRepository.save(todo);

            } else {
                result = false;

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = false;


        }
        return result;

    }

    public Boolean delete(Long id) {
        Boolean result = true;
        try {
            if (Objects.nonNull(httpSession.getAttribute("user"))) {
                User user = userRepository.findById((Long) httpSession.getAttribute("user")).get();
                Todo todo = todoRepository.deleteByIdAndUser(id, user);
                todoRepository.save(todo);

            } else {
                result = false;

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = false;


        }
        return result;

    }
}




