package com.verdiance.wisiee.Service;

import com.verdiance.wisiee.DTO.User.AddressBookListResponseDTO;
import com.verdiance.wisiee.DTO.User.AddressBookRequestDTO;
import com.verdiance.wisiee.DTO.User.AddressBookResponseDTO;
import com.verdiance.wisiee.DTO.User.MyPageDTO;
import com.verdiance.wisiee.DTO.User.OauthDTO;
import com.verdiance.wisiee.DTO.User.UserChkExistNickNmDTO;
import com.verdiance.wisiee.DTO.User.UserInfoUpdateDTO;
import com.verdiance.wisiee.Entity.AddressBookEntity;
import com.verdiance.wisiee.Entity.UserEntity;
import com.verdiance.wisiee.Exception.Common.ResourceUpdateFailedException;
import com.verdiance.wisiee.Exception.User.AddressNotFoundException;
import com.verdiance.wisiee.Exception.User.AliasConflictException;
import com.verdiance.wisiee.Exception.User.DefaultAddressNotFoundException;
import com.verdiance.wisiee.Exception.User.SessionUserNotFoundException;
import com.verdiance.wisiee.Exception.User.UserNotFound;
import com.verdiance.wisiee.Mapper.AddressBookMapper;
import com.verdiance.wisiee.Repository.AddressBookRepository;
import com.verdiance.wisiee.Repository.UserRepository;
import com.verdiance.wisiee.Service.Interface.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AddressBookRepository addressBookRepository;
    private final HttpSession httpSession;



    @Override
    public UserChkExistNickNmDTO chkExistNickNm(UserChkExistNickNmDTO dto) {
        if (userRepository.existsByNickNm(dto.getNickNm())) {
            dto.setExistRslt(true);
        }
        return dto;
    }

    @Override
    public UserEntity getUser() {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId==null) {
            throw new SessionUserNotFoundException();
        }
        return userRepository.findById(userId)
                .orElseThrow(SessionUserNotFoundException::new);
    }


    @Override
    public OauthDTO getCurrentUser() {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId==null) {
            throw new SessionUserNotFoundException();
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));

        return OauthDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickNm(user.getNickNm())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public MyPageDTO getMyPage() {
        UserEntity user = getUser();

        int formCount = userRepository.countFormsByUser(user);

        return MyPageDTO.builder()
                .userId(user.getUserId())
                .nickNm(user.getNickNm())
                .email(user.getEmail())
                .profileImgUrl(user.getProfileImgUrl())
                .formCount(formCount)
                .nickChangeLeft(3 - user.getNickChangeCount())
                .createdAt(user.getCreatedAt())
                .build();
    }



    @Override
    @Transactional
    public void updateUserProfile(UserInfoUpdateDTO dto) {

        UserEntity user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new UserNotFound(dto.getUserId()));

        if (dto.getNickNm() != null && !dto.getNickNm().isBlank()) {

            // 1) 중복 체크
            if (userRepository.existsByNickNm(dto.getNickNm())) {
                throw new ResourceUpdateFailedException("이미 사용 중인 닉네임입니다.");
            }

            // 2) 닉네임 변경 제한 (60일 정책)
            user.validateNicknameChangeAllowed();

            // 3) 변경 및 변경 날짜 기록
            user.changeNickName(dto.getNickNm());
        }
    }


    // TODO : 사용자 폼 리스트 조회 메소드 추가

    @Override
    @Transactional
    public void updateUserProfileImage(Long userId, String url) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));

        user.changeProfileImage(url);
    }

    @Override
    @Transactional
    public void deleteUser(Long usedId) {
        userRepository.deleteById(usedId);
    }

    @Override
    @Transactional
    public AddressBookRequestDTO createAddressBook(AddressBookRequestDTO dto, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));

        if (addressBookRepository.existsByUser_UserIdAndAlias(userId, dto.getAlias())) {
            throw new AliasConflictException(dto.getAlias());
        }

        // 처음 등록이면 자동 default
        boolean isFirstAddress = addressBookRepository.countByUser_UserId(userId)==0;
        if (isFirstAddress) {
            dto.setDefaultAddress(true);
        }

        AddressBookEntity addressBook = AddressBookMapper.toEntity(dto, user);
        addressBookRepository.save(addressBook);
        return dto;
    }

    @Override
    public AddressBookListResponseDTO getAddressBook(Long userId) {
        List<AddressBookEntity> userAddressBook = addressBookRepository.findByUser_UserId(userId);
        return AddressBookMapper.toListDTO(userAddressBook);
    }

    @Override
    public AddressBookRequestDTO updateAddressBook(AddressBookRequestDTO dto, Long userId) {

        // 2. 기존 주소록 엔티티 조회 (id가 있어야 함)
        AddressBookEntity entity = addressBookRepository.findById(dto.getId())
                .orElseThrow(AddressNotFoundException::new);

        // 4. alias 유니크 체크 (alias가 바뀌었을 때만)
        if (!entity.getAlias().equals(dto.getAlias()) &&
                addressBookRepository.existsByUser_UserIdAndAlias(userId, dto.getAlias())) {
            throw new AliasConflictException(dto.getAlias());
        }

        // 5. Builder로 기존 엔티티 기반 새 객체 생성
        AddressBookEntity updatedEntity = AddressBookMapper.buildUpdatedEntity(dto, entity);

        // 6. save() → merge → update
        addressBookRepository.save(updatedEntity);

        return dto;
    }

    @Override
    public void setDefaultAddress(Long addressId, Long userId) {
        AddressBookEntity target = addressBookRepository.findById(addressId)
                .orElseThrow(AddressNotFoundException::new);

        // 1. 기존 기본 주소 false 처리 (쿼리 한 번)
        addressBookRepository.resetDefaultAddress(addressId, userId);

        // 2. 선택한 주소를 기본으로
        target.chgDefault(true);
        addressBookRepository.save(target);
    }

    @Override
    public void delAddressBook(Long id) {
        addressBookRepository.deleteById(id);
    }

    @Override
    public AddressBookResponseDTO getMainAddress(Long userId) {
        AddressBookEntity entity = addressBookRepository
                .findByUser_UserIdAndDefaultAddressYnTrue(userId)
                .orElseThrow(DefaultAddressNotFoundException::new);

        return AddressBookMapper.toResponseDTO(entity);
    }


}
