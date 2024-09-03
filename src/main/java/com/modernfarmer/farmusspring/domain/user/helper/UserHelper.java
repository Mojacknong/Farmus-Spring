package com.modernfarmer.farmusspring.domain.user.helper;

import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.user.exception.UserErrorCode;
import com.modernfarmer.farmusspring.domain.user.exception.custom.UserNotFoundException;
import com.modernfarmer.farmusspring.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHelper {

    private final UserRepository userRepository;

    public void deleteUser(Long userId){
        userRepository.deleteUser(userId);
    }

    public User getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다.", UserErrorCode.NOT_FOUND_USER));
    }

    public String getUserLevel(Long userId) {
        User user = getUserEntity(userId);
        return user.getLevel();
    }

    public String getUserNickname(Long userId) {
        User user = getUserEntity(userId);
        return user.getNickname();
    }
}
