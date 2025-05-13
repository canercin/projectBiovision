package com.biovision.back.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
