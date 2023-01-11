package com.homework.travel.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.homework.travel.domain.entity.City;
import com.homework.travel.domain.entity.Travel;
import com.homework.travel.domain.entity.User;
import com.homework.travel.repository.CityRepository;
import com.homework.travel.repository.TravelRepository;
import com.homework.travel.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelService {
    
    private final TravelRepository travelRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    /**
     * 여행 등록 
     */
    @Transactional
    public Long plan(Long cityId, Long userId, LocalDateTime startDate, LocalDateTime endDate) {

        // 1. Entity 조회
        City city = cityRepository.findOne(cityId);
        User user = userRepository.findOne(userId);
    
        // 2. Travel 등록 
        Travel travel = Travel.planTravel(city, user, startDate, endDate);

        travelRepository.save(travel);
 
        return travel.getId();
    }

}
