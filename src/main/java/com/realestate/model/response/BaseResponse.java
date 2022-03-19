package com.realestate.model.response;

import com.realestate.model.dto.MessageDTO;
import com.realestate.utils.MessageUtil;
import lombok.Data;


@Data
public class BaseResponse {

    private MessageDTO message;

    public void addSuccessMessage() {
        this.setMessage(MessageDTO
                .builder()
                .type(MessageUtil.SUCCESS.getType())
                .code(MessageUtil.SUCCESS.getCode())
                .text(MessageUtil.SUCCESS.getText())
                .build());
    }

}