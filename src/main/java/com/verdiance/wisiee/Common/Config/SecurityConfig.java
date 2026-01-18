package com.verdiance.wisiee.Common.Config;

import com.verdiance.wisiee.Filter.OAuth2TokenRefreshFilter;
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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2TokenRefreshFilter jwtAuthenticationFilter; // [추가] 필터 주입

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // 1. CSRF 비활성화 (JWT는 CSRF 공격으로부터 상대적으로 안전함)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. [핵심] 세션 관리 상태 없음(STATELESS)으로 설정
                // 스프링 시큐리티가 세션을 생성하지도 않고, 기존 것을 사용하지도 않음
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 3. 예외 처리 (인증 실패 시 401 응답)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )

                // 4. CORS 설정 적용
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 5. 요청 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // (1) Swagger, OAuth2, 로그인 관련은 모두 허용
                        .requestMatchers(
                                "/oauth2/**",
                                "/login/**",
                                "/favicon.ico",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/error"
                        ).permitAll()

                        // (2) 그 외 모든 요청은 인증 필요 (JWT 검사)
                        // 주의: 기존 코드의 "/**"를 permitAll() 하면 필터가 있어도 아무나 들어옵니다. 제거했습니다.
                        .anyRequest().authenticated()
                )

                // 6. OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri("/oauth2/authorization")
                                .authorizationRequestResolver(new CustomAuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/authorization"))
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(oAuth2LoginSuccessHandler) // JWT 발급 핸들러 연결
                )

                // 7. [핵심] JWT 인증 필터를 UsernamePassword 필터 앞에 배치
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList(
                "https://localhost:5173",
                "https://wisiee.com",
                "https://www.wisiee.com"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // [추가] 클라이언트가 Authorization 헤더(JWT)를 읽을 수 있게 허용
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}