package com.quesito.security.auth;

import com.quesito.security.config.JwtService;
import com.quesito.security.customer.Customer;
import com.quesito.security.customer.CustomerRepository;
import com.quesito.security.customer.Role;
import com.quesito.security.exceptions.CustomerAlreadyRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Customer user = Customer.builder()
                .email(request.getEmail())
                .name(request.getName())
                .cedula(request.getCedula())
                .rol(Role.USER)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        customerRepository.findCustomerByEmail(request.getEmail())
                .ifPresent(customer -> {
                    throw new CustomerAlreadyRegister(String.format("Email %s is already register", customer.getEmail()));
                });
        customerRepository.findCustomerByCedula(request.getCedula())
                .ifPresent(customer -> {
                    throw new CustomerAlreadyRegister(String.format("Cedula %s is already register", customer.getCedula()));
                });
        customerRepository.save(user);
        String token = jwtService.generateToken(new HashMap<>(), user);
        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager
                .authenticate(UsernamePasswordAuthenticationToken.unauthenticated(request.getEmail(), request.getPassword()));
        var user = customerRepository.findCustomerByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(new HashMap<>(), user);
        return AuthenticationResponse.builder().token(token).build();
    }


}
