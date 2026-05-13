package com.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Bean Validation
    @NotBlank(message = "First name required")
    @Size(min = 1, max = 100)
    private String firstName;

    @NotBlank(message = "Last name required")
    @Size(min = 1, max = 100)
    private String lastName;

    @NotNull(message = "Email required")
    @Email(message = "Must be a valid email")
    @Column(nullable = false, unique = true)
    private String email;

    // Set automatically on INSERT
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties({"customer", "hibernateLazyInitializer"})
    @Builder.Default // Keeps this default value when using Lombok builder
    private List<Account> accounts = new ArrayList<>();
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // BCrypt hash — never plaintext

    @Builder.Default // Keeps the default role when Customer is created through Lombok builder
    private String role = "ROLE_USER"; // e.g. ROLE_USER or ROLE_ADMIN
    @Builder.Default // Keeps new builder-created customers enabled unless explicitly disabled
    private boolean enabled = true;

    @Builder.Default // Keeps failed login count initialized when using Lombok builder
    private int failedAttempts = 0;
    @Builder.Default // Keeps new builder-created customers unlocked by default
    private boolean accountLocked = false;
    private LocalDateTime lockTime;
}
