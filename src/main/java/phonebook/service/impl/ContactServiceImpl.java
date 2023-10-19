package phonebook.service.impl;

import org.springframework.stereotype.Service;
import phonebook.model.Contact;
import phonebook.model.ContactValidation;
import phonebook.repository.ContactRepository;
import phonebook.service.ContactService;

import java.util.List;
import java.util.Optional;

import static phonebook.Constants.ContactService.FIRST_NAME_VALIDATION_ERROR_MESSAGE;
import static phonebook.Constants.ContactService.LAST_NAME_VALIDATION_ERROR_MESSAGE;
import static phonebook.Constants.ContactService.PHONE_ALREADY_EXISTS_MESSAGE;
import static phonebook.Constants.ContactService.PHONE_VALIDATION_ERROR_MESSAGE;

@Service
public class ContactServiceImpl implements ContactService {
	private final ContactRepository contactRepository;

	public ContactServiceImpl(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	private boolean isExistContactWithPhone(String phone) {
		Optional<Contact> contact = contactRepository.findByPhoneAndNotDeleted(phone);
		return contact.isPresent();
	}

	@Override
	public ContactValidation validate(Contact contact) {
		ContactValidation contactValidation = new ContactValidation();
		contactValidation.setValid(true);

		if (contact.getFirstName().isEmpty()) {
			contactValidation.setValid(false);
			contactValidation.setError(FIRST_NAME_VALIDATION_ERROR_MESSAGE);
			return contactValidation;
		}

		if (contact.getLastName().isEmpty()) {
			contactValidation.setValid(false);
			contactValidation.setError(LAST_NAME_VALIDATION_ERROR_MESSAGE);
			return contactValidation;
		}

		if (contact.getPhone().isEmpty()) {
			contactValidation.setValid(false);
			contactValidation.setError(PHONE_VALIDATION_ERROR_MESSAGE);
			return contactValidation;
		}

		if (isExistContactWithPhone(contact.getPhone())) {
			contactValidation.setValid(false);
			contactValidation.setError(PHONE_ALREADY_EXISTS_MESSAGE);
			return contactValidation;
		}

		return contactValidation;
	}

	@Override
	public ContactValidation save(Contact contact) {
		ContactValidation contactValidation = validate(contact);

		if (contactValidation.isValid()) {
			contactRepository.save(contact);
		}

		return contactValidation;
	}

	@Override
	public List<Contact> findByTerm(String term) {
		if (term == null) {
			return contactRepository.findContacts(null);
		}

		return contactRepository.findContacts(term.trim());
	}

	@Override
	public void setAsDeleted(List<Long> contactsIds) {
		contactRepository.setDeletedByIds(contactsIds);
	}

	@Override
	public void toggleImportant(Long contactId) {
		contactRepository.toggleImportant(contactId);
	}
}