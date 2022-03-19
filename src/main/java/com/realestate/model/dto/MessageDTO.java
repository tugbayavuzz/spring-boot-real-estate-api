package com.realestate.model.dto;

import com.realestate.model.enums.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageDTO {

    private final MessageType type;
    private final String code;
    private final String text;

}