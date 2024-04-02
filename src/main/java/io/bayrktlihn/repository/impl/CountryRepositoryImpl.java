package io.bayrktlihn.repository.impl;

import io.bayrktlihn.entity.Country;
import io.bayrktlihn.entity.Municipality;
import io.bayrktlihn.repository.CountryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class CountryRepositoryImpl implements CountryRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public List<? extends Country> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Country> countryJPOLQuery = entityManager.createQuery("select c from Country c", Country.class);
            return countryJPOLQuery.getResultList();
        }
    }

    @Override
    public Country findById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.find(Country.class, id);
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
    public Country save(Country entity) {
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
