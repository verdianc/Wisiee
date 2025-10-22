package com.verdiance.wisiee.Oauth.Interface;

public interface OAuth2TokenService {
    String refreshAccessToken(String refreshToken);
}
