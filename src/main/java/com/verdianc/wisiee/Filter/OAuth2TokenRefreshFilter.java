package com.verdianc.wisiee.Filter;

import com.verdianc.wisiee.Oauth.OAuth2TokenServiceimpl;
import com.verdianc.wisiee.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class OAuth2TokenRefreshFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final OAuth2TokenServiceimpl oAuth2TokenService;
    private final HttpSession httpSession;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId!=null) {
            //토큰 만료 여부 확인
            if (isAccessTokenExpired()) {
                //만료시, refresh token 조회
                String refreshToken = userRepository.findRefreshTokenByUserId(userId).orElse(null);
                //refresh token으로 access token 업데이트
                String newAccessToken = oAuth2TokenService.refreshAccessToken(refreshToken);
                //access token update
                httpSession.setAttribute("accessToken", newAccessToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAccessTokenExpired() {
        Long expiresAt = (Long) httpSession.getAttribute("accessTokenExpiresAt");
        return expiresAt!=null && System.currentTimeMillis() > (expiresAt - 60_000);
    }
}
