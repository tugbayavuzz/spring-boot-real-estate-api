package com.realestate.service.async.impl;

import com.realestate.model.entity.AdvertEntity;
import com.realestate.model.enums.AdvertStatusType;
import com.realestate.repository.AdvertRepository;
import com.realestate.service.async.AdvertAsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AdvertAsyncServiceImpl implements AdvertAsyncService {

    private final AdvertRepository advertRepository;

    @Async
    @Override
    public void updateAdvertStatus() {
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<AdvertEntity> advertEntityList = advertRepository.getInReviewAdverts();
        if(advertEntityList != null && !advertEntityList.isEmpty()){
            for (AdvertEntity advertEntity: advertEntityList) {
                advertEntity.setStatus(AdvertStatusType.ACTIVE);
                advertRepository.save(advertEntity);
            }
        }
    }
}
