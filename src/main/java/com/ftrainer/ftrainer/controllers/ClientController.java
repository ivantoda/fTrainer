package com.ftrainer.ftrainer.controllers;

import com.ftrainer.ftrainer.dto.ClientRequestPayload;
import com.ftrainer.ftrainer.dto.UserPayload;
import com.ftrainer.ftrainer.entities.*;
import com.ftrainer.ftrainer.security.SecurityUtils;
import com.ftrainer.ftrainer.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    private final UserService userService;

    private final ExerciseService exerciseService;

    private final ProgramService programService;
    private final ClientRequestService clientRequestService;

    private final SetExerciseService setExerciseService;
    @Autowired
    private final WebSocketController webSocketController;

    public ClientController(UserService userService, ClientService clientService, ExerciseService exerciseService, SetExerciseService setExerciseService, ProgramService programService, ClientRequestService clientRequestService,
                            WebSocketController webSocketController) {
        this.userService = userService;
        this.clientService = clientService;
        this.exerciseService = exerciseService;
        this.programService = programService;
        this.clientRequestService = clientRequestService;
        this.webSocketController = webSocketController;
        this.setExerciseService = setExerciseService;
    }

    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @GetMapping()
    public String viewHomePage() {
        return "client/clientIndex";
    }

    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @GetMapping({"/showAllTrainers/{pageNo}", "/showAllTrainers"})
    public String findPaginatedTrainers(  @PathVariable(required = false, value = "pageNo") Integer pageNo,
                                          @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder,
                                          @RequestParam(value="searchKeyword", required = false, defaultValue="") String searchKeyWord,
                                          Model model) {
        if (pageNo == null) {
            pageNo = 1;
        }

        int pageSize = 8;
        Page<User> page;

        if ("desc".equalsIgnoreCase(sortOrder)) {
            page = clientService.findAllTrainersSortedByFirstnameDesc(searchKeyWord, pageNo, pageSize);
        } else {
            page = clientService.findAllTrainersSortedByFirstnameAsc(searchKeyWord, pageNo, pageSize);
        }

        List<User> trainers = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("trainers", trainers);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("searchKeyword", searchKeyWord);

        return "/client/showTrainers";
    }

    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @GetMapping("/sendRequestForm")
    public String sendRequestForm(@RequestParam("trainerId") Integer trainerId, Model model){
        User trainer = userService.findById(trainerId);
        model.addAttribute("trainer", trainer);
        return "client/sendRequestForm";
    }

    @PostMapping("/sendRequest/{trainer_id}")
    public String sendRequest(@PathVariable("trainer_id") Integer trainerId,
                              @ModelAttribute ClientRequestPayload requestPayload,
                              RedirectAttributes redirectAttributes)
    {
        int userId = SecurityUtils.getCurrentUserId();
        User client = userService.findById(userId);
        if (client != null) {

            User trainer = userService.findById(trainerId);
            clientRequestService.createRequest(trainer, client, requestPayload);

            String notificationMessage = "New request from client: " + client.getUsername();
            webSocketController.sendNotification(notificationMessage);

            redirectAttributes.addFlashAttribute("successMessage", "Request sent!");

            return "redirect:/client";
        } else {
            return "redirect:/error";
        }
    }
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @GetMapping({"/showClientProgram/{pageNo}", "/showClientProgram" })
    public String showAllPrograms(@PathVariable(required = false, value = "pageNo") Integer pageNo,
                                  @RequestParam(value="searchKeyword", required = false, defaultValue = "") String searchKeyWord,
                                  Model model){
        int userId = SecurityUtils.getCurrentUserId();
        if(pageNo == null){
            pageNo = 1;
        }
        int pageSize = 6;
        UserPayload clientPayload = userService.findById2(userId);

        Map<Program, UserPayload> programs = programService.findProgramsByClient(clientPayload, searchKeyWord, pageNo, pageSize);

        model.addAttribute("programs", programs);
        model.addAttribute("client",clientPayload);
        return "client/showPrograms";
    }

    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @GetMapping("/showProgramDetails")
    public String showProgramDetails(@RequestParam(value="programId") Integer programId,
                                     @RequestParam(value="trainerId") Integer trainerId,
                                     Model model){
        Optional<Program> program = programService.findById(programId);
        UserPayload trainer = userService.findById2(trainerId);
        if(program.isPresent()){
            Map<SetExercise, Exercise> setExercises = setExerciseService.getByProgramId(program.get().getId());
            model.addAttribute("program", program.get());
            model.addAttribute("trainer", trainer);
            model.addAttribute("setExercises", setExercises);
            return "client/showProgramDetails";
        }else
        {
            return "redirect:/error";
        }
    }
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @PostMapping("/deleteProgram/{programId}")
    public String deleteProgram(@PathVariable Integer programId,
                                RedirectAttributes redirectAttributes) {
        Optional<Program> programOptional = programService.findById(programId);

        if (programOptional.isPresent()) {
            Program program = programOptional.get();
            programService.delete(program);
            redirectAttributes.addFlashAttribute("successMessage", "Program deleted!");
            return "redirect:/client/showClientProgram";
        }
        return "redirect:/client/";
    }
}
