package com.realestate.service;

import com.realestate.model.dto.PackageDTO;
import com.realestate.model.response.BaseResponse;
import com.realestate.model.response.PackageResponse;

import java.util.List;

public interface PackageService {

    List<PackageDTO> findAllByUserId(Long userId);

    PackageResponse getById(Long id);

    PackageResponse createNewPackage(PackageDTO packageDTO);

    BaseResponse updateById(Long id);

    BaseResponse updateProductCount(Long id);
}
