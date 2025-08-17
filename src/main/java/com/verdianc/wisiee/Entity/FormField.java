package com.verdianc.wisiee.Entity;

import com.verdianc.wisiee.Common.Enum.SelectOption;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Embeddable
@Entity(name = "FormField")
@RequiredArgsConstructor
@Setter
public class FormField {
  // 폼 영역(일부) -> 폼인포에 여러개 들어감
  @EmbeddedId
  private FormFieldId formFieldId;
  //enum, 단일인지, 다중인지
  // dto에서 리스트로 받아서 여러개 넣기
  @Column(name = "select_option")
  @Enumerated(EnumType.STRING)
  private SelectOption selectOption;

  @Column(name = "option_items")
  private String optionItems;

  @Column(name = "field_title")
  private String fieldTitle;

  @Column(name = "field_descript")
  private String fieldDescipt;


  @Column(name = "is_required")
  private Boolean isRequired;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "form_id", nullable = false)
  private FormEntity form;

}
