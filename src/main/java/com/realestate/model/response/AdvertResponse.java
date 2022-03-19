package com.realestate.model.response;

import com.realestate.model.dto.AdvertDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdvertResponse extends BaseResponse {
    private AdvertDTO advert;
}
