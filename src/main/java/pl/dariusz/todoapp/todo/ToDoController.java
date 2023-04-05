package pl.dariusz.todoapp.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ToDoController {

    private final ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("/index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("toDoTasks", toDoService.getTasks());
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create(){
        return new ModelAndView("create");
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modifyTask(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("modify");
        ToDoTask task = toDoService.getTaskById(id);
        modelAndView.addObject("task", task);
        return modelAndView;
    }

    @PostMapping("/modify/{id}")
    public ModelAndView updateTask(@PathVariable Long id, @ModelAttribute("task") ToDoTask task) {
        task.setId(id);
        toDoService.addNewTask(task);
        return new ModelAndView("redirect:/api/index");
    }

    @PostMapping("/newtask")
    public RedirectView addTask(@RequestParam String title, @RequestParam(required=false, defaultValue="false") boolean done) {
        long taskNum = toDoService.count() + 1;
        toDoService.addNewTask(new ToDoTask(taskNum, title, done));
        System.out.println(taskNum);
        return new RedirectView("/api/index");

    }

    @GetMapping("/delete/{taskId}")
    public RedirectView deleteTask(@PathVariable("taskId") Long taskId){
        toDoService.deleteTask(taskId);
        return new RedirectView("/api/index");

    }

    @PostMapping("/update/{taskId}")
    public RedirectView updateTask(@PathVariable("taskId") Long taskId, @RequestParam String title, @RequestParam(required = false) boolean done) {
        ToDoTask task = toDoService.getTasks().get(Math.toIntExact(taskId));
        task.setTitle(title);
        task.setDone(done);
        toDoService.addNewTask(task);
        return new RedirectView("/api/index");
    }


}
