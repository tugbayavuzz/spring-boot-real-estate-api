package com.realestate.model.response;

import com.realestate.model.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse extends BaseResponse {
    private UserDTO user;
}
