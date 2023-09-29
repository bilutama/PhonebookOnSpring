package phonebook.service;

import org.springframework.stereotype.Service;
import phonebook.dao.contact.ContactDao;
import phonebook.model.Contact;
import phonebook.model.ContactValidation;

import java.util.List;

@Service
public class ContactServiceImpl {
	private final ContactDao contactDao;

	public ContactServiceImpl(ContactDao contactDao) {
		this.contactDao = contactDao;
	}

	private boolean isExistContactWithPhone(String phone) {
		List<Contact> contactList = contactDao.findByPhone(phone);
		return !contactList.isEmpty();
	}

	public ContactValidation validateContact(Contact contact) {
		ContactValidation contactValidation = new ContactValidation();
		contactValidation.setValid(true);

		if (contact.getFirstName().isEmpty()) {
			contactValidation.setValid(false);
			contactValidation.setError("First name is required");
			return contactValidation;
		}

		if (contact.getLastName().isEmpty()) {
			contactValidation.setValid(false);
			contactValidation.setError("Last name is required");
			return contactValidation;
		}

		if (contact.getPhone().isEmpty()) {
			contactValidation.setValid(false);
			contactValidation.setError("Telephone is required");
			return contactValidation;
		}

		if (isExistContactWithPhone(contact.getPhone())) {
			contactValidation.setValid(false);
			contactValidation.setError("Phone provided is already exists in the phonebook");
			return contactValidation;
		}

		return contactValidation;
	}

	public ContactValidation addContact(Contact contact) {
		ContactValidation contactValidation = validateContact(contact);

		if (contactValidation.isValid()) {
			contactDao.create(contact);
		}

		return contactValidation;
	}

	public List<Contact> getContacts(String term) {
		return contactDao.getContacts(term);
	}

	public void setContactsAsDeleted(List<Long> contactsIds) {
		contactDao.setDeletedByIds(contactsIds);
	}

	public void toggleImportant(Long contactId) {
		contactDao.toggleImportant(contactId);
	}
}