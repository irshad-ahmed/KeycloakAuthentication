package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SigningKeyDTO {
    @JsonProperty("public_key")
    private String publicKey;
}
