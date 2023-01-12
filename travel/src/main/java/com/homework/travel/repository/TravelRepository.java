package com.homework.travel.repository;

import org.springframework.stereotype.Repository;

import com.homework.travel.domain.entity.Travel;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TravelRepository {

    private final EntityManager em;

    public void save(Travel travel) {
        em.persist(travel);
    }

    public Long delete(Travel travel) {
        em.remove(travel);
        return travel.getId();
    }

    public Travel findOne(Long id) {
        return em.find(Travel.class, id);
    }

}
