package pl.dariusz.todoapp.todo;


import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ToDoTask {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Nonnull
    private String title;
    private boolean done;

    ToDoTask(String title, boolean done){
        this.title = title;
        this.done = done;
    }

}
