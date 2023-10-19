package phonebook.service;

import phonebook.model.Contact;
import phonebook.model.ContactValidation;

import java.util.List;

public interface ContactService {

	ContactValidation validate(Contact contact);

	ContactValidation save(Contact contact);

	List<Contact> findByTerm(String term);

	void setAsDeleted(List<Long> contactsIds);

	void toggleImportant(Long contactId);
}