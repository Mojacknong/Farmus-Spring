package com.modernfarmer.farmusspring.domain.user.helper;

import com.modernfarmer.farmusspring.domain.user.entity.User;
import com.modernfarmer.farmusspring.domain.user.exception.UserNotFoundException;
import com.modernfarmer.farmusspring.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHelper {

    private final UserRepository userRepository;

    public User getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다."));
    }
}
