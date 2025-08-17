package com.verdianc.wisiee.Entity;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

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
  private String selectOption;

  @Column(name = "optionInfos")
  private String optionInfos;

  private String title;

  private String descipt;

  private Boolean required;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "form_id", nullable = false)
  private FormEntity form;




}
