package ru.practicum.ewm.category.model;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 140, nullable = false)
    private String name;
}