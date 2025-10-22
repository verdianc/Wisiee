package com.verdiance.wisiee.DTO.User;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressBookListResponseDTO {
    private List<AddressBookResponseDTO> addressBooks;
    private int count;
    private boolean limitExceeded;
}