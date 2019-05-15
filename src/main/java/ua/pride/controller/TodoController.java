package ua.pride.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.pride.entity.Todo;
import ua.pride.service.TodoService;

import java.util.List;

@RestController
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping(value = "/")
    public ModelAndView getTodoList() {
        ModelAndView mav = new ModelAndView("view/tasks");
        List<Todo> tasks = todoService.todoList();
        mav.addObject("tasks", tasks);
        mav.addObject("task", new Todo());
        mav.addObject("title", "Tasks");
        mav.addObject("isAdd", true);
        return mav;
    }

    @PostMapping(value = "/save")
    public ModelAndView save(@ModelAttribute Todo todo, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("view/tasks");
        Todo task = todoService.save(todo);
        if (task != null) {
            redirectAttributes.addFlashAttribute("successMessage", "Task added successfully");
            return new ModelAndView("redirect:/");
        } else {
            mav.addObject("errorMessage", "Task not added, try again");
            mav.addObject("todo", todo);
            return mav;
        }
    }

    @GetMapping(value = "/getTask/{id}")
    public ModelAndView getTask(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("view/tasks");
        Todo task = todoService.findById(id);
        List<Todo> tasks = todoService.todoList();
        mav.addObject("tasks", tasks);
        mav.addObject("task", task);
        mav.addObject("title", "Edit task");
        mav.addObject("isAdd", false);
        return mav;
    }

    @PostMapping(value = "/update")
    public ModelAndView update(@ModelAttribute Todo todo, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("view/tasks");
        Todo task = todoService.update(todo);
        if (task != null) {
            redirectAttributes.addFlashAttribute("successMessage", "Task updated successfully");
            return new ModelAndView("redirect:/");
        } else {
            mav.addObject("errorMessage", "Task not updated, try again");
            mav.addObject("todo", todo);
            return mav;
        }
    }

    @GetMapping(value = "/deleteTask/{id}")
    public ModelAndView deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("redirect:/");
        todoService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Task deleted successfully");
        return mav;
    }
}
