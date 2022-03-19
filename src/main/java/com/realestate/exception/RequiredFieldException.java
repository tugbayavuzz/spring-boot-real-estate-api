package com.realestate.exception;

import com.realestate.model.response.MessageResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequiredFieldException extends RuntimeException {
    private MessageResponse messageResponse;
}