package com.example.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "test")
public class User {

    @Id
    @Column(name = "login")
    @Length(min = 3, max = 10, message = "*Your login must have 3-10 characters")
    @NotEmpty(message = "Please, provide your login")
    private String login;

    @Column(name = "fullName")
    @Length(min = 3, max = 50, message = "*Your login must have 3-50 characters")
    @NotEmpty(message = "Please, provide your full name")
    private String fullName;

    @Column(name = "dob")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Please, provide your dob")
    @Past(message = "Please, provide your real dob")
    private LocalDate dob;

    @Column(name = "gender")
    @NotNull(message = "Please, select your gender")
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
}
