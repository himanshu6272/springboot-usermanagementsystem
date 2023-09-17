package com.springboot.UserManagementSystem.dao;

import com.springboot.UserManagementSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    public boolean userExist(@Param("email") String email);

    @Query("select u from User u where u.email = :email")
    public User getUserByEmail(@Param("email") String email);
}
