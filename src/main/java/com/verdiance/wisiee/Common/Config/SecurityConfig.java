package com.verdiance.wisiee.Common.Config;

import com.verdiance.wisiee.Oauth.CustomAuthorizationRequestResolver;
import com.verdiance.wisiee.Oauth.CustomOAuth2UserService;
import com.verdiance.wisiee.Oauth.OAuth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor  // Lombok이 final 필드 생성자 만들어줌
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**", "/wisiee/**", "/wisiee/docs",
                                "/wisiee/swagger-ui/**",
                                "/wisiee/v3/api-docs/**",
                                "/wisiee/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .authorizationEndpoint(authorization -> authorization
                                .authorizationRequestResolver(new CustomAuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/authorization"))
                        )
                        .successHandler(oAuth2LoginSuccessHandler)
                );

        return http.build();
    }

}

