package com.ftrainer.ftrainer.controllers;

import com.ftrainer.ftrainer.dto.ClientRequestPayload;
import com.ftrainer.ftrainer.dto.ImagePayload;
import com.ftrainer.ftrainer.dto.UserPayload;
import com.ftrainer.ftrainer.entities.*;
import com.ftrainer.ftrainer.repositories.*;
import com.ftrainer.ftrainer.security.SecurityUtils;
import com.ftrainer.ftrainer.services.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Security;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    private final UserService userService;
    private final RoleRepository roleRepository;

    private final ExerciseRepository exerciseRepository;
    private final SetExerciseRepository setExerciseRepository;

    private final ProgramRepository programRepository;

    private final ClientRequestService clientRequestService;
    private final UserRepository userRepository;
    @Autowired
    private final WebSocketController webSocketController;

    public ClientController(UserService userService, RoleRepository roleRepository, ClientService clientService, ExerciseRepository exerciseRepository, SetExerciseRepository setExerciseRepository, ProgramRepository programRepository, ClientRequestService clientRequestService,
                            UserRepository userRepository, WebSocketController webSocketController) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.clientService = clientService;
        this.exerciseRepository = exerciseRepository;
        this.setExerciseRepository = setExerciseRepository;
        this.programRepository = programRepository;
        this.clientRequestService = clientRequestService;
        this.userRepository = userRepository;
        this.webSocketController = webSocketController;
    }

    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @GetMapping()
    public String viewHomePage(Model model) {
        int userId = SecurityUtils.getCurrentUserId();
        model.addAttribute("userId", userId);
        return "client/clientIndex";
    }

    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @GetMapping("/showAllTrainers")
    public String showAllTrainers(@RequestParam(defaultValue = "asc") String sort, Model model) {
        List<User> trainers;
        if (sort.equals("asc")) {
            trainers = clientService.getTrainersAsc();
        } else {
            trainers = clientService.getTrainersDesc();
        }
        model.addAttribute("trainers", trainers);
        return "client/showTrainers";
    }

    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @GetMapping("/sendRequestForm")
    public String sendRequestForm(@RequestParam("trainerId") Integer trainerId, Model model){
        User trainer = userService.findById(trainerId);
        model.addAttribute("trainer", trainer);
        return "client/sendRequestForm";
    }

    @PostMapping("/sendRequest/{trainer_id}")
    public String sendRequest(@PathVariable("trainer_id") Integer trainerId, @ModelAttribute ClientRequestPayload requestPayload, Model model) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            var clientName = authentication.getName();
            User client = userRepository.findByUsername(clientName);

            model.addAttribute("client", client);

            User trainer = userService.findById(trainerId);
            User clientUser = userService.findById(client.getId());

            ClientRequest clientRequest = new ClientRequest();

            clientRequest.setId(requestPayload.getId());
            clientRequest.setTrainer(trainer);
            clientRequest.setClient(clientUser);
            clientRequest.setDescription(requestPayload.getDescription());

            clientRequestService.createRequest(clientRequest);

            String notificationMessage = "New request from client: " + clientUser.getUsername();
            webSocketController.sendNotification(notificationMessage);

            return "redirect:/client";
        } else {
            return "redirect:/error";
        }
    }
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @GetMapping("/showClientProgram")
    public String showAllPrograms(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var clientName = authentication.getName();
        User client = userRepository.findByUsername(clientName);
        List<SetExercise> setExercises = setExerciseRepository.findAll();
        List<Program> programs = programRepository.findAll();
        List<Exercise> exercises = exerciseRepository.findAll();
        model.addAttribute("programs", programs);
        model.addAttribute("setExercises",setExercises);
        model.addAttribute("exercises",exercises);
        model.addAttribute("client",client);
        return "client/showPrograms";
    }
}
