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
    private byte[] fileData;
    private String contentType;

    // Optional getter
    public Optional<String> getNickNmOpt() {
        return Optional.ofNullable(nickNm);
    }


    public void setFile(MultipartFile file) throws IOException {
        if (file!=null && !file.isEmpty()) {
            this.fileData = file.getBytes();
            this.contentType = file.getContentType();
        }
    }
}
