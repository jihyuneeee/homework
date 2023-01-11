package com.homework.travel.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homework.travel.domain.entity.CityInfo;
import com.homework.travel.domain.repository.CityRepository;
import com.homework.travel.domain.response.ResponseInfo;

// import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public ResponseInfo cityRegister(CityInfo cityInfo) {

        // 1. City 존재 여부 확인
        Optional<CityInfo> data = cityRepository.findByCityname(cityInfo.getCityname());

        if (data.isPresent()) {
            ResponseInfo response = ResponseInfo.builder()
                    .status("fail")
                    .message("this city already exists.")
                    .build();
            return response;
        }

        // 2. City 등록
        cityRepository.save(cityInfo);

        ResponseInfo response = ResponseInfo.builder()
                .status("success")
                .message("city registration success.")
                .build();

        return response;
    }

    @Transactional
    @Override
    public ResponseInfo cityChange(Long id, CityInfo cityInfo) {

        return cityRepository.findById(id)
                .map(city -> {
                    city.setCityname(cityInfo.getCityname());
                    cityRepository.save(city);

                    ResponseInfo response = ResponseInfo.builder()
                            .status("success")
                            .message("city registration success.")
                            .build();

                    return response;
                }).orElseGet(() -> {

                    ResponseInfo response = ResponseInfo.builder()
                            .status("fail")
                            .message("this city not exists.")
                            .build();
                    return response;

                });

    }

    @Override
    public ResponseInfo getCity(Long id) {

        return cityRepository.findById(id).map(city -> {
            ResponseInfo response = ResponseInfo.builder()
                    .code(201)
                    .status("success")
                    .message("city registration success.")
                    .result(Arrays.asList(city))
                    .build();

            return response;
        }).orElseGet(() -> {
            ResponseInfo response = ResponseInfo.builder()
                    .status("fail")
                    .message("this city not exists.")
                    .result(Collections.emptyList())
                    .build();
            return response;
        });
    }

}
