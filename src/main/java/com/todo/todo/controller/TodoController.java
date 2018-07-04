package com.todo.todo.controller;

import com.todo.todo.co.TodoCo;
import com.todo.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    TodoService todoService;

    @RequestMapping("/list")
    public String list(Model model, HttpSession httpSession) {
        Long id = (Long) httpSession.getAttribute("user");
        model.addAttribute("todoList", todoService.getTodoLists());
        return Objects.nonNull(id) ? "dashboard/dashboard" : "redirect:/";
    }

    @RequestMapping("/create")
    public String create(HttpSession httpSession) {
        Long id = (Long) httpSession.getAttribute("user");
        return Objects.nonNull(id) ? "dashboard/create" : "redirect:/";
    }


    @RequestMapping("/update")
    public String update(Model model, Long id, String status) {
        Boolean result = todoService.update(id, status);
        return "redirect:/todo/list";
    }

    @RequestMapping("/delete")
    public String delete(Model model, Long id) {
        Boolean result = todoService.delete(id);
        return "redirect:/todo/list";
    }

    @PostMapping("/addList")
    public String addList(Model model, @ModelAttribute("todoCo") TodoCo todoCo) {
        try {
            todoService.addList(todoCo);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Unable to save the task");
            return "/todo/create";
        }
        return "redirect:/todo/list";
    }


    @RequestMapping("/logout")
    public String logout(Model model, HttpSession httpSession) {
        httpSession.setAttribute("user", null);
        httpSession.invalidate();
        model.addAttribute("successMessage", "You have been successfully logout");
        return "login/login";
    }

}