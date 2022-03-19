package com.realestate.model.response;

import com.realestate.model.dto.AdvertDTO;
import lombok.Data;

import java.util.List;

@Data
public class FavouriteAdvertsResponse extends BaseResponse {

    private List<AdvertDTO> favouriteAdverts;

}