package com.verdiance.wisiee.Common.Config;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CookieConfig {
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return (factory) -> factory.addContextCustomizers((context) -> {
            // 스프링 부트 내장 톰캣의 쿠키 처리기를 교체합니다.
            Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();

            // 🚀 핵심: 모든 쿠키에 대해 SameSite=None을 강제 적용합니다.
            cookieProcessor.setSameSiteCookies(SameSiteCookies.NONE.getValue());

            context.setCookieProcessor(cookieProcessor);
        });
    }
}
