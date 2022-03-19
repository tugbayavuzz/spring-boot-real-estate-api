package com.realestate.repository;

import com.realestate.model.entity.FavouriteAdvertsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteAdvertsRepository extends JpaRepository<FavouriteAdvertsEntity, Long> {
    List<FavouriteAdvertsEntity> findAllByUserId(Long userId);
}
