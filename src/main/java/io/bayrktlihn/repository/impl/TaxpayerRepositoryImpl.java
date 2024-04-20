package io.bayrktlihn.repository.impl;

import io.bayrktlihn.entity.Taxpayer;
import io.bayrktlihn.repository.TaxpayerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaxpayerRepositoryImpl implements TaxpayerRepository {

    private final EntityManagerFactory entityManagerFactory;


    @Override
    public List<? extends Taxpayer> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Taxpayer> countryJPOLQuery = entityManager.createQuery("select t from Taxpayer t", Taxpayer.class);
            return countryJPOLQuery.getResultList();
        }
    }

    @Override
    public Taxpayer findById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.find(Taxpayer.class, id);
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
    public Taxpayer save(Taxpayer entity) {
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

    @Override
    public List<Taxpayer> findAllRealTaxpayers() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Taxpayer> countryJPOLQuery = entityManager.createQuery("select t from Taxpayer t inner join fetch t.realTaxpayer", Taxpayer.class);
            return countryJPOLQuery.getResultList();
        }
    }
}
