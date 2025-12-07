package com.verdiance.wisiee.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AdminEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long adminId;

  @Column(nullable = false, length = 50)
  private String username;

  @Column(nullable = false, length = 200)
  private String passwordHash;

  // 비밀번호 비교 (BCrypt)
  public boolean matchPassword(String rawPassword) {
    return org.springframework.security.crypto.bcrypt.BCrypt
        .checkpw(rawPassword, this.passwordHash);
  }

}
