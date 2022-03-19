package com.realestate.model.mapper;

import com.realestate.model.dto.PackageDTO;
import com.realestate.model.entity.PackageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PackageMapper {
    PackageEntity dtoToEntity(PackageDTO dto);

    PackageDTO entityToDto(PackageEntity entity);
}