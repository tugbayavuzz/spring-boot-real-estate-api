package com.realestate.model.response;

import com.realestate.model.dto.PackageDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PackageResponse extends BaseResponse{

    private PackageDTO packageDTO;
}
