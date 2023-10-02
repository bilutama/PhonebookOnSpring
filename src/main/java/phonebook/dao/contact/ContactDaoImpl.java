package phonebook.dao.contact;

import phonebook.model.Contact;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

//@Repository
public class ContactDaoImpl extends GenericContactDaoImpl<Contact, Long> implements ContactDao {
    public ContactDaoImpl() {
        super(Contact.class);
    }

    @Override
    public List<Contact> getContacts(String term) {
        return find(term);
    }

    @Override
    public void setDeletedByIds(List<Long> contactsIds) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Contact> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(clazz);

        Root<Contact> root = criteriaUpdate.from(clazz);

        // Update for all contacts with ids from contactsIds
        for (Long thisId : contactsIds) {
            criteriaUpdate.set("isDeleted", true);
            criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), thisId));
            entityManager.createQuery(criteriaUpdate).executeUpdate();
        }
    }

    @Override
    public List<Contact> findByPhone(String phone) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> criteriaQuery = criteriaBuilder.createQuery(clazz);

        Root<Contact> root = criteriaQuery.from(clazz);

        Predicate phonePredicate = criteriaBuilder.equal(root.get("phone"), phone);
        Predicate notDeletedPredicate = criteriaBuilder.equal(root.get("isDeleted"), false);

        criteriaQuery.where(criteriaBuilder.and(
                phonePredicate,
                notDeletedPredicate)
        );

        CriteriaQuery<Contact> select = criteriaQuery.select(root);
        TypedQuery<Contact> query = entityManager.createQuery(select);

        return query.getResultList();
    }

    @Override
    public void toggleImportant(Long contactId) {
        Contact contact = getById(contactId);
        contact.setImportant(!contact.getImportant()); // Toggle contact importance
        update(contact);
    }
}