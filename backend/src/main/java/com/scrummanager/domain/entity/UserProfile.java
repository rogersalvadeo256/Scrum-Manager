package com.scrummanager.domain.entity;

import com.scrummanager.domain.enums.Availability;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prof_cod")
    private Long id;

    @Column(name = "prof_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "prof_availability", nullable = false)
    private Availability availability;

    @Lob
    @Column(name = "prof_photo", columnDefinition = "BYTEA")
    private byte[] photo;
}
