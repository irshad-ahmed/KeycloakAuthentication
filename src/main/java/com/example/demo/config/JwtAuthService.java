package com.example.demo.config;

import com.example.demo.dto.InspectTokenRequestDTO;
import com.example.demo.dto.InspectTokenResponseDTO;
import com.example.demo.dto.SigningKeyDTO;
import com.example.demo.dto.TokenResponseDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthService {

    @Autowired
    RestTemplate template;

    public UsernamePasswordAuthenticationToken decodeToken(String token) throws Exception {
        UsernamePasswordAuthenticationToken tokenData = null;
        InspectTokenRequestDTO request = new InspectTokenRequestDTO();
        request.setToken(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("client_id", "demo-client");
        requestParam.add("client_secret", "83755419-4abc-469a-ade6-4f08f42fc85b");
        requestParam.add("token", token);
        String tokenUrl = "http://localhost:8081/auth/realms/demo/protocol/openid-connect/token/introspect";
        InspectTokenResponseDTO response = template.postForEntity(
                tokenUrl,
                new HttpEntity<>(requestParam, headers),
                InspectTokenResponseDTO.class
        ).getBody();
        if(response.getActive()){
            Claims claims = Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();
            tokenData = new UsernamePasswordAuthenticationToken(
                    claims.get("name"),
                    null,
                    ((List<String>) claims.get("groups"))
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()));
        }
        return tokenData;
    }
    private PublicKey getPublicKey() throws Exception {
        SigningKeyDTO signingKeyDTO = template.getForEntity("http://localhost:8081/auth/realms/demo", SigningKeyDTO.class)
                .getBody();
        byte[] publicBytes = Base64.decodeBase64(signingKeyDTO.getPublicKey());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
}
