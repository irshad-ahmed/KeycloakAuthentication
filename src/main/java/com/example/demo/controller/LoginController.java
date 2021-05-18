package com.example.demo.controller;

import com.example.demo.dto.LogoutTokenRequestDTO;
import com.example.demo.dto.TokenRequestDTO;
import com.example.demo.dto.TokenResponseDTO;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/token/login")
    public TokenResponseDTO login(@RequestBody TokenRequestDTO tokenRequestDTO){
        return loginService.login(tokenRequestDTO);
    }

    @GetMapping("/hello")
    public String testApi(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String ret = auth.getName()+" "+auth.getAuthorities();
        //System.out.println(auth.getName()+" "+auth.getAuthorities());
        return ret;
    }

    @PostMapping("/token/logout")
    public boolean logout(@RequestBody LogoutTokenRequestDTO logoutTokenRequestDTO){
        return loginService.logout(logoutTokenRequestDTO);
    }
}
