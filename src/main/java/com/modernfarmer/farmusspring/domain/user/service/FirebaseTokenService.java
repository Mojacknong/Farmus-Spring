package com.modernfarmer.farmusspring.domain.user.service;

import com.modernfarmer.farmusspring.domain.user.dto.request.SetLevelRequest;
import com.modernfarmer.farmusspring.domain.user.dto.request.SetMotivationRequest;
import com.modernfarmer.farmusspring.domain.user.entity.User;

import com.modernfarmer.farmusspring.domain.user.entity.UserFirebaseToken;
import com.modernfarmer.farmusspring.domain.user.exception.UserNotFoundException;
import com.modernfarmer.farmusspring.domain.user.repository.FirebaseTokenRepository;
import com.modernfarmer.farmusspring.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@AllArgsConstructor
@Service
public class FirebaseTokenService {

    private final FirebaseTokenRepository firebaseTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public void deleteFirebaseToken(Long userId, String firebaseToken) {
        firebaseTokenRepository.deleteToken(firebaseToken, userId );
    }


    @Transactional
    public void addFirebaseToken(Long userId, String firebaseToken){

        User user = userRepository.findUserById(userId);
        validateUser(user);
        UserFirebaseToken.createUserFirebaseToken(firebaseToken, user);
    }

    public void validateUser(User user){
        if(user == null)
            throw new UserNotFoundException("해당 유저는 존재하지 않습니다.");
    }


}
