package phonebook.dao.contact;

import phonebook.model.Contact;

import java.util.List;

public interface ContactDao extends GenericContactDao<Contact, Long> {
	List<Contact> getContacts(String term);

	List<Contact> findByPhone(String phone);

	void setDeletedByIds(List<Long> contactsIds);

	void toggleImportant(Long contactId);
}