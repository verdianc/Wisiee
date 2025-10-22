package com.verdiance.wisiee.Controller;

import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/")
    public String index() {
        return "forward:/index.html"; // templates/index.html 찾아감
    }

    @GetMapping("/home")
    public String hi(HttpSession session, Authentication authentication) {
        return "forward:/home.html";
    }
}
