package com.verdiance.wisiee.Oauth;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.oauth.google")
public class GoogleOAuthProperties {
  private String revokeUri;
}