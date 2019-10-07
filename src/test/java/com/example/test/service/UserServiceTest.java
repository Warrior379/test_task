package com.example.test.service;

import com.example.test.model.Gender;
import com.example.test.model.User;
import com.example.test.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;
    private User user1, user2;

    @Before
    public void setUp(){
        initMocks(this);
        userService = new UserService(userRepository);
        user1 = User.builder()
                .login("Warrior379")
                .gender(Gender.MALE)
                .fullName("Sergey Melaschenko")
                .dob(LocalDate.of(2000,7,18))
                .build();

        user2 = User.builder()
                .login("Login231")
                .gender(Gender.FEMALE)
                .fullName("Sofia Melaschenko")
                .dob(LocalDate.of(2000,2,11))
                .build();

        Mockito.when(userRepository.findByLogin(any(String.class))).thenReturn(Optional.of(user1)).thenReturn(null);
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user1,user2));
        Mockito.when(userRepository.saveAndFlush(any(User.class))).thenReturn(user1);
        Mockito.doNothing().when(userRepository).deleteById(any());
        Mockito.when(userRepository.updateUser(any(String.class),any(String.class),
                any(LocalDate.class),any(Integer.class),any(String.class))).thenReturn(1);

    }

    @Test
    public void saveUser() {
        String login = "Warrior379";
        User user = userService.saveUser(user1);
        assertEquals(login,user.getLogin());
    }

    @Test
    public void deleteUser() {
        userService.deleteUser(userService.getUserByLogin("Warrior379").orElse(null).getLogin());
        assertEquals(null,userService.getUserByLogin("Warrior379"));
    }

    @Test
    public void getAllUsers() {
        List<User> users= List.of(user1,user2);
        userService.saveUser(user1);
        userService.saveUser(user2);
        assertEquals(users,userService.getAllUsers());
    }

    @Test
    public void getUserByLogin() {
        String login = "Warrior379";
        userService.saveUser(user1);
        assertEquals(user1,userService.getUserByLogin(login).orElse(null));
    }

    @Test
    public void updateUser() {
        String login = user1.getLogin();
        user1.setLogin("New Login");
        assertEquals(1,userService.updateUser(login,user1));
    }
}