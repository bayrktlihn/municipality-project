package io.bayrktlihn.repository.impl;

import io.bayrktlihn.entity.District;
import io.bayrktlihn.repository.DistrictRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class DistrictRepositoryImpl implements DistrictRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public List<? extends District> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<District> districtJPOLQuery = entityManager.createQuery("select d from District d", District.class);
            return districtJPOLQuery.getResultList();
        }
    }

    @Override
    public District findById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.find(District.class, id);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction tx = entityManager.getTransaction();

            try {
                tx.begin();
                entityManager.remove(findById(id));
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
            }
        }
    }

    @Override
    public District save(District entity) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction tx = entityManager.getTransaction();

            try {
                tx.begin();

                if (entity.getId() != null) {
                    entity = entityManager.merge(entity);
                } else {
                    entityManager.persist(entity);
                }

                tx.commit();
            } catch (Exception e) {
                tx.rollback();
            }
        }

        return entity;
    }
}
