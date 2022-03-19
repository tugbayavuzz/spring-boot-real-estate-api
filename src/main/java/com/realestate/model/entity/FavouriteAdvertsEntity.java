package com.realestate.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FavouriteAdverts")
public class FavouriteAdvertsEntity {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long advertId;

}
