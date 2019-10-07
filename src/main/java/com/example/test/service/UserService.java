package com.example.test.service;

import com.example.test.model.User;
import com.example.test.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        return userRepository.saveAndFlush(user);
    }

    public void deleteUser(String login){
        userRepository.deleteById(login);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserByLogin(String login){
        return userRepository.findByLogin(login);
    }

    public int updateUser(String oldLogin, User user){
        return userRepository.updateUser(user.getLogin(),oldLogin,user.getDob(),user.getGender().getIntegerName(),user.getFullName());
    }
}
