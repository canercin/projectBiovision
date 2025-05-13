package com.biovision.back.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@SuperBuilder
@Data
public abstract class UserDTO {
    protected UUID id;
    protected String username;
    protected String password;
    protected String role;
    protected String firstName;
    protected String lastName;
}
