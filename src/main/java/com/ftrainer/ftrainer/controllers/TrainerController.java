package com.ftrainer.ftrainer.controllers;

import com.ftrainer.ftrainer.dto.ExercisePayload;
import com.ftrainer.ftrainer.entities.*;
import com.ftrainer.ftrainer.repositories.*;
import com.ftrainer.ftrainer.security.JwtUtil;
import com.ftrainer.ftrainer.security.SecurityUtils;
import com.ftrainer.ftrainer.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/trainer")
public class TrainerController {
    private final ExerciseService exerciseService;

    private final ClientRequestService clientRequestService;

    private final ProgramService programService;

    private final UserService userService;

    private final SetExerciseService setExerciseService;
    private final ProgramRepository programRepository;
    private final SetExerciseRepository setExerciseRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public TrainerController(ExerciseService exerciseService, ClientRequestService clientRequestService, ProgramService programService,
                             UserService userService, SetExerciseService setExerciseService, ProgramRepository programRepository,
                             SetExerciseRepository setExerciseRepository,
                             ExerciseRepository exerciseRepository) {
        this.exerciseService = exerciseService;
        this.clientRequestService = clientRequestService;
        this.programService = programService;
        this.userService = userService;
        this.setExerciseService = setExerciseService;
        this.programRepository = programRepository;
        this.setExerciseRepository = setExerciseRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @GetMapping()
    public String viewHomePage(Model model) {
        int userId = SecurityUtils.getCurrentUserId();
        String token = JwtUtil.generateToken(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("token", token);
        return "trainer/trainerIndex";
    }

    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @GetMapping("exerciseSuccess")
    public String exerciseSuccess() {
        return "exercise/exerciseSuccess";
    }

    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @GetMapping("addExercise")
    public String showAddExerciseForm(Model model) {
        model.addAttribute("exercise", new Exercise());
        return "exercise/addExerciseForm";
    }

    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @PostMapping("saveExercise")
    public String saveExercise(@ModelAttribute ExercisePayload exercisePayload, RedirectAttributes redirectAttributes) throws ParseException {
        boolean exerciseExists = exerciseService.existsByName(exercisePayload.getName());
        if (exerciseExists) {
            redirectAttributes.addFlashAttribute("error", "Exercise with the same name already exists");
            return "redirect:/trainer/addExercise";
        }

        exerciseService.addExercise(exercisePayload);
        return "redirect:/trainer/exerciseSuccess";
    }

    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @GetMapping("showAllExercises")
    public String showAllExercises(Model model) {
        List<Exercise> exercises = exerciseService.findAllExercisesOrderedByIdAsc();
        model.addAttribute("exercises", exercises);
        return "exercise/showAllExercises";
    }

    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @PostMapping("/deleteExercise/{id}")
    public String deleteExercise(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = exerciseService.deleteExerciseById(id);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("deleteSuccess", true);
        }
        return "redirect:/trainer/showAllExercises";
    }

    @GetMapping("/editExerciseForm/{id}")
    public String editExerciseForm(@PathVariable Integer id, Model model) {
        Exercise exercise = exerciseService.findById(id);
        model.addAttribute("exercise", exercise);
        return "exercise/editExercise";
    }
    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @PostMapping("/editExercise/{id}")
    public String editExercise(@PathVariable("id") Integer id, @ModelAttribute ExercisePayload exercisePayload, RedirectAttributes redirectAttributes) {
        exerciseService.editExercise(id, exercisePayload);
        redirectAttributes.addFlashAttribute("success", "Exercise updated successfully!");
        return "redirect:/trainer/showAllExercises";
    }

    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @GetMapping("/showAllRequests")
    public String showAllRequests(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var trainerName = authentication.getName();
        User trainer = userService.findByUsername(trainerName);

        List<ClientRequest> clientRequests = clientRequestService.getClientRequestsByTrainer(trainer);
        List<User> clients = userService.findAllClients();
        model.addAttribute("clientRequests", clientRequests);
        model.addAttribute("clients", clients);

        return "trainer/showAllClientRequests";
    }

    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @GetMapping("/writeProgram/{clientId}/{trainerId}")
    public String writeProgramForm(@PathVariable Integer clientId, @PathVariable Integer trainerId, Model model){
        User trainer = userService.findById(trainerId);
        User client = userService.findById(clientId);

        Program program = programRepository.findFirstByClientAndTrainerOrderByIdDesc(client, trainer);

        if (program == null) {
            program = programService.addProgram(trainer, client);
        }
        List<Exercise> exercises = exerciseService.findAllExercisesOrderedByIdAsc();
        model.addAttribute("exercises", exercises);
        model.addAttribute("program", program);

        return "trainer/writeProgramForm";
    }

    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @PostMapping("/addSetExercise")
    public String addSetExercise(@RequestParam("exerciseCount[]") List<Integer> exerciseCounts, @RequestParam("setCount[]") List<Integer> setCounts,
                                 @RequestParam("selectedExerciseId[]") List<Integer> exerciseId, @RequestParam Integer programId){
        int i = 0;
        for (Integer exerciseCount: exerciseCounts)
        {
            setExerciseService.addSetExercise(exerciseCount, setCounts.get(i), exerciseId.get(i), programId);
            i++;
        }
        return "redirect:/trainer";
    }
    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @GetMapping("/showAllPrograms")
    public String showAllPrograms(Model model){
        List<SetExercise> setExercises = setExerciseRepository.findAll();
        List<Program> programs = programRepository.findAll();
        List<Exercise> exercises = exerciseRepository.findAll();
        model.addAttribute("programs", programs);
        model.addAttribute("setExercises",setExercises);
        model.addAttribute("exercises",exercises);
        return "trainer/showAllPrograms";
    }
    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @PostMapping("/deleteProgram/{programId}")
    public String deleteProgram(@PathVariable Integer programId) {
        Optional<Program> programOptional = programService.findById(programId);

        if (programOptional.isPresent()) {
            Program program = programOptional.get();

            programService.delete(program);

            return "redirect:/trainer/showAllPrograms";
        }
        return "redirect:/trainer/";
    }
    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @DeleteMapping("/deleteClientRequest/{requestId}")
    public ResponseEntity<Map<String, String>> deleteRequest(@PathVariable Integer requestId) {
        ClientRequest clientRequest = clientRequestService.findById(requestId);
        if (clientRequest != null) {
            clientRequestService.delete(clientRequest);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Request deleted successfully!");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Request not found!"));
    }
}
