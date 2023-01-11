package com.homework.travel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homework.travel.domain.entity.City;
import com.homework.travel.repository.CityRepository;

import lombok.RequiredArgsConstructor;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityService {
    
    private final CityRepository cityRepository;
    
    /**
     * 도시 등록 
     */
    @Transactional
    public Long register(City city) {

        validateDuplicateCity(city); // 중복 도시 검증
        cityRepository.save(city);
        return city.getId();
    }

    /**
     * 도시 수정
     */
    @Transactional
    public void update(Long id, String name) {
        City city = cityRepository.findOne(id);
        city.setCityname(name);
    }

    public City findOne(Long id) {
        return cityRepository.findOne(id);
    }

    private void validateDuplicateCity(City city) {
        List<City> findCities = cityRepository.findByCityname(city.getCityname());
        if (!findCities.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 도시입니다.");
        }
    }
    
}
