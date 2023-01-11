package com.homework.travel.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homework.travel.domain.entity.CityInfo;

public interface CityRepository extends JpaRepository<CityInfo, Long> {

    Optional<CityInfo> findByCityname(String cityname);

    // CityInfo findByNo(int no);
}
