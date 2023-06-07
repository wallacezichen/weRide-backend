package com.weride.model;

import com.weride.model.enums.CarBrand;
import com.weride.model.enums.Transmission;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.awt.*;
import java.time.Year;

public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand", nullable = false)
    private CarBrand brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "made_year", nullable = false)
    private Year madeYear;

    @Column(name = "color", nullable = false)
    private Color color;

    @Column(name = "transmission", nullable = false)
    private Transmission transmission;

    @Column(name = "license_plate", nullable = false)
    @NotBlank(message = "License Plate should not be blank")
    @Pattern(regexp = "^[A-Za-z0-9]{1,10}$", message = "License Plate should be in correct format")
    private String licensePlate;

    @Column(name = "verified_status", nullable = false)
    private Boolean verifiedStatus;


}
