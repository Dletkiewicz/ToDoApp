package pl.dariusz.todoapp.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ToDoRepository extends JpaRepository<ToDoTask, Long> {

    @Modifying
    @Query("UPDATE ToDoTask t SET t.title = :title, t.done = :done WHERE t.id = :id")
    void updateTask(@Param("id") Long id, @Param("title") String title, @Param("done") boolean done);


}
