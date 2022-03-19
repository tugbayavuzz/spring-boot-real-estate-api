package com.realestate.service.impl;

import com.realestate.model.response.BaseResponse;
import com.realestate.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public BaseResponse doPayment() {
        //ödeme işleminin başarılı yapıldığı kabul edildi.
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.addSuccessMessage();
        return baseResponse;
    }
}
