package com.homework.travel.service;

import org.springframework.stereotype.Service;

import com.homework.travel.domain.entity.User;
import com.homework.travel.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
 
    private final UserRepository userRepository;

    /**
     * 도시 등록 
     */
    @Transactional
    public Long register(User user) {

        // validateDuplicateCity(user); // 중복 도시 검증
        userRepository.save(user);
        return user.getId();
    }

}
