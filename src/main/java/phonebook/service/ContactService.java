package phonebook.service;

import phonebook.model.Contact;
import phonebook.model.ContactValidation;

import java.util.List;

public interface ContactService {

	ContactValidation validateContact(Contact contact);

	ContactValidation saveContact(Contact contact);

	List<Contact> findContacts(String term);

	void setContactsAsDeleted(List<Long> contactsIds);

	void toggleImportant(Long contactId);
}