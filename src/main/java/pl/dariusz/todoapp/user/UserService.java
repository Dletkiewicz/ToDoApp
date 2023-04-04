package pl.dariusz.todoapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void registerNewUser(User user) {
        Optional<User> studentOptional = userRepository.findUserByUsername(user.getUsername());
        if(studentOptional.isPresent()){
            throw new IllegalStateException("Nickname is taken");
        }
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        boolean exists =  userRepository.existsById(userId);
        if(!exists){
            throw new IllegalStateException("User with id" + userId + "does not exists");
        }
        userRepository.deleteById(userId);
    }

}

