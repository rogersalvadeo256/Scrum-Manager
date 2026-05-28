package com.scrummanager.domain.entity;

import com.scrummanager.domain.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_cod")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Always stored as BCrypt hash – never plain text.
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "security_question", nullable = false)
    private String securityQuestion;

    /**
     * Stored as BCrypt hash for extra protection.
     */
    @Column(name = "security_answer_hash", nullable = false)
    private String securityAnswerHash;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private UserProfile profile;
}
