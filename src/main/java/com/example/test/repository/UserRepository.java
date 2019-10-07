package com.example.test.repository;

import com.example.test.model.Gender;
import com.example.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByLogin(String login);

    @Modifying
    @Query(value = "update test.users SET login=:newLogin, dob=:newDob, gender=:newGender, " +
            "full_name=:newFullName where login=:oldLogin", nativeQuery = true)
    @Transactional
    int updateUser(@Param("newLogin") String newLogin, @Param("oldLogin") String oldLogin,
                    @Param("newDob") LocalDate newDob, @Param("newGender") int newGender,
                    @Param("newFullName") String newFullName);
}
