package com.realestate.service;

import com.realestate.model.dto.FavouriteAdvertsDTO;
import com.realestate.model.response.FavouriteAdvertsResponse;

public interface FavouriteAdvertsService {

    FavouriteAdvertsResponse findAllByUserId(Long userId);

    FavouriteAdvertsDTO createNewFavouriteAdvert(FavouriteAdvertsDTO favouriteAdvertsDTO);

}
