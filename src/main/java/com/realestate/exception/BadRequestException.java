package com.realestate.exception;

import com.realestate.model.response.MessageResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BadRequestException extends RuntimeException {
    private MessageResponse messageResponse;
}
