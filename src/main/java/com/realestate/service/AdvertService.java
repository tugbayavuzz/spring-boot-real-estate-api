package com.realestate.service;

import com.realestate.model.dto.AdvertDTO;
import com.realestate.model.response.AdvertResponse;
import com.realestate.model.response.BaseResponse;

import java.util.List;

public interface AdvertService {

    AdvertResponse getById(Long id);

    List<AdvertDTO> getAllByUserId(Long userId);

    AdvertResponse createNewAdvert(AdvertDTO advertDTO);

    AdvertResponse updateById(AdvertDTO advertDTO);

    BaseResponse setActiveStatusById(Long id);

    BaseResponse setPassiveStatusById(Long id);

    BaseResponse deleteById(Long id);

}
