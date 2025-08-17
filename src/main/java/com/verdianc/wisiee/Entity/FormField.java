package com.verdianc.wisiee.Entity;

import java.util.List;

public class FormField {
  // 폼 영역(일부) -> 폼인포에 여러개 들어감
  private String formId;
  //enum, 단일인지, 다중인지
  // dto에서 리스트로 받아서 여러개 넣기
  private String selectOption;

  private List<String> optionInfos;

  private String title;

  private String descipt;

  private Boolean required;

  private int order;







}
