package com.quesito.security.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findCustomerByEmail(String email);
    Optional<Customer> findCustomerByCedula(String cedula);
}
