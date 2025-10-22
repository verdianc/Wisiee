package com.verdiance.wisiee.Common.Util;

import com.verdiance.wisiee.Exception.User.SessionUserNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonUtil {
    private final HttpSession httpSession;

    public Long getUserId() {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId==null) {
            throw new SessionUserNotFoundException();
        }
        return userId;
    }
}
