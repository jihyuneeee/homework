package com.homework.travel.service;

import java.time.LocalDateTime;
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

    @Transactional
    public City findOne(Long id) {

        City city = cityRepository.findOne(id);

        LocalDateTime now = LocalDateTime.now();
        city.setInqryDate(now);

        return city;
    }

    @Transactional
    public void deleteCity(Long id) {

        List<City> travel = cityRepository.findById(id);
        System.out.println("$$$$$$$$$$$$$$$$$$$");
        // System.out.println("city :" + city.get(0).getCityname());

        if (travel.isEmpty()) {
            System.out.println("Empty");
            City city = cityRepository.findOne(id);

            // 삭제
            Long id2 = cityRepository.delete(city);
            System.out.println("id2 :" + id2);
        }

    }

    private void validateDuplicateCity(City city) {
        List<City> findCities = cityRepository.findByCityname(city.getCityname());
        if (!findCities.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 도시입니다.");
        }
    }

}
