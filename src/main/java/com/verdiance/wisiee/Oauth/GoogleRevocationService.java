package com.verdiance.wisiee.Oauth;

import com.verdiance.wisiee.Exception.Oauth.LogoutFail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GoogleRevocationService {

    @Value("${spring.security.oauth2.client.provider.google.logout-uri}")
    private String logoutUri;

    
    private final RestTemplate restTemplate = new RestTemplate();

    public void revokeToken(String token) {
        if (token==null || token.isBlank()) {
            return;
        }

        // x-www-form-urlencoded 방식으로 token 파라미터 전송
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", token);

        try {
            // Google로 POST 요청 전송
            restTemplate.postForLocation(logoutUri, params);

        } catch (Exception e) {
            throw new LogoutFail();

        }
    }
}
