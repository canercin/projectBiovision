package com.biovision.back.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
