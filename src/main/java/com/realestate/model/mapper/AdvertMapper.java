package com.realestate.model.mapper;

import com.realestate.model.dto.AdvertDTO;
import com.realestate.model.entity.AdvertEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdvertMapper {
    AdvertEntity dtoToEntity(AdvertDTO dto);

    AdvertDTO entityToDto(AdvertEntity entity);
}
