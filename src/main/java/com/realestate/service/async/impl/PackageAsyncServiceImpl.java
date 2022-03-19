package com.realestate.service.async.impl;

import com.realestate.model.entity.PackageEntity;
import com.realestate.model.enums.PaymentType;
import com.realestate.repository.PackageRepository;
import com.realestate.service.async.PackageAsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PackageAsyncServiceImpl implements PackageAsyncService {

    private final PackageRepository packageRepository;

    @Async
    @Override
    public void updatePackagePaymentType(Long packageId) {
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Optional<PackageEntity> byId = packageRepository.findById(packageId);
        if (byId.isPresent()) {
            byId.get().setPaymentType(PaymentType.OK);
            packageRepository.save(byId.get());
        }
    }
}
