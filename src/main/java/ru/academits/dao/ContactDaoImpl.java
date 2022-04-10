package ru.academits.dao;

import org.springframework.stereotype.Repository;
import ru.academits.model.Contact;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContactDaoImpl extends GenericDaoImpl<Contact, Long> implements ContactDao {
    public ContactDaoImpl() {
        super(Contact.class);
    }

    @Override
    public List<Contact> getContacts(String term) {
        return find(term);
    }

    @Override
    public void setDeletedByIds(ArrayList<Long> contactsIds) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Contact> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(clazz);

        Root<Contact> root = criteriaUpdate.from(clazz);
        criteriaUpdate.set("isDeleted", false);

        // Update for all contacts with ids from contactsIds
        for (Long id : contactsIds) {
            criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), id));
            entityManager.createQuery(criteriaUpdate).executeUpdate();
        }
    }

    @Override
    public List<Contact> findByPhone(String phone) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> criteriaQuery = criteriaBuilder.createQuery(clazz);

        Root<Contact> root = criteriaQuery.from(clazz);

        criteriaQuery.where(criteriaBuilder.equal(root.get("phone"), phone));

        CriteriaQuery<Contact> select = criteriaQuery.select(root);
        TypedQuery<Contact> query = entityManager.createQuery(select);

        return query.getResultList();
    }
}