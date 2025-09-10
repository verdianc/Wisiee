package com.verdianc.wisiee.Oauth;

import com.verdianc.wisiee.Exception.Oauth.RefreshAccessTokenFail;
import com.verdianc.wisiee.Exception.Oauth.RefreshTokenNotFound;
import com.verdianc.wisiee.Oauth.Interface.OAuth2TokenService;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OAuth2TokenServiceimpl implements OAuth2TokenService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpSession httpSession;

    @Value("${spring.security.oauth2.client.registration.google.token-uri}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Override
    public String refreshAccessToken(String refreshToken) {
        if (refreshToken==null) throw new RefreshTokenNotFound();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("refresh_token", refreshToken);
        params.add("grant_type", "refresh_token");


        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, params, Map.class);
        Map<String, Object> body = response.getBody();
        if (body==null || body.get("access_token")==null) {
            throw new RefreshAccessTokenFail();
        }

        // 새 토큰 만료시간 세션에 업데이트
        Long expiresIn = ((Number) body.get("expires_in")).longValue();
        httpSession.setAttribute("accessTokenExpiresAt", System.currentTimeMillis() + expiresIn * 1000);

        return (String) body.get("access_token");
    }
}
