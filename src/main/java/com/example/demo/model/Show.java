package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shows")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate showDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private double price;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;
}
