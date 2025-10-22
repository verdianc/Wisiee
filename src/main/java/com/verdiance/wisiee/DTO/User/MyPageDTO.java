package com.verdiance.wisiee.DTO.User;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageDTO {

    private Long userId;
    private String nickNm;
    private String email;
    private String profileImgUrl; // S3 업로드 URL
    private int formCount;      // 사용자가 작성한 폼 개수
    private int nickChangeLeft; // 닉네임 변경 가능 횟수 (3 - nickChangeCount)
    private LocalDateTime createdAt; // 가입일
    private AddressBookResponseDTO mainAddress;
    // TODO : 주문 개수 추가


}
