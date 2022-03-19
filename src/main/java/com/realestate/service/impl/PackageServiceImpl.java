package com.realestate.service.impl;

import com.realestate.exception.BadRequestException;
import com.realestate.exception.DataNotFoundException;
import com.realestate.exception.RequiredFieldException;
import com.realestate.model.dto.PackageDTO;
import com.realestate.model.entity.PackageEntity;
import com.realestate.model.enums.PaymentType;
import com.realestate.model.mapper.PackageMapper;
import com.realestate.model.response.BaseResponse;
import com.realestate.model.response.MessageResponse;
import com.realestate.model.response.PackageResponse;
import com.realestate.repository.PackageRepository;
import com.realestate.service.PackageService;
import com.realestate.service.PaymentService;
import com.realestate.service.async.PackageAsyncService;
import com.realestate.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;
    private final PackageAsyncService packageAsyncService;
    private final PaymentService paymentService;
    private final PackageMapper packageMapper = Mappers.getMapper(PackageMapper.class);

    @Override
    public List<PackageDTO> findAllByUserId(Long userId) {
        if (Objects.isNull(userId)) {
            throw RequiredFieldException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.ID_IS_REQUIRED)).build();
        }

        List<PackageEntity> allByUserId = packageRepository.findAllByUserId(userId);
        if (allByUserId == null || allByUserId.isEmpty()) {
            throw DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build();
        }

        List<PackageDTO> packageDTOList = new ArrayList<>();
        for (PackageEntity packageEntity : allByUserId) {
            packageDTOList.add(packageMapper.entityToDto(packageEntity));
        }

        return packageDTOList;
    }

    @Override
    public PackageResponse getById(Long id) {
        if (Objects.isNull(id)) {
            throw RequiredFieldException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.ID_IS_REQUIRED)).build();
        }

        PackageDTO packageDTO = Optional.of(packageRepository.findById(id))
                .get()
                .map(packageMapper::entityToDto)
                .orElseThrow(() -> DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build());

        PackageResponse packageResponse = PackageResponse.builder().packageDTO(packageDTO).build();
        packageResponse.addSuccessMessage();
        return packageResponse;
    }

    @Override
    public PackageResponse createNewPackage(PackageDTO packageDTO) {
        PackageEntity packageEntity = packageMapper.dtoToEntity(packageDTO);
        packageEntity.setValidityStartDate(LocalDate.now());
        packageEntity.setValidityEndDate(LocalDate.now().plusDays(30));
        packageEntity.setProductCount(10);
        packageEntity.setPaymentType(PaymentType.NOT_OK);
        packageDTO = Optional.of(packageRepository.save(packageEntity)).map(packageMapper::entityToDto).orElse(null);

        BaseResponse baseResponse = paymentService.doPayment();
        if (baseResponse.getMessage().getCode().equals("E0000")) {
            packageAsyncService.updatePackagePaymentType(packageDTO.getId());
        }

        PackageResponse packageResponse = PackageResponse.builder().packageDTO(packageDTO).build();
        packageResponse.addSuccessMessage();
        return packageResponse;
    }

    @Override
    public BaseResponse updateById(Long id) {
        if (Objects.isNull(id)) {
            throw RequiredFieldException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.ID_IS_REQUIRED)).build();
        }

        Optional.ofNullable(packageRepository.findById(id)
                        .orElseThrow(() -> DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build()))
                .ifPresent(packageEntity -> {
                    if (packageEntity.getValidityEndDate().isBefore(LocalDate.now())) {
                        throw BadRequestException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.INVALID_PACKAGE_ERROR)).build();
                    }

                    packageEntity.setValidityEndDate(packageEntity.getValidityEndDate().plusDays(30));
                    packageEntity.setProductCount(packageEntity.getProductCount() + 10);
                    packageRepository.save(packageEntity);
                });

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.addSuccessMessage();
        return baseResponse;
    }

    @Override
    public BaseResponse updateProductCount(Long id) {
        if (Objects.isNull(id)) {
            throw RequiredFieldException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.ID_IS_REQUIRED)).build();
        }

        Optional.ofNullable(packageRepository.findById(id)
                        .orElseThrow(() -> DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build()))
                .ifPresent(packageEntity -> {
                    packageEntity.setProductCount(packageEntity.getProductCount() - 1);
                    packageRepository.save(packageEntity);
                });

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.addSuccessMessage();
        return baseResponse;
    }
}
