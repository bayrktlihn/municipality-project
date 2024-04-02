package io.bayrktlihn.repository.impl;

import io.bayrktlihn.entity.Municipality;
import io.bayrktlihn.repository.MunicipalityRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MunicipalityRepositoryImpl implements MunicipalityRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public List<? extends Municipality> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Municipality> municipalityJPOLQuery = entityManager.createQuery("select m from Municipality m", Municipality.class);
            return municipalityJPOLQuery.getResultList();
        }


    }

    @Override
    public Municipality findById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.find(Municipality.class, id);
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
    public Municipality save(Municipality entity) {

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
