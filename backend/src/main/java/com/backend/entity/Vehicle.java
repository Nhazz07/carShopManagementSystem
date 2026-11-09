package com.backend.entity;

import com.backend.entity.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "vehicles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "vin"),
                @UniqueConstraint(columnNames = "stock_code")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Vehicle identification
    @Column(name = "stock_code", nullable = false, unique = true)
    private String stockCode;

    @Column(unique = true)
    private String vin;

    // Reference to the car's variant
    @Column(name = "variant_id", nullable = false)
    private Long variantId;

    // Vehicle specifications
    @Column(nullable = false)
    private Integer manufacturingYear;

    private Integer mileage;

    private String color;

    private String condition;

    // Pricing
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal sellingPrice;

    // Deposit configured by admin
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal reservationDepositAmount;

    // Vehicle availability
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status;

    // Publication visibility
    @Builder.Default
    @Column(nullable = false)
    private Boolean published = false;

    // Vehicle images
    @Builder.Default
    @OneToMany(
            mappedBy = "vehicle",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<VehicleImage> images = new ArrayList<>();

    // Timestamps
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}