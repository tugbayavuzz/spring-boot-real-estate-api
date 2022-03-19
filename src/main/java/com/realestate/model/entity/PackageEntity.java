package com.realestate.model.entity;

import com.realestate.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Packages")
public class PackageEntity {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private int productCount;
    private PaymentType paymentType;
    private LocalDate validityStartDate;
    private LocalDate validityEndDate;
}
