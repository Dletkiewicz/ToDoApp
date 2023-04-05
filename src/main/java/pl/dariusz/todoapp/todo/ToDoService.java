package pl.dariusz.todoapp.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService{

    private final ToDoRepository toDoRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public List<ToDoTask> getTasks(){
        return toDoRepository.findAll();
    }

    public Long count(){
        return toDoRepository.count();
    }

    public void addNewTask(ToDoTask toDoTask){
        toDoRepository.save(toDoTask);
    }

    public void addNewTask(String title, boolean done){
        ToDoTask toDoTask = new ToDoTask(title, done);
        toDoRepository.save(toDoTask);
    }

    public void deleteTask(Long taskId){
        boolean exists =  toDoRepository.existsById(taskId);
        if(!exists){
            throw new IllegalStateException("Task with id" + taskId + "does not exists");
        }
        toDoRepository.deleteById(taskId);
    }

    public ToDoTask getTaskById(Long id) {
        return toDoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
    }

    public void updateTask(Long id, String title, boolean done) {
        ToDoTask taskToUpdate = toDoRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Task with id " + id + " does not exist"));

        taskToUpdate.setTitle(title);
        taskToUpdate.setDone(done);

        toDoRepository.save(taskToUpdate);
    }

}
