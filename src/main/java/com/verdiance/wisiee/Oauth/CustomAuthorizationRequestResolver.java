package com.verdiance.wisiee.Oauth;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private final DefaultOAuth2AuthorizationRequestResolver defaultResolver;

    public CustomAuthorizationRequestResolver(ClientRegistrationRepository repo, String authorizationRequestBaseUri) {
        this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(repo, authorizationRequestBaseUri);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest req = defaultResolver.resolve(request);
        return req!=null ? customize(req):null;
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest req = defaultResolver.resolve(request, clientRegistrationId);
        return req!=null ? customize(req):null;
    }

    private OAuth2AuthorizationRequest customize(OAuth2AuthorizationRequest request) {
        if (request==null) return null;

        if ("google".equals(request.getAttribute(OAuth2ParameterNames.REGISTRATION_ID))) {
            Map<String, Object> extraParams = new HashMap<>(request.getAdditionalParameters());

            // 오프라인 액세스(리프레시 토큰 발급) 요청
            extraParams.put("access_type", "offline");

            // 테스트 단계에서는 매번 동의창을 띄워 토큰을 확실히 받음
//            extraParams.put("prompt", "consent");

            return OAuth2AuthorizationRequest.from(request)
                    .additionalParameters(extraParams)
                    .build();
        }
        return request;
    }
}

