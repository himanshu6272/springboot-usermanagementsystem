package com.springboot.UserManagementSystem.dao;

import com.springboot.UserManagementSystem.models.Address;
import com.springboot.UserManagementSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("select a from Address a where a.user.id = :id")
    public List<Address> getAddressByUserId(@Param("id") int id);

}
