package io.bayrktlihn.repository.impl;

import io.bayrktlihn.entity.District;
import io.bayrktlihn.entity.Neighbourhood;
import io.bayrktlihn.repository.NeighbourhoodRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NeighbourhoodRepositoryImpl implements NeighbourhoodRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public List<? extends Neighbourhood> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Neighbourhood> neighbourhoodJPOLQuery = entityManager.createQuery("select n from Neighbourhood n", Neighbourhood.class);
            return neighbourhoodJPOLQuery.getResultList();
        }
    }

    @Override
    public Neighbourhood findById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.find(Neighbourhood.class, id);
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
    public Neighbourhood save(Neighbourhood entity) {
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
