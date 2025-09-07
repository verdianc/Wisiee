package com.verdianc.wisiee.Oauth;

import com.verdianc.wisiee.Oauth.Interface.OauthUserInfoResp;
import java.util.Map;

public class NaverUserResp implements OauthUserInfoResp {
    private Map<String, Object> attributes;

    public NaverUserResp(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

}
