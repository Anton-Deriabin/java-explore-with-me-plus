package ru.practicum.request;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "requests", schema = "public")
@Getter
@Setter
@ToString
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    LocalDateTime created;

    @Column(name = "event_id", nullable = false)
    Long event;

    @Column(name = "requester_id", nullable = false)
    Long requester;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    RequestStatus status;
}