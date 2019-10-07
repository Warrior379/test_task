package com.example.test.controller;

import com.example.test.model.User;
import com.example.test.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public ModelAndView startPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("users",userService.getAllUsers());
        modelAndView.setViewName("start");
        return modelAndView;
    }

    @GetMapping(value = "/add")
    public ModelAndView addPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",new User());
        modelAndView.setViewName("addUser");
        return modelAndView;
    }

    @PostMapping(value = "/addUser")
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();

        if(userService.getUserByLogin(user.getLogin()).orElse(null) != null){
            bindingResult
                    .rejectValue("login","error.user",
                            "There is already a user registered with the login provided.");
        }

        if(bindingResult.hasErrors()){
            modelAndView.addObject(user);
            modelAndView.addObject("message", "User hasn't been created successfully");
            modelAndView.setViewName("addUser");
        } else{
            userService.saveUser(user);
            modelAndView = startPage();
        }

        return modelAndView;
    }

    @GetMapping(value = "/change/{login}")
    public ModelAndView changePage(@PathVariable String login){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",userService.getUserByLogin(login));
        modelAndView.addObject("login",login);
        modelAndView.setViewName("changeUser");
        return modelAndView;
    }

    @PutMapping(value = "/change/{login}")
    public ModelAndView changeUser(@PathVariable("login") String login, @Valid User user, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();

        if((!user.getLogin().equals(login)) && (userService.getUserByLogin(user.getLogin()).orElse(null) != null)){
            bindingResult
                    .rejectValue("login","error.user",
                            "There is already a user registered with the login provided.");
        }

        if(bindingResult.hasErrors()){
            modelAndView.addObject(user);
            modelAndView.addObject("login",login);
            modelAndView.addObject("message", "User hasn't been changed successfully");
            modelAndView.setViewName("changeUser");
        } else{
            if(!user.getLogin().equals(login)){
                userService.updateUser(login,user);
            } else {
                userService.saveUser(user);
            }
            modelAndView = startPage();
        }

        return modelAndView;
    }

    @DeleteMapping(value = "/delete/{login}")
    public ModelAndView deleteUser(@PathVariable("login") String login){
        userService.deleteUser(login);
        return startPage();
    }
}
