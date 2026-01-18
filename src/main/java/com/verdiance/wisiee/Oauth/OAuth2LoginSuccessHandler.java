package com.verdiance.wisiee.Oauth;

import com.verdiance.wisiee.Entity.UserEntity;
import com.verdiance.wisiee.Exception.Oauth.UserIdNotFound;
import com.verdiance.wisiee.Oauth.Jwt.JwtTokenProvider;
import com.verdiance.wisiee.Repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final JwtTokenProvider jwtTokenProvider; // [추가] JWT 생성 의존성 주입

    @Value("${app.config.sendRedirect}")
    private String sandRedirect; // 예: http://localhost:3000/oauth/callback

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 1. OAuth2User 정보 추출
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        // 2. Provider 확인
        String providerNm = oauthToken.getAuthorizedClientRegistrationId();
        String providerId;

        switch (providerNm.toLowerCase()) {
            case "google" -> providerId = oAuth2User.getAttribute("sub");
            case "naver" -> {
                Map<String, Object> resp = (Map<String, Object>) oAuth2User.getAttribute("response");
                providerId = (String) resp.get("id");
            }
            case "kakao" -> providerId = String.valueOf(oAuth2User.getAttribute("id"));
            default -> throw new IllegalArgumentException("지원하지 않는 Provider: " + providerNm);
        }

        // 3. Provider(구글 등)의 토큰 가져오기 (필요하다면 유지)
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(providerNm, oAuth2User.getName());
        String refreshToken = null;
        if (client!=null && client.getRefreshToken()!=null) {
            refreshToken = client.getRefreshToken().getTokenValue();
        }

        // 4. DB 사용자 조회 및 업데이트
        UserEntity user = userRepository.findByProviderNmAndProviderId(providerNm.toUpperCase(), providerId)
                .orElseThrow(() -> new UserIdNotFound(providerNm.toUpperCase(), providerId));

        user.generateDefaultNickname();

        // 구글 리프레시 토큰 저장 (필요시)
        if (refreshToken!=null && !refreshToken.equals(user.getRefreshToken())) {
            userRepository.updateRefreshToken(user.getUserId(), refreshToken);
        }
        userRepository.save(user);

        // ================= [변경 핵심 구간] =================

        // 5. JWT 생성
        // 우리 서버에서 인증용으로 쓸 Access Token을 발급합니다.
        String myAccessToken = jwtTokenProvider.createAccessToken(user.getUserId(), "user");

        // 6. 리다이렉트 URL 생성 (쿼리 파라미터로 토큰 전달)
        // 결과 예시: http://localhost:3000/oauth/callback?accessToken=eyJhbGci...
        String targetUrl = UriComponentsBuilder.fromUriString(sandRedirect)
                .fragment("accessToken=" + myAccessToken)
                .build()
                .toUriString();

        // 7. 리다이렉트 수행
        response.sendRedirect(targetUrl);
    }
}