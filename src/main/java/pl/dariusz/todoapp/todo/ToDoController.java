package pl.dariusz.todoapp.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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

//
//    @PostMapping("/newtask")
//    public String addTask(@RequestParam ToDoTask toDoTask) {
//        toDoService.addNewTask(toDoTask);
//        return "redirect:/tasks";
//    }

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

    @PutMapping("/{taskId}")
    public void updateTask(@PathVariable Long taskId, @RequestBody ToDoTask toDoTask) {
        toDoService.updateTask(taskId, toDoTask.getTitle(), toDoTask.isDone());
    }

}
