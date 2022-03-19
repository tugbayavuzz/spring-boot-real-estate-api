package com.realestate.model.entity;

import com.realestate.model.enums.AdvertStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Adverts")
public class AdvertEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Long packageId;
    private String title;
    private String description;
    private Double price;
    private Date date;
    private String address;

    @Enumerated(EnumType.STRING)
    private AdvertStatusType status;

}
