package com.verdianc.wisiee.Mapper;

import com.verdianc.wisiee.DTO.FormFieldDTO;
import com.verdianc.wisiee.Entity.FormField;
import com.verdianc.wisiee.Entity.FormFieldId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FormFieldMapper {

    // Entity -> DTO
    public static FormFieldDTO toDTO(FormField entity) {
        if (entity==null) return null;

        //옵션 아이템 서버 -> 클라이언트 일 떄에는 LIST로
        List<String> optionList = null;
        if (entity.getOptionItems()!=null && !entity.getOptionItems().isEmpty()) {
            optionList = Arrays.stream(entity.getOptionItems().split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
        }
        //등록/수정 시에는 ID 불필요
        return FormFieldDTO.builder()
                .fieldTitle(entity.getFieldTitle())
                .fieldDescipt(entity.getFieldDescipt())
                .isRequired(entity.isRequired())
                .selectOption(entity.getSelectOption())
                .optionItems(optionList)
                .build();
    }

    // DTO -> Entity
    public static FormField toEntity(FormFieldDTO dto) {
        if (dto==null) return null;

        //옵션 아이템 클라이언트 -> 서버 일 떄에는 STRING으로
        String optionItems = null;
        if (dto.getOptionItems()!=null && !dto.getOptionItems().isEmpty()) {
            optionItems = String.join(",", dto.getOptionItems());
        }

        //등록/수정 시에는 ID 필요
        FormFieldId id = new FormFieldId(dto.getFormId(), dto.getOrder());

        return FormField.builder()
                .formFieldId(id)
                .fieldTitle(dto.getFieldTitle())
                .fieldDescipt(dto.getFieldDescipt())
                .isRequired(dto.isRequired())
                .selectOption(dto.getSelectOption())
                .optionItems(optionItems)
                .build();
    }
}
