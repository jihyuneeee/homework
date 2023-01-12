package com.homework.travel.repository;

import java.time.LocalDateTime;
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

    public List<City> findByCityname(String cityname) {
        return em.createQuery("select c from City c where c.cityname = :cityname", City.class)
                .setParameter("cityname", cityname)
                .getResultList();
    };

    public List<City> findById(Long id) {

        return em
                .createQuery(
                        "select c from City c join fetch c.travels t where c.id = :id ",
                        City.class)
                .setParameter("id", id)
                .getResultList();
    };

    public City findOne(Long id) {
        return em.find(City.class, id);
    }

    public Long delete(City city) {
        em.remove(city);
        return city.getId();
    }

    // CityInfo findByNo(int no);
}
