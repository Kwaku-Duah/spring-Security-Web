package com.management.farm.Model.LayerActivities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
public class LayerActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String medication;
    private String temperatureCondition;
    private String feed;
    private String water;
    private double weight;
    private String season;
    private String comments;

   
}

