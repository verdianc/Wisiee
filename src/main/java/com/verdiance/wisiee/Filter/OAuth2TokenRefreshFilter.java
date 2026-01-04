//package com.verdiance.wisiee.Filter;
//
//import com.verdiance.wisiee.Oauth.OAuth2TokenServiceimpl;
//import com.verdiance.wisiee.Repository.UserRepository;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//@Component
//@RequiredArgsConstructor
//public class OAuth2TokenRefreshFilter extends OncePerRequestFilter {
//
//    private final UserRepository userRepository;
//    private final OAuth2TokenServiceimpl oAuth2TokenService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        HttpSession httpSession = request.getSession();
//        Long userId = (Long) httpSession.getAttribute("userId");
//        if (userId!=null && isAccessTokenExpired(httpSession)) {
//
//            //만료시, refresh token 조회
//            String refreshToken = userRepository.findRefreshTokenByUserId(userId).orElse(null);
//            if (refreshToken==null) {
//                handleException(response, httpSession);
//                return;
//            }
//
//            try {
//                //refresh token으로 access token 업데이트
//                String newAccessToken = oAuth2TokenService.refreshAccessToken(refreshToken);
//                //access token update
//                httpSession.setAttribute("accessToken", newAccessToken);
//            } catch (Exception e) {
//                logger.error("토큰 갱신 실패: ", e);
//                handleException(response, httpSession);
//                return;
//            }
//
//
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private boolean isAccessTokenExpired(HttpSession httpSession) {
//        Long expiresAt = (Long) httpSession.getAttribute("accessTokenExpiresAt");
//        return expiresAt!=null && System.currentTimeMillis() > (expiresAt - 60_000);
//    }
//
//    private void handleException(HttpServletResponse response, HttpSession httpSession) throws IOException {
//        try {
//            httpSession.invalidate();
//        } catch (IllegalStateException e) {
//            // 이미 만료된 경우 무시
//        }
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//    }
//}
