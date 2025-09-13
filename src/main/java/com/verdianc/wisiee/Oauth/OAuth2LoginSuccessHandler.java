package com.verdianc.wisiee.Oauth;

import com.verdianc.wisiee.Entity.UserEntity;
import com.verdianc.wisiee.Exception.Oauth.UserIdNotFound;
import com.verdianc.wisiee.Repository.UserRepository;
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

    private final HttpSession httpSession; // 스프링 세션
    private final UserRepository userRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Value("${spring.config.sandRedirect}")    // MinIO=true, AWS는 false여도 동작
    private String sandRedirect;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // OAuth2User
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Authentication 안에 OAuth2AuthenticationToken 캐스팅
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        // 여기서 registrationId (= providerNm) 얻을 수 있음 (google, naver, kakao 등)
        String providerNm = oauthToken.getAuthorizedClientRegistrationId();

        // providerId (구글은 sub, 네이버는 id 등)
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

        // 이미 저장된 authorizedClient에서 accessToken / refreshToken 가져오기
        OAuth2AuthorizedClient client =
                authorizedClientService.loadAuthorizedClient(providerNm, oAuth2User.getName());

        String refreshToken = null;
        if (client!=null) {
            //access token 만료 시간 session에 저장
            httpSession.setAttribute("accessTokenExpiresAt", client.getAccessToken().getExpiresAt().toEpochMilli());
            if (client.getRefreshToken()!=null) {
                //refresh token 얻기
                refreshToken = client.getRefreshToken().getTokenValue();
                String accessToken = client.getAccessToken().getTokenValue();
            }


        }
        // DB 조회 (providerNm + providerId 기준)
        UserEntity user = userRepository.findByProviderNmAndProviderId(providerNm.toUpperCase(), providerId)
                .orElseThrow(() -> new UserIdNotFound(providerNm.toUpperCase(), providerId));


        // 기본 닉네임 없으면 생성
        user.generateDefaultNickname();
        userRepository.save(user);



        //refresh token 저장
        // TODO : 암호화 필요
        if (refreshToken!=null && !refreshToken.equals(user.getRefreshToken())) {
            userRepository.updateRefreshToken(user.getUserId(), refreshToken);
        }

        // 세션에 userId 저장
        httpSession.setAttribute("userId", user.getUserId());

        // 로그인 성공 후 API 호출로만 처리
        response.sendRedirect(sandRedirect);



//        // 5. 로그인 후 리다이렉트
//        response.sendRedirect("/wisiee/home");
    }

}
