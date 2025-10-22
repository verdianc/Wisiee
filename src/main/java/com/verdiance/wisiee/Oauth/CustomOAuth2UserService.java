package com.verdiance.wisiee.Oauth;

import com.verdiance.wisiee.Entity.UserEntity;
import com.verdiance.wisiee.Oauth.Interface.OauthUserInfoResp;
import com.verdiance.wisiee.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // 어떤 provider인지 확인 (google, kakao, naver)
        String registrationId = userRequest.getClientRegistration().getRegistrationId().toUpperCase();


        // provider별 user info 파싱
        OauthUserInfoResp userInfo = switch (registrationId) {
            case "GOOGLE" -> new GoogleUserResp(oAuth2User.getAttributes());
            case "KAKAO" -> new KakaoUserResp(oAuth2User.getAttributes());
            case "NAVER" -> new NaverUserResp(oAuth2User.getAttributes());
            default -> throw new IllegalArgumentException("지원하지 않는 Provider: " + registrationId);
        };
        log.debug(userInfo.toString());

        // DB에서 사용자 조회
        UserEntity user = userRepository.findByProviderNmAndProviderId(registrationId, userInfo.getId())
                .orElseGet(() -> UserEntity.builder()
                        .providerNm(registrationId)
                        .providerId(userInfo.getId())
                        .email(userInfo.getEmail())
                        .build()
                );

        // 신규라면 저장
        if (user.getUserId()==null) {
            userRepository.save(user);
        }

        return oAuth2User;
    }
}
