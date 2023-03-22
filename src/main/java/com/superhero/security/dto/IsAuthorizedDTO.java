package com.superhero.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IsAuthorizedDTO {
    private boolean success;
    private boolean authorized;
}
