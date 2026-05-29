package com.scrummanager.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_keys", indexes = {
        @Index(name = "idx_api_keys_user", columnList = "user_cod"),
        @Index(name = "idx_api_keys_hash", columnList = "key_hash", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_cod")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_cod", nullable = false)
    private User owner;

    @Column(name = "key_name", nullable = false, length = 100)
    private String name;

    /** SHA-256 hex digest of the raw key – never stored in plain text. */
    @Column(name = "key_hash", nullable = false, unique = true, length = 64)
    private String keyHash;

    /** First 8 chars of the raw key, safe to display. */
    @Column(name = "key_prefix", nullable = false, length = 8)
    private String keyPrefix;

    @Builder.Default
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}
