package com.collabtodo.collabtodo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/")
    public String listTasks(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "task/list";
    }

    @GetMapping("/task/add")
    public String showAddTaskForm(Task task) {
        return "task/add";
    }

    @PostMapping("/task/add")
    public String addTask(@Validated Task task, BindingResult result) {
        if (result.hasErrors()) {
            return "task/add";
        } else {
            taskRepository.save(task);
            return "redirect:/";
        }
    }

    @GetMapping("/task/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
        model.addAttribute("task", task);
        return "task/edit";
    }

    @PostMapping("/task/edit/{id}")
    public String updateTask(@PathVariable Long id, @Validated Task task, BindingResult result) {
        if (result.hasErrors()) {
            return "task/edit";
        } else {
            taskRepository.save(task);
            return "redirect:/";
        }
    }

    @GetMapping("/task/delete/{id}")
    public String showDeleteTaskForm(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
        model.addAttribute("task", task);
        return "task/delete";
    }

    @PostMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
        taskRepository.delete(task);
        return "redirect:/";
    }
}
