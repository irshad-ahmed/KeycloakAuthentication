package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LogoutTokenRequestDTO {
    @JsonProperty("refresh_token")
    private String refreshToken;
}
