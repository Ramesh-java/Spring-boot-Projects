package com.example.skillsnap.repository;

import com.example.skillsnap.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Customer,Long> {
Optional<Customer> findCustomerByEmail(String email);

}
