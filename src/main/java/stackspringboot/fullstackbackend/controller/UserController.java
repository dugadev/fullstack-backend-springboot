package stackspringboot.fullstackbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stackspringboot.fullstackbackend.exception.UserNotFoundException;
import stackspringboot.fullstackbackend.model.User;
import stackspringboot.fullstackbackend.repository.UserRepository;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //Get all register
    @GetMapping("api/v1/users")
    List<User>getAllUsers(){
        return userRepository.findAll();
    }

    //Get register by Id
    @GetMapping("api/v1/user/{id}")
    User getUserbyId(@PathVariable Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
    }

    //Create new register
    @PostMapping("api/v1/user/create")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);

    }

    @PutMapping("api/v1/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id){

        return userRepository.findById(id)
        .map(user ->{
            user.setName(newUser.getName());
            user.setUsername(newUser.getUsername());
            user.setEmail(newUser.getEmail());
            user.setRol(newUser.getRol());

            return userRepository.save(user);
        }).orElseThrow(() ->new  UserNotFoundException(id));
    }

@DeleteMapping("api/v1/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "user with id " +id+ " has been delete success.";
}

}
