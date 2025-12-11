package com.verdiance.wisiee.DTO.User;

import java.io.IOException;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class UserInfoUpdateDTO {

    private Long userId;
    private String nickNm;
    private String email;
}
