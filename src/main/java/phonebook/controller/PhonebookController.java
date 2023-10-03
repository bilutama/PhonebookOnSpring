package phonebook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import phonebook.converters.call.CallDtoToCallConverter;
import phonebook.converters.call.CallToCallDtoConverter;
import phonebook.converters.contact.ContactDtoToContactConverter;
import phonebook.converters.contact.ContactToContactDtoConverter;
import phonebook.dto.CallDto;
import phonebook.dto.ContactDto;
import phonebook.model.ContactValidation;
import phonebook.service.CallService;
import phonebook.service.ContactService;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/phonebook/rpc/api/v1")
public class PhonebookController {
	private static final Logger logger = LoggerFactory.getLogger(PhonebookController.class);

	private final ContactService contactService;

	private final ContactDtoToContactConverter contactDtoToContactConverter;

	private final ContactToContactDtoConverter contactToContactDtoConverter;

	private final CallService callService;

	private final CallDtoToCallConverter callDtoToCallConverter;

	private final CallToCallDtoConverter callToCallDtoConverter;

	public PhonebookController(
		ContactService contactService,
		ContactDtoToContactConverter contactDtoToContactConverter,
		ContactToContactDtoConverter contactToContactDtoConverter,
		CallService callService,
		CallDtoToCallConverter callDtoToCallConverter,
		CallToCallDtoConverter callToCallDtoConverter
	) {
		this.contactService = contactService;
		this.contactDtoToContactConverter = contactDtoToContactConverter;
		this.contactToContactDtoConverter = contactToContactDtoConverter;
		this.callService = callService;
		this.callDtoToCallConverter = callDtoToCallConverter;
		this.callToCallDtoConverter = callToCallDtoConverter;
	}

	@PostMapping(value = {"findContacts", "findContacts/{term}"})
	@ResponseBody
	public List<ContactDto> getContacts(@PathVariable(required = false) String term) {
		logger.info(
			"Received POST request to find {}contacts with term=\"{}\"",
			term == null ? "all " : "",
			term == null ? "" : "with term=\"" + term + "\""
		);

		return contactToContactDtoConverter.convert(contactService.getContacts(term));
	}

	@PostMapping(value = "saveContact")
	@ResponseBody
	public ContactValidation saveContact(@RequestBody ContactDto contact) {
		logger.info(
			"Received POST request to save a new contact for {} {}, phone {}",
			contact.getFirstName(),
			contact.getLastName(),
			contact.getPhone()
		);

		return contactService.saveContact(contactDtoToContactConverter.convert(contact));
	}

	@PostMapping(value = "deleteContacts")
	@ResponseBody
	public void setContactsAsDeleted(@RequestBody List<Long> contactIds) {
		contactService.setContactsAsDeleted(contactIds);

		String ids = Arrays.toString(contactIds.toArray());
		logger.info("Received POST request to set contacts with IDs={} as deleted", ids);
	}

	@PostMapping(value = "toggleImportant/{contactId}")
	@ResponseBody
	public void toggleImportant(@PathVariable Long contactId) {
		contactService.toggleImportant(contactId);

		logger.info("Received POST request to toggle importance for contact ID={}", contactId);
	}

	@PostMapping(value = "saveCall")
	@ResponseBody
	public void saveCall(@RequestBody Long callId) {
		CallDto call = new CallDto();
		call.setCallContactId(callId);
		call.setCallTime(new Timestamp(System.currentTimeMillis()));

		callService.saveCall(callDtoToCallConverter.convert(call));

		logger.info("Received POST request to call the contact with ID={}", callId);
	}

	@PostMapping(value = {"findCalls", "getCalls/{callContactId}"})
	@ResponseBody
	public List<CallDto> findCalls(@PathVariable(required = false) Long callContactId) {
		logger.info(
			"Received POST request to find{} calls{}",
			callContactId == null ? " all" : "",
			callContactId == null ? "" : " for contact with id=" + callContactId
		);

		return callToCallDtoConverter.convert(callService.getCalls(callContactId));
	}

	@PostMapping(value = "deleteCalls")
	@ResponseBody
	public void setCallsAsDeleted(@RequestBody List<Long> callsIds) {
		callService.setCallsAsDeleted(callsIds);

		String ids = Arrays.toString(callsIds.toArray());
		logger.info("Received POST request to set calls with IDs={} as deleted.", ids);
	}
}