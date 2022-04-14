package ru.academits.dao.call;

import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Transactional
public class GenericCallDaoImpl<T, C extends Serializable> implements GenericCallDao<T, C> {
    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> clazz;

    public GenericCallDaoImpl(Class<T> type) {
        this.clazz = type;
    }

    @Transactional
    @Override
    public void create(T obj) {
        entityManager.persist(obj);
    }

    @Transactional
    @Override
    public void remove(T obj) {
        entityManager.remove(obj);
    }

    @Override
    public T getById(C id) {
        return entityManager.find(clazz, id);
    }

    @Transactional
    @Override
    public List<T> find(Long contactId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);

        Root<T> root = criteriaQuery.from(clazz);

        if (contactId != null) {
            criteriaQuery.where(criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("isDeleted"), false),
                    criteriaBuilder.equal(root.get("callContactId"), contactId))
            );
        } else {
            criteriaQuery.where(criteriaBuilder.equal(root.get("isDeleted"), false));
        }

        CriteriaQuery<T> select = criteriaQuery.select(root);
        TypedQuery<T> typedQuery = entityManager.createQuery(select);

        return typedQuery.getResultList();
    }
}