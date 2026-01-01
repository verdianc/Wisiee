package com.verdiance.wisiee.Common.Config;

import com.verdiance.wisiee.Oauth.CustomAuthorizationRequestResolver;
import com.verdiance.wisiee.Oauth.CustomOAuth2UserService;
import com.verdiance.wisiee.Oauth.OAuth2LoginSuccessHandler;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
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
                                .authorizationRequestResolver(new CustomAuthorizationRequestResolver(clientRegistrationRepository, "/wisiee/oauth2/authorization"))
                        )
                        .successHandler(oAuth2LoginSuccessHandler)
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 1. 프론트엔드 주소 허용 (쿠키를 쓰려면 "*" 사용 불가, 반드시 명시해야 함)
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",  // 로컬 개발 환경
                "https://wisiee.com"    // 배포 환경
        ));

        // 2. 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // 3. 모든 헤더 허용
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // 4. 자격 증명(쿠키/세션) 허용 (이게 true여야 로그인 유지가 됨)
        configuration.setAllowCredentials(true);

        // 5. 브라우저가 preflight 요청 결과를 캐시하는 시간 (1시간)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

