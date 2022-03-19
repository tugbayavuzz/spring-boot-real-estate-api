package com.realestate.service.impl;

import com.realestate.exception.DataNotFoundException;
import com.realestate.exception.RequiredFieldException;
import com.realestate.model.dto.AdvertDTO;
import com.realestate.model.dto.FavouriteAdvertsDTO;
import com.realestate.model.response.FavouriteAdvertsResponse;
import com.realestate.model.entity.FavouriteAdvertsEntity;
import com.realestate.model.response.MessageResponse;
import com.realestate.repository.FavouriteAdvertsRepository;
import com.realestate.service.AdvertService;
import com.realestate.service.FavouriteAdvertsService;
import com.realestate.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FavouriteAdvertsServiceImpl implements FavouriteAdvertsService {

    private final FavouriteAdvertsRepository favouriteAdvertsRepository;
    private final AdvertService advertService;

    @Override
    public FavouriteAdvertsResponse findAllByUserId(Long userId) {
        if (Objects.isNull(userId)) {
            throw RequiredFieldException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.ID_IS_REQUIRED)).build();
        }

        List<FavouriteAdvertsEntity> allByUserId = favouriteAdvertsRepository.findAllByUserId(userId);
        if (allByUserId == null || allByUserId.isEmpty()) {
            throw DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build();
        }

        List<AdvertDTO> favouriteAdvertsDTOS = new ArrayList<>();
        for (FavouriteAdvertsEntity favouriteAdvertsEntity : allByUserId) {
            favouriteAdvertsDTOS.add(advertService.getById(favouriteAdvertsEntity.getAdvertId()).getAdvert());
        }

        FavouriteAdvertsResponse favouriteAdvertsResponse = new FavouriteAdvertsResponse();
        favouriteAdvertsResponse.setFavouriteAdverts(favouriteAdvertsDTOS);
        favouriteAdvertsResponse.addSuccessMessage();
        return favouriteAdvertsResponse;
    }

    @Override
    public FavouriteAdvertsDTO createNewFavouriteAdvert(FavouriteAdvertsDTO favouriteAdvertsDTO) {
        FavouriteAdvertsEntity favouriteAdvertsEntity = new FavouriteAdvertsEntity();
        favouriteAdvertsEntity.setUserId(favouriteAdvertsDTO.getUserId());
        favouriteAdvertsEntity.setAdvertId(favouriteAdvertsDTO.getAdvertId());
        favouriteAdvertsEntity = favouriteAdvertsRepository.save(favouriteAdvertsEntity);

        favouriteAdvertsDTO.setId(favouriteAdvertsEntity.getId());
        return favouriteAdvertsDTO;
    }
}
