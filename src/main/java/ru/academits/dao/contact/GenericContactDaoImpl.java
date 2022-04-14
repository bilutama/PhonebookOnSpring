package ru.academits.dao.contact;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class GenericContactDaoImpl<T, PK extends Serializable> implements GenericContactDao<T, PK> {
    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> clazz;

    public GenericContactDaoImpl(Class<T> type) {
        this.clazz = type;
    }

    @Transactional
    @Override
    public void create(T obj) {
        entityManager.persist(obj);
    }

    @Transactional
    @Override
    public void update(T obj) {
        entityManager.merge(obj);
    }

    @Transactional
    @Override
    public void remove(T obj) {
        entityManager.remove(obj);
    }

    @Override
    public T getById(PK id) {
        return entityManager.find(clazz, id);
    }

    @Transactional
    @Override
    public List<T> find(String term) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);

        Root<T> root = criteriaQuery.from(clazz);

        List<Predicate> predicates = new ArrayList<>();
        Predicate notDeletedPredicate = criteriaBuilder.equal(root.get("isDeleted"), false);
        predicates.add(notDeletedPredicate);

        // Add predicates to filter if term is passed
        if (term != null && !term.equals("")) {
            String finalTerm = "%" + term + "%";

            Predicate contactContentsPredicate = criteriaBuilder.or(
                    criteriaBuilder.like(root.get("firstName"), finalTerm),
                    criteriaBuilder.like(root.get("lastName"), finalTerm),
                    criteriaBuilder.like(root.get("phone"), finalTerm)
            );

            predicates.add(contactContentsPredicate);
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new)));

        CriteriaQuery<T> select = criteriaQuery.select(root);
        TypedQuery<T> typedQuery = entityManager.createQuery(select);

        return typedQuery.getResultList();
    }
}