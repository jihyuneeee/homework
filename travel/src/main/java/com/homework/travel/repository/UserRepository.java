package com.homework.travel.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.homework.travel.domain.entity.User;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }
    
    public List<User> findByUsername(String username){
        return em.createQuery("select c from City c where c.cityname = :cityname", User.class)
        .setParameter("username", username)
        .getResultList();
    };

    public User findOne(Long id) {
        return em.find(User.class, id);
    }
    
}
