package com.bank.service;


import com.bank.model.Customer;
import com.bank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Customer customer = customerRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username));
// Auto-unlock after 30 minutes
        if (customer.isAccountLocked() && customer.getLockTime() != null) {
            if (customer.getLockTime().plusMinutes(30).isBefore(LocalDateTime.now())) {
                customer.setAccountLocked(false);
                customer.setFailedAttempts(0);
                customerRepository.save(customer);
            }
        }
        return new User(
                customer.getUsername(),
                customer.getPassword(),
                customer.isEnabled(),
                true, true,
                !customer.isAccountLocked(), // false = account is locked
                List.of(new SimpleGrantedAuthority(customer.getRole()))
        );
    }
}
