package com.bank.service;

import com.bank.model.Customer;
import com.bank.model.VerificationToken;
import com.bank.repository.CustomerRepository;
import com.bank.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final CustomerRepository customerRepo;
    private final VerificationTokenRepository tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Transactional
    public void register(Customer customer) {
// 1. Hash the password
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setEnabled(false); // must verify email first
        customer.setRole("ROLE_USER");
        customerRepo.save(customer);
// 2. Generate token
        String token = UUID.randomUUID().toString();
        VerificationToken vt = VerificationToken.builder()
                .token(token)
                .customer(customer)
                .expiresAt(LocalDateTime.now().plusHours(24))
                .build();
        tokenRepo.save(vt);
// 3. Send email
        sendVerificationEmail(customer.getEmail(), token);
    }
    public void verify(String token) {
        VerificationToken vt = tokenRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (vt.isUsed()) throw new RuntimeException("Token already used");
        if (vt.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Token expired");
        vt.getCustomer().setEnabled(true);
        vt.setUsed(true);
        customerRepo.save(vt.getCustomer());
    }
    private void sendVerificationEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verify your JD Bank account");
        message.setText("Click to verify:\n\n"
                + "http://localhost:8081/verify?token=" + token);
        mailSender.send(message);
    }
}
