package com.realestate.repository;

import com.realestate.model.entity.AdvertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertRepository extends JpaRepository<AdvertEntity, Long> {

    List<AdvertEntity> findAllByUserId(Long userId);

    @Query("SELECT a FROM AdvertEntity a WHERE a.status = 'IN_REVIEW'")
    List<AdvertEntity> getInReviewAdverts();

}
