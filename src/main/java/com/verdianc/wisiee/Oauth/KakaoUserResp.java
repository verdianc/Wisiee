package com.verdianc.wisiee.Oauth;

import com.verdianc.wisiee.Oauth.Interface.OauthUserInfoResp;
import java.util.Map;

public class KakaoUserResp implements OauthUserInfoResp {
    private Map<String, Object> attributes;
    private Map<String, Object> kakaoAccount;
    private Map<String, Object> properties;

    public KakaoUserResp(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        return kakaoAccount!=null ? (String) kakaoAccount.get("email"):null;
    }
}
