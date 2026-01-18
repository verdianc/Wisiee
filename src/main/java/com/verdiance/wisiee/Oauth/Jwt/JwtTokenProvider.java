package com.verdiance.wisiee.Oauth.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long accessTokenValidityInMilliseconds;

    // application.yml에서 값 가져오기
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            @Value("${jwt.expiration}") long accessTokenValidityInMilliseconds) {
        // 비밀키를 암호화 알고리즘에 맞게 변환
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        // *만약 Base64 인코딩 안 된 평문 키를 yml에 적었다면 .decode 대신 .getBytes() 사용하세요.
        // 여기서는 안전하게 평문을 getBytes()로 처리하는 코드로 드릴게요 (초보자용)
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
    }

    // [질문하신 메서드] Access Token 생성
    public String createAccessToken(Long userId, String role) {
        Claims claims = Jwts.claims().setSubject(userId.toString()); // JWT payload에 저장되는 정보 단위
        claims.put("role", role); // 정보는 key-value 쌍으로 저장됩니다.

        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(validity) // set Expire Time
                .signWith(key, SignatureAlgorithm.HS256) // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .compact();
    }

    // (나중에 필요함) 토큰에서 UserId 추출
    public String getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // (나중에 필요함) 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
