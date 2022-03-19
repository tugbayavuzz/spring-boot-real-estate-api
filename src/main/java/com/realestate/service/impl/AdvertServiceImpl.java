package com.realestate.service.impl;

import com.realestate.exception.BadRequestException;
import com.realestate.exception.DataNotFoundException;
import com.realestate.exception.RequiredFieldException;
import com.realestate.model.dto.AdvertDTO;
import com.realestate.model.dto.PackageDTO;
import com.realestate.model.entity.AdvertEntity;
import com.realestate.model.enums.AdvertStatusType;
import com.realestate.model.enums.PaymentType;
import com.realestate.model.mapper.AdvertMapper;
import com.realestate.model.response.AdvertResponse;
import com.realestate.model.response.BaseResponse;
import com.realestate.model.response.MessageResponse;
import com.realestate.model.response.UserResponse;
import com.realestate.repository.AdvertRepository;
import com.realestate.service.AdvertService;
import com.realestate.service.PackageService;
import com.realestate.service.UserService;
import com.realestate.service.async.AdvertAsyncService;
import com.realestate.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {

    private final AdvertRepository advertRepository;
    private final UserService userService;
    private final AdvertMapper advertMapper = Mappers.getMapper(AdvertMapper.class);
    private final AdvertAsyncService advertAsyncService;
    private final PackageService packageService;

    @Override
    public AdvertResponse getById(Long id) {
        if (Objects.isNull(id)) {
            throw RequiredFieldException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.ID_IS_REQUIRED)).build();
        }

        AdvertDTO advertDTO = Optional.of(advertRepository.findById(id))
                .get()
                .map(advertMapper::entityToDto)
                .orElseThrow(() -> DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build());

        AdvertResponse advertResponse = AdvertResponse.builder().advert(advertDTO).build();
        advertResponse.addSuccessMessage();
        return advertResponse;
    }

    @Override
    public List<AdvertDTO> getAllByUserId(Long userId) {
        if (Objects.isNull(userId)) {
            throw RequiredFieldException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.ID_IS_REQUIRED)).build();
        }

        return Optional.of(advertRepository.findAllByUserId(userId)
                        .stream()
                        .map(advertMapper::entityToDto)
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    @Override
    public AdvertResponse createNewAdvert(AdvertDTO advertDTO) {
        UserResponse userResponse = userService.getById(advertDTO.getUserId());
        if (userResponse.getUser() == null) {
            throw DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build();
        }

        List<PackageDTO> packageDTOList = packageService.findAllByUserId(advertDTO.getUserId());
        if (packageDTOList == null || packageDTOList.isEmpty()) {
            throw DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build();
        }

        PackageDTO availablePackage = new PackageDTO();
        for (PackageDTO packageDTO : packageDTOList) {
            if ((packageDTO.getValidityEndDate().isEqual(LocalDate.now())) || (packageDTO.getValidityEndDate().isAfter(LocalDate.now())) && packageDTO.getProductCount() > 0 && packageDTO.getPaymentType().equals(PaymentType.OK)) {
                //gecerlilik tarihi dolmamıs ve ilan yayınlama hakkı bitmemiş paket secildi
                availablePackage = packageDTO;
                break;
            }
        }

        if (availablePackage == null) {
            throw DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build();
        }

        AdvertEntity advertEntity = advertMapper.dtoToEntity(advertDTO);
        advertEntity.setStatus(AdvertStatusType.IN_REVIEW); //default
        advertEntity.setPackageId(availablePackage.getId()); // set package
        advertDTO = Optional.of(advertRepository.save(advertEntity)).map(advertMapper::entityToDto).orElse(null);

        advertAsyncService.updateAdvertStatus();

        packageService.updateProductCount(availablePackage.getId());

        AdvertResponse advertResponse = AdvertResponse.builder().advert(advertDTO).build();
        advertResponse.addSuccessMessage();
        return advertResponse;
    }

    @Override
    public AdvertResponse updateById(AdvertDTO advertDTO) {
        if (Objects.isNull(advertDTO.getId())) {
            throw RequiredFieldException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.ID_IS_REQUIRED)).build();
        }

        Optional.ofNullable(advertRepository.findById(advertDTO.getId())
                        .orElseThrow(() -> DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build()))
                .ifPresent(advertEntity -> {
                    advertEntity.setDescription(advertDTO.getDescription());
                    advertEntity.setPrice(advertDTO.getPrice());
                    advertEntity.setTitle(advertDTO.getTitle());
                    advertEntity.setAddress(advertDTO.getAddress());
                    advertEntity.setDate(advertDTO.getDate());
                    advertRepository.save(advertEntity);
                });

        AdvertResponse advertResponse = AdvertResponse.builder().advert(advertDTO).build();
        advertResponse.addSuccessMessage();
        return advertResponse;
    }

    @Override
    public BaseResponse setActiveStatusById(Long id) {
        if (Objects.isNull(id)) {
            throw RequiredFieldException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.ID_IS_REQUIRED)).build();
        }

        Optional.ofNullable(advertRepository.findById(id)
                        .orElseThrow(() -> DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build()))
                .ifPresent(advertEntity -> {
                    if (advertEntity.getStatus().equals(AdvertStatusType.IN_REVIEW)) {
                        throw BadRequestException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.INSUFFICIENT_PRIVILEGE)).build();
                    }

                    if (advertEntity.getStatus().equals(AdvertStatusType.ACTIVE)) {
                        throw BadRequestException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.ACTIVE_STATUS_ERROR)).build();
                    }

                    advertEntity.setStatus(AdvertStatusType.ACTIVE);
                    advertRepository.save(advertEntity);
                });


        BaseResponse baseResponse = new BaseResponse();
        baseResponse.addSuccessMessage();
        return baseResponse;
    }

    @Override
    public BaseResponse setPassiveStatusById(Long id) {
        if (Objects.isNull(id)) {
            throw RequiredFieldException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.ID_IS_REQUIRED)).build();
        }

        Optional<AdvertEntity> advertEntity = advertRepository.findById(id);
        if (advertEntity.isEmpty()) {
            throw DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build();
        }

        if (advertEntity.get().getStatus().equals(AdvertStatusType.IN_REVIEW)) {
            throw BadRequestException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.INSUFFICIENT_PRIVILEGE)).build();
        }

        if (advertEntity.get().getStatus().equals(AdvertStatusType.PASSIVE)) {
            throw BadRequestException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.PASSIVE_STATUS_ERROR)).build();
        }

        advertEntity.get().setStatus(AdvertStatusType.PASSIVE);
        advertRepository.save(advertEntity.get());

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.addSuccessMessage();
        return baseResponse;
    }

    @Override
    public BaseResponse deleteById(Long id) {
        if (Objects.isNull(id)) {
            throw RequiredFieldException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.ID_IS_REQUIRED)).build();
        }

        Optional.ofNullable(advertRepository.findById(id)
                        .orElseThrow(() -> DataNotFoundException.builder().messageResponse(MessageResponse.addErrorMessage(MessageUtil.DATA_NOT_FOUND)).build()))
                .ifPresent(advertRepository::delete);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.addSuccessMessage();
        return baseResponse;
    }

}
