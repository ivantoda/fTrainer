package com.ftrainer.ftrainer.controllers;

import com.ftrainer.ftrainer.dto.UserPayload;
import com.ftrainer.ftrainer.entities.User;
import com.ftrainer.ftrainer.repositories.RoleRepository;
import com.ftrainer.ftrainer.repositories.UserRepository;
import com.ftrainer.ftrainer.services.AdminService;
import com.ftrainer.ftrainer.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public AdminController(AdminService adminService, UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping()
    public String AdminIndex(){
            return "admin/adminIndex";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/showAllTrainers")
    public String showAllTrainers(Model model){
        List<User> trainers = adminService.getTrainers();
        model.addAttribute("trainers", trainers);

        return "admin/showAllTrainers";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/showAllClients")
    public String showAllClients(Model model){
        List<User> clients = adminService.getClients();
        model.addAttribute("clients", clients);

        return "admin/showAllClients";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            User user = adminService.getUserById(id);
            String roleName = user.getUserRole().getName(); // Get the role name

            adminService.deleteUserById(id);

            if (roleName.equals("CLIENT")) {
                redirectAttributes.addFlashAttribute("message", "The user with id=" + id + " has been deleted successfully!");
                return "redirect:/admin/showAllClients";
            } else {
                redirectAttributes.addFlashAttribute("message", "The user with id=" + id + " has been deleted successfully!");
                return "redirect:/admin/showAllTrainers";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/showAllClients";
        }
    }
}
