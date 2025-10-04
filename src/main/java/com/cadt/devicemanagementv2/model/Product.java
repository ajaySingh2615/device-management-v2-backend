package com.cadt.devicemanagementv2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product extends BaseEntity {

    @Column(nullable = false, unique = true, length = 64)
    private String sku;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(nullable = false, length = 120)
    private String model;

    private String cpu;

    @Column(nullable = false)
    private Integer ramGb = 8;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private StorageType storageType = StorageType.SSD;

    @Column(nullable = false)
    private Integer storageGb = 256;

    private Double screenSize;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private Grade grade = Grade.A;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, length = 8)
    private String currency = "INR";

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer quantity = 0;

    @Column(nullable = false)
    private Integer warrantyMonths = 3;

    private String primaryImageUrl;

    @Column(length = 500)
    private String conditionNotes;

    @Column(columnDefinition = "json")
    private String specsJson;

    public enum Grade {A, B, C,}

    public enum StorageType {HDD, SSD, NVME}
}
