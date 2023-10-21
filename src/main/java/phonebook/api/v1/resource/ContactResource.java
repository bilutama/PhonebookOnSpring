package phonebook.api.v1.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phonebook.converters.contact.ContactDtoToContactConverter;
import phonebook.converters.contact.ContactToContactDtoConverter;
import phonebook.dto.ContactDto;
import phonebook.model.ContactValidation;
import phonebook.service.ContactService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/phonebook/rpc/api/v1")
public class ContactResource {
	private static final Logger logger = LoggerFactory.getLogger(ContactResource.class);

	private final ContactService contactService;

	private final ContactDtoToContactConverter contactDtoToContactConverter;

	private final ContactToContactDtoConverter contactToContactDtoConverter;

	public ContactResource(
		ContactService contactService,
		ContactDtoToContactConverter contactDtoToContactConverter,
		ContactToContactDtoConverter contactToContactDtoConverter
	) {
		this.contactService = contactService;
		this.contactDtoToContactConverter = contactDtoToContactConverter;
		this.contactToContactDtoConverter = contactToContactDtoConverter;
	}

	@PostMapping(value = {"findContacts", "findContacts/{term}"})
	public List<ContactDto> findContacts(@PathVariable(required = false) String term) {
		if (term == null) {
			logger.info("Received POST request to find all contacts");
		} else {
			term = term.trim();
			logger.info("Received POST request to find contacts with term=\"{}\"", term);
		}

		return contactToContactDtoConverter.convert(contactService.findByTerm(term));
	}

	@PostMapping(value = "saveContact")
	public ContactValidation saveContact(@RequestBody ContactDto contactDto) {
		logger.info(
			"Received POST request to save a new contact for {} {}, phone {}",
			contactDto.getFirstName(),
			contactDto.getLastName(),
			contactDto.getPhone()
		);

		return contactService.save(contactDtoToContactConverter.convert(contactDto));
	}

	@PostMapping(value = "deleteContacts")
	public void setContactsAsDeleted(@RequestBody List<Long> contactIds) {
		String ids = Arrays.toString(contactIds.toArray());
		logger.info("Received POST request to set contacts with IDs={} as deleted", ids);

		contactService.setAsDeleted(contactIds);
	}

	@PostMapping(value = "toggleImportant/{contactId}")
	public void toggleImportant(@PathVariable Long contactId) {
		logger.info("Received POST request to toggle importance for contact ID={}", contactId);

		contactService.toggleImportant(contactId);
	}
}