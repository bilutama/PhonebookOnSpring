package phonebook.service;

import org.springframework.stereotype.Service;
import phonebook.model.Contact;
import phonebook.model.ContactValidation;
import phonebook.repository.ContactRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {
	private final ContactRepository contactRepository;

	public ContactServiceImpl(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	private boolean isExistContactWithPhone(String phone) {
		Optional<Contact> contact = contactRepository.findByPhone(phone);
		return contact.isPresent();
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

	public ContactValidation saveContact(Contact contact) {
		ContactValidation contactValidation = validateContact(contact);

		if (contactValidation.isValid()) {
			contactRepository.save(contact);
		}

		return contactValidation;
	}

	public List<Contact> getContacts(String term) {
		return contactRepository.findContacts(term);
	}

	public void setContactsAsDeleted(List<Long> contactsIds) {
		contactRepository.setDeletedByIds(contactsIds);
	}

	public void toggleImportant(Long contactId) {
		contactRepository.toggleImportant(contactId);
	}
}