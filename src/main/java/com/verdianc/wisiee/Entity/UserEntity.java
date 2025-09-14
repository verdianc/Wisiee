package com.verdianc.wisiee.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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

    // 닉네임 변경 횟수
    @Column(name = "nick_change_count", nullable = false)
    private int nickChangeCount = 0;

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

    public void changeProfileImage(String newUrl) {
        this.profileImgUrl = newUrl;
    }


    public void changeNickName(String newNick) {
        this.nickNm = newNick;
        this.nickChangeCount++;
    }

    public void generateDefaultNickname() {
        if (this.nickNm==null || this.nickNm.isBlank()) {
            this.nickNm = "user-" + this.userId;
        }
    }


}
