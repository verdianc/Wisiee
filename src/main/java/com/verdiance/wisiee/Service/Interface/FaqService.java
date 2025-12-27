package com.verdiance.wisiee.Service.Interface;

import com.verdiance.wisiee.Common.Enum.Category;
import com.verdiance.wisiee.DTO.Faq.FaqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FaqService {

  FaqDTO createFaq(String question, String answer, Category category);

  Page<FaqDTO> getFaqsByCategory(Category category, Pageable pageable);

  Page<FaqDTO> searchFaq(String keyword, Pageable pageable);

  void deleteFaq(Long faqId);
}
