package com.ftrainer.ftrainer.controllers;

import com.ftrainer.ftrainer.dto.ExercisePayload;
import com.ftrainer.ftrainer.entities.*;
import com.ftrainer.ftrainer.repositories.ExerciseRepository;
import com.ftrainer.ftrainer.repositories.ProgramRepository;
import com.ftrainer.ftrainer.repositories.SetExerciseRepository;
import com.ftrainer.ftrainer.repositories.UserRepository;
import com.ftrainer.ftrainer.services.ClientRequestService;
import com.ftrainer.ftrainer.services.ExerciseService;
import com.ftrainer.ftrainer.services.ProgramService;
import com.ftrainer.ftrainer.services.SetExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/trainer")
public class TrainerController {
    private final ExerciseService exerciseService;

    private final ClientRequestService clientRequestService;
    private final UserRepository userRepository;

    private final ProgramService programService;

    private final SetExerciseService setExerciseService;
    private final ProgramRepository programRepository;
    private final SetExerciseRepository setExerciseRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public TrainerController(ExerciseService exerciseService, ClientRequestService clientRequestService,
                             UserRepository userRepository, ProgramService programService,
                             SetExerciseService setExerciseService, ProgramRepository programRepository,
                             SetExerciseRepository setExerciseRepository,
                             ExerciseRepository exerciseRepository) {
        this.exerciseService = exerciseService;
        this.clientRequestService = clientRequestService;
        this.userRepository = userRepository;
        this.programService = programService;
        this.setExerciseService = setExerciseService;
        this.programRepository = programRepository;
        this.setExerciseRepository = setExerciseRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @GetMapping()
    public String viewHomePage() {
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
        User trainer = userRepository.findByUsername(trainerName);

        List<ClientRequest> clientRequests = clientRequestService.getClientRequestsByTrainer(trainer);
        model.addAttribute("clientRequests", clientRequests);

        return "trainer/showAllClientRequests";
    }

    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @GetMapping("/writeProgram/{clientId}/{trainerId}")
    public String writeProgramForm(@PathVariable Integer clientId, @PathVariable Integer trainerId, Model model){
        User trainer = userRepository.findById(trainerId).orElse(null);
        User client = userRepository.findById(clientId).orElse(null);

        Program program = programRepository.findFirstByClientAndTrainerOrderByIdDesc(client, trainer);

        if (program == null) {
            program = new Program();
            program.setTrainer(trainer);
            program.setClient(client);
            programService.addProgram(program);
        }

        List<Exercise> exercises = exerciseService.findAllExercisesOrderedByIdAsc();
        model.addAttribute("exercises", exercises);
        model.addAttribute("program", program);

        return "trainer/writeProgramForm";
    }

    @PreAuthorize("hasAnyAuthority('TRAINER')")
    @PostMapping("/addSetExercise")
    public String addSetExercise(@RequestParam Integer selectedExerciseId, @RequestParam Integer setCount, @RequestParam Integer exerciseCount, @RequestParam Integer programId){
        SetExercise setExercise = new SetExercise();
        setExercise.setExerciseId(selectedExerciseId);
        setExercise.setExerciseCount(exerciseCount);
        setExercise.setSetCount(setCount);
        Program program = programRepository.findById(programId).orElse(null);
        setExercise.setProgram(program);
        setExerciseService.addSetExercise(setExercise);
        return "redirect:/trainer/writeProgram/" + program.getClient().getId() + "/" + program.getTrainer().getId();
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
        Optional<Program> programOptional = programRepository.findById(programId);

        if (programOptional.isPresent()) {
            Program program = programOptional.get();

            programRepository.delete(program);

            return "redirect:/trainer/showAllPrograms";
        }
        return "redirect:/trainer/";
    }
}
