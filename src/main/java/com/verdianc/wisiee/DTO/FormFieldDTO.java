package com.verdianc.wisiee.DTO;

import com.verdianc.wisiee.Common.Enum.SelectOption;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FormFieldDTO {

    //formId
    private String formId;
    //field 제목
    private String fieldTitle;
    //field 설명
    private String fieldDescipt;
    //필수 여부
    private Boolean isRequired;
    //선택 옵션값
    private SelectOption selectOption;
    //선택값 리스트
    private List<String> optionItems;
    //순서
    private int order;
}
