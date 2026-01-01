package com.verdiance.wisiee.Oauth;

import com.verdiance.wisiee.Entity.UserEntity;
import com.verdiance.wisiee.Exception.Oauth.UserIdNotFound;
import com.verdiance.wisiee.Repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;

    // application.yml 에서 이 값을 읽어옵니다.
    @Value("${app.config.sendRedirect}")
    private String sandRedirect;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 1. OAuth2User 정보 추출
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        // 2. Provider 확인 (google, naver, kakao)
        String providerNm = oauthToken.getAuthorizedClientRegistrationId();
        String providerId;

        switch (providerNm.toLowerCase()) {
            case "google" -> {
                providerId = oAuth2User.getAttribute("sub");
            }
            case "naver" -> {
                Map<String, Object> resp = (Map<String, Object>) oAuth2User.getAttribute("response");
                providerId = (String) resp.get("id");
            }
            case "kakao" -> {
                providerId = String.valueOf(oAuth2User.getAttribute("id"));
            }
            default -> throw new IllegalArgumentException("지원하지 않는 Provider: " + providerNm);
        }

        // 3. 토큰 가져오기
        OAuth2AuthorizedClient client =
                authorizedClientService.loadAuthorizedClient(providerNm, oAuth2User.getName());

        String accessToken = "";
        String refreshToken = null;

        HttpSession httpSession = request.getSession(true);

        if (client!=null) {
            httpSession.setAttribute("accessTokenExpiresAt", client.getAccessToken().getExpiresAt().toEpochMilli());
            accessToken = client.getAccessToken().getTokenValue();

            if (client.getRefreshToken()!=null) {
                refreshToken = client.getRefreshToken().getTokenValue();
            }
        }

        // 4. DB 사용자 조회 및 저장
        UserEntity user = userRepository.findByProviderNmAndProviderId(providerNm.toUpperCase(), providerId)
                .orElseThrow(() -> new UserIdNotFound(providerNm.toUpperCase(), providerId));

        user.generateDefaultNickname();

        if (refreshToken!=null && !refreshToken.equals(user.getRefreshToken())) {
            userRepository.updateRefreshToken(user.getUserId(), refreshToken);
        }

        userRepository.save(user);

        // 5. 세션 저장 (백엔드용)
        httpSession.setAttribute("userId", user.getUserId());

        // response.sendRedirect(targetUrl); 바로 윗줄에 추가
        String sessionid = httpSession.getId();
        // SameSite=None과 Secure를 직접 명시해야만 localhost와 wisiee.store 간에 세션이 공유됩니다.
        String cookieHeader = String.format("JSESSIONID=%s; Path=/; HttpOnly; SameSite=None; Secure", sessionid);
        response.setHeader("Set-Cookie", cookieHeader);

        response.sendRedirect(sandRedirect);
    }
}