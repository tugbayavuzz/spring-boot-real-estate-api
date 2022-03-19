package com.realestate.model.dto;

import com.realestate.model.enums.AdvertStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertDTO {

    private Long id;
    private Long userId;
    private Long packageId;
    private String title;
    private String description;
    private Double price;
    private Date date;
    private String address;
    private AdvertStatusType status;

}