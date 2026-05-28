package com.scrummanager.domain.model;

import com.scrummanager.domain.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "friendship")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "frq_id")
    private Long id;

    @Column(name = "frq_requested_by_id", nullable = false)
    private Long requestedById;

    @Column(name = "frq_receiver_id", nullable = false)
    private Long receiverId;

    @Enumerated(EnumType.STRING)
    @Column(name = "frq_status", nullable = false)
    private RequestStatus status;

    @Column(name = "frq_sent_date", nullable = false)
    private LocalDate sentDate;
}
