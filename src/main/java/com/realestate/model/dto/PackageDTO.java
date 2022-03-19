package com.realestate.model.dto;

import com.realestate.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackageDTO {

    private Long id;
    private Long userId;
    private String name;
    private String description;
    private int productCount;
    private PaymentType paymentType;
    private LocalDate validityStartDate;
    private LocalDate validityEndDate;

}
