package com.homework.travel.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.homework.travel.domain.entity.City;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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

    public List<City> findByUserId(Long userId, LocalDateTime now){

        return em.createQuery(
                "select  "+
                " c ,"+
                // " c.cityname, "+
                " ("+
                "    CASE "+
                "       WHEN DATE_FORMAT(t.startDate, '%Y-%m-%d HH:mm:ss') > DATE_FORMAT(:nowdate, '%Y-%m-%d HH:mm:ss') THEN 1 "+
                "       WHEN DATE_FORMAT(c.regDate,'%Y-%m-%d HH:mm:ss') = DATE_FORMAT(:nowdate, '%Y-%m-%d HH:mm:ss') THEN 2 "+
                "       WHEN DATE_FORMAT(c.inqryDate,'%Y-%m-%d HH:mm:ss') = DATE_FORMAT(:afterweek, '%Y-%m-%d HH:mm:ss') THEN 3 "+
                "       ELSE 4 END "+
                " ) test "+
                
                " from City c" +
                " join fetch c.travels t" +
                " where t.user.id = :id " +
                " and not date_format(:nowdate, '%Y-%m-%d') BETWEEN date_format(t.startDate, '%Y-%m-%d') AND date_format(t.endDate, '%Y-%m-%d') "
                // " order by "+ 
                // " test ASC, "+
                // " (CASE WHEN DATE_FORMAT(t.startDate, '%Y-%m-%d HH:mm:ss') > DATE_FORMAT(:nowdate, '%Y-%m-%d HH:mm:ss') THEN t.startDate END ) DESC, "+
                // " (CASE WHEN DATE_FORMAT(c.regDate,'%Y-%m-%d HH:mm:ss') = DATE_FORMAT(:nowdate, '%Y-%m-%d HH:mm:ss') THEN  THEN c.regDate END) DESC, "+
                // " (CASE WHEN WHEN DATE_FORMAT(c.inqryDate,'%Y-%m-%d HH:mm:ss') = DATE_FORMAT(:afterdate, '%Y-%m-%d HH:mm:ss')  THEN c.inqryDate END) DESC "
                 , City.class)
        .setParameter("id", userId)
        .setParameter("nowdate", now)
        .setParameter("afterweek", now.plusDays(7))
        .setFirstResult(0)
        .setMaxResults(10)
        .getResultList();
    }

    public City findOne(Long id) {
        return em.find(City.class, id);
    }

    public Long delete(City city) {
        em.remove(city);
        return city.getId();
    }

    /**
     * JPA Criteria
     */
    public List<City> findAllByCriteria(CitySearch citySearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<City> cq = cb.createQuery(City.class);
        Root<City> o = cq.from(City.class);
        Join<Object, Object> m = o.join("travels", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        // //주문 상태 검색
        // if (citySearch.getOrderStatus() != null) {
        //     Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
        //     criteria.add(status);
        // }
        // //회원 이름 검색
        // if (StringUtils.hasText(citySearch.getMemberName())) {
        //     Predicate name =
        //             cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
        //     criteria.add(name);
        // }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<City> query = em.createQuery(cq).setMaxResults(10);
        return query.getResultList();
    }
}
