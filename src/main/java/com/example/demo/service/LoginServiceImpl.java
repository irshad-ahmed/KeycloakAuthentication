package com.example.demo.service;

import com.example.demo.dto.LogoutTokenRequestDTO;
import com.example.demo.dto.TokenRequestDTO;
import com.example.demo.dto.TokenResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    RestTemplate template;

    @Override
    public TokenResponseDTO login(TokenRequestDTO tokenRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("client_id", "demo-client");
        requestParam.add("client_secret", "83755419-4abc-469a-ade6-4f08f42fc85b");
        requestParam.add("grant_type", tokenRequestDTO.getGrantType());
        requestParam.add("username", tokenRequestDTO.getUsername());
        requestParam.add("password", tokenRequestDTO.getPassword());
        String tokenUrl = "http://localhost:8081/auth/realms/demo/protocol/openid-connect/token";
        TokenResponseDTO response = template.postForEntity(
                tokenUrl,
                new HttpEntity<>(requestParam, headers),
                TokenResponseDTO.class
                ).getBody();
        return response;
    }

    @Override
    public boolean logout(LogoutTokenRequestDTO logoutTokenRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("client_id", "demo-client");
        requestParam.add("client_secret", "83755419-4abc-469a-ade6-4f08f42fc85b");
        requestParam.add("refresh_token", logoutTokenRequestDTO.getRefreshToken());
        String tokenUrl = "http://localhost:8081/auth/realms/demo/protocol/openid-connect/logout";
        boolean response = template.postForEntity(
                tokenUrl, new HttpEntity<>(requestParam, headers), String.class)
                .getStatusCode().equals(HttpStatus.NO_CONTENT);
        return response;
    }
}
