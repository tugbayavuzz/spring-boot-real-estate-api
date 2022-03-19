package com.realestate.repository;

import com.realestate.model.entity.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<PackageEntity,Long> {

    List<PackageEntity> findAllByUserId(Long userId);
}
