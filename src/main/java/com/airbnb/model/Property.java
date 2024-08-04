package com.airbnb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String address;

    private BigDecimal pricePerNight;

    private int numberOfBedrooms;

    private int numberOfBathrooms;

    private boolean isAvailable;

    private boolean drinkAllowed;

    private boolean petAllowed;

    private int maxCheckoutTimeInNights;

    private BigDecimal extraCharges;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User owner;
}

