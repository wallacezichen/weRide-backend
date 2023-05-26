package com.weride.model;

import java.sql.Driver;
import java.util.Date;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id")
    private Driver driverId;

    @Column(name = "is_rated", nullable = false)
    private Boolean isRated;

    @Column(name = "pick_up_time", nullable = false)
    private Date pickUpTime;

    @Column(name = "drop_off_time", nullable = false)
    private Date dropOffTime;

    @Column(name = "pick_up_latitude", nullable = false)
    private double pickUpLatitude;

    @Column(name = "pick_up_longitude", nullable = false)
    private double pickUpLongitude;

    @Column(name = "drop_off_latitude", nullable = false)
    private double dropOffLatitude;

    @Column(name = "drop_off_longitude", nullable = false)
    private double dropOffLongitude;


    @Column(name = "trip_fare", nullable = false)
    private Double tripFare;
    @Column(name = "trip_distance", nullable = false)
    private Double tripDistance;
    @Column(name = "trip_duration", nullable = false)
    private Double tripDuration;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rider_id")
    private User riderId;

    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid;







}
