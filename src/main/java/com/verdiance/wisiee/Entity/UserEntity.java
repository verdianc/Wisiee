package com.verdiance.wisiee.Entity;

import com.verdiance.wisiee.Exception.Common.ResourceUpdateFailedException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
//동일한 provider + providerId는 복수개 존재할 수 없음
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_INFO",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "providerInfo",
                        columnNames = {"provider_name", "provider_id"}
                )
        })
@Builder
@Getter
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    //소셜 로그인 provider 정보
    @Column(name = "provider_name", nullable = false)
    private String providerNm;

    //소셜 로그인 provider에서 제공하는 providerId
    //신규 사용지 <-> 기존 사용자 구분키 위해
    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "nick_name", nullable = true, unique = true, length = 50)
    private String nickNm;

    @Column(name = "email")
    private String email;

    @Column(name = "refresh_token", length = 512)
    private String refreshToken;

    @Column(name = "profile_image_url", columnDefinition = "TEXT")
    private String profileImgUrl;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressBookEntity> addressBooks = new ArrayList<>();

    @Column(name = "nickname_updated_at")
    private LocalDate nicknameUpdatedAt;


    public void changeProfileImage(String newUrl) {
        this.profileImgUrl = newUrl;
    }


    public void validateNicknameChangeAllowed() {
        // null → 최초 변경이므로 허용
        if (this.nicknameUpdatedAt == null) {
            return;
        }

        LocalDate availableDate = this.nicknameUpdatedAt.plusDays(60);

        if (LocalDate.now().isBefore(availableDate)) {
            throw new ResourceUpdateFailedException(
                "닉네임은 최초 변경 후 60일이 지나야 변경 가능합니다."
            );
        }
    }


    // 닉네임 변경 처리
    public void changeNickName(String newNickNm) {
        this.nickNm = newNickNm;
        this.nicknameUpdatedAt = LocalDate.now(); // 변경 일시 기록
    }


    public void generateDefaultNickname() {
        if (this.nickNm==null || this.nickNm.isBlank()) {
            this.nickNm = "user-" + this.userId;
        }
    }


}
