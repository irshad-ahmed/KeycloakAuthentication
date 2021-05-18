package com.example.demo.service;

import com.example.demo.dto.LogoutTokenRequestDTO;
import com.example.demo.dto.TokenRequestDTO;
import com.example.demo.dto.TokenResponseDTO;

public interface LoginService {
    public TokenResponseDTO login(TokenRequestDTO tokenRequestDTO);

    boolean logout(LogoutTokenRequestDTO logoutTokenRequestDTO);
}
