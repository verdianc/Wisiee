package com.verdianc.wisiee.Oauth;

import com.verdianc.wisiee.Oauth.Interface.OauthUserInfoResp;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoogleUserResp implements OauthUserInfoResp {
    private Map<String, Object> attributes;

    public GoogleUserResp(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

}
