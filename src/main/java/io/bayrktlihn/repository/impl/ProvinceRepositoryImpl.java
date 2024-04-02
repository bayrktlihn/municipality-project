package io.bayrktlihn.repository.impl;

import io.bayrktlihn.entity.Municipality;
import io.bayrktlihn.entity.Province;
import io.bayrktlihn.repository.ProvinceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class ProvinceRepositoryImpl implements ProvinceRepository {


    private final EntityManagerFactory entityManagerFactory;

    @Override
    public List<? extends Province> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            TypedQuery<Province> provinceJPOLQuery = entityManager.createQuery("select p from Province p" , Province.class);
            return provinceJPOLQuery.getResultList();
        }


    }

    @Override
    public Province findById(Long id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.find(Province.class, id);
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
    public Province save(Province entity) {

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
