package com.homework.travel.repository;

import java.util.List;


import org.springframework.stereotype.Repository;
import com.homework.travel.domain.entity.City;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CityRepository {

    private final EntityManager em;

    public void save(City city) {
        em.persist(city);
    }
    
    public List<City> findByCityname(String cityname){
        return em.createQuery("select c from City c where c.cityname = :cityname", City.class)
        .setParameter("cityname", cityname)
        .getResultList();
    };

    public City findOne(Long id) {
        return em.find(City.class, id);
    }
    // CityInfo findByNo(int no);
}
