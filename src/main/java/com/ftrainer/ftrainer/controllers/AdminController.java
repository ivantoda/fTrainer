package com.ftrainer.ftrainer.controllers;

import com.ftrainer.ftrainer.entities.User;
import com.ftrainer.ftrainer.security.SecurityUtils;
import com.ftrainer.ftrainer.services.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping()
    public String AdminIndex(Model model)
    {
        int userId = SecurityUtils.getCurrentUserId();
        model.addAttribute("userId", userId);
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
    @GetMapping("/showClients")
    public String showAllClients(Model model){
        List<User> clients = adminService.getClients();
        model.addAttribute("clients", clients);

        return "admin/showClients";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/showAllClients/{pageNo}")
    public String findPaginatedClients(  @PathVariable(value = "pageNo") int pageNo,
                                  @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder,
                                 @RequestParam(value="searchKeyword", required = false, defaultValue="") String searchKeyWord,
                                 Model model) {
        int pageSize = 5;
        Page<User> page;

        if ("desc".equalsIgnoreCase(sortOrder)) {
            page = adminService.findAllClientsSortedByFirstnameDesc(searchKeyWord, pageNo, pageSize);
        } else {
            page = adminService.findAllClientsSortedByFirstnameAsc(searchKeyWord, pageNo, pageSize);
        }

        List<User> listClients = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listClients", listClients);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("searchKeyword", searchKeyWord);

        return "/admin/showAllClients";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/showAllTrainers/{pageNo}")
    public String findPaginatedTrainers(  @PathVariable(value = "pageNo") int pageNo,
                                  @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder,
                                  @RequestParam(value="searchKeyword", required = false, defaultValue="") String searchKeyWord,
                                  Model model) {
        int pageSize = 5;
        Page<User> page;

        if ("desc".equalsIgnoreCase(sortOrder)) {
            page = adminService.findAllTrainersSortedByFirstnameDesc(searchKeyWord, pageNo, pageSize);
        } else {
            page = adminService.findAllTrainersSortedByFirstnameAsc(searchKeyWord, pageNo, pageSize);
        }

        List<User> listTrainers = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listTrainers", listTrainers);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("searchKeyword", searchKeyWord);

        return "/admin/showAllTrainers";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            User user = adminService.getUserById(id);
            String roleName = user.getUserRole().getName();

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
