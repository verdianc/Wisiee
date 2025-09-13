package com.verdianc.wisiee.DTO.User;

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
    private String bankName;
    private String accountNumber;
    private String accountHolder;

    // Optional getter
    public Optional<String> getNickNmOpt() {
        return Optional.ofNullable(nickNm);
    }

    public Optional<String> getBankNameOpt() {
        return Optional.ofNullable(bankName);
    }

    public Optional<String> getAccountNumberOpt() {
        return Optional.ofNullable(accountNumber);
    }

    public Optional<String> getAccountHolderOpt() {
        return Optional.ofNullable(accountHolder);
    }

    public void setFile(MultipartFile file) throws IOException {
        if (file!=null && !file.isEmpty()) {
            this.fileData = file.getBytes();
            this.contentType = file.getContentType();
        }
    }
}
