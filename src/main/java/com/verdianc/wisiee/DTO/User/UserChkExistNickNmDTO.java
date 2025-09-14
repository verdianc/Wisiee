package com.verdianc.wisiee.DTO.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserChkExistNickNmDTO {

    private String nickNm;
    private boolean existRslt;
}
