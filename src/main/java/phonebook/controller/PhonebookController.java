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
import phonebook.service.ContactServiceImpl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/phonebook/rpc/api/v1")
public class PhonebookController {
    private static final Logger logger = LoggerFactory.getLogger(PhonebookController.class);

    private final ContactServiceImpl contactServiceImpl;
    private final ContactDtoToContactConverter contactDtoToContactConverter;
    private final ContactToContactDtoConverter contactToContactDtoConverter;

    private final CallService callService;

    private final CallDtoToCallConverter callDtoToCallConverter;

    private final CallToCallDtoConverter callToCallDtoConverter;

    public PhonebookController(ContactServiceImpl contactServiceImpl, ContactDtoToContactConverter contactDtoToContactConverter, ContactToContactDtoConverter contactToContactDtoConverter, CallService callService, CallDtoToCallConverter callDtoToCallConverter, CallToCallDtoConverter callToCallDtoConverter) {
        this.contactServiceImpl = contactServiceImpl;
        this.contactDtoToContactConverter = contactDtoToContactConverter;
        this.contactToContactDtoConverter = contactToContactDtoConverter;
        this.callService = callService;
        this.callDtoToCallConverter = callDtoToCallConverter;
        this.callToCallDtoConverter = callToCallDtoConverter;
    }

    @PostMapping(value = {"getContacts", "getContacts/{term}"})
    @ResponseBody
    public List<ContactDto> getContacts(@PathVariable(required = false) String term) {
        logger.info("getContacts is called with term=\"{}\"", term == null ? "" : term);

        return contactToContactDtoConverter.convert(contactServiceImpl.getContacts(term));
    }

    @PostMapping(value = "addContact")
    @ResponseBody
    public ContactValidation addContact(@RequestBody ContactDto contact) {
        logger.info("Adding contact {} {}, phone {}", contact.getFirstName(), contact.getLastName(), contact.getPhone());

        return contactServiceImpl.addContact(contactDtoToContactConverter.convert(contact));
    }

    @PostMapping(value = "delete")
    @ResponseBody
    public void setContactsAsDeleted(@RequestBody List<Long> contactIds) {
        contactServiceImpl.setContactsAsDeleted(contactIds);

        String ids = Arrays.toString(contactIds.toArray());
        logger.info("Contacts with IDs={} are set as deleted.", ids);
    }

    @PostMapping(value = "toggleImportant/{contactId}")
    @ResponseBody
    public void toggleImportant(@PathVariable Long contactId) {
        contactServiceImpl.toggleImportant(contactId);

        logger.info("Toggling importance for contact ID={} changed", contactId);
    }

    @PostMapping(value = "addCall")
    @ResponseBody
    public void addCall(@RequestBody Long callId) {
        CallDto call = new CallDto();
        call.setCallContactId(callId);
        call.setCallTime(new Timestamp(System.currentTimeMillis()));

        callService.addCall(callDtoToCallConverter.convert(call));

        logger.info("A contact with ID={} was called", callId);
    }

    @PostMapping(value = {"getCalls", "getCalls/{contactId}"})
    @ResponseBody
    public List<CallDto> getCalls(@PathVariable(required = false) Long contactId) {
        if (contactId == null) {
            logger.info("getCalls method is called");
        } else {
            logger.info("getCalls method is called for contact with id={}", contactId);
        }

        return callToCallDtoConverter.convert(callService.getCalls(contactId));
    }

    @PostMapping(value = "deleteCalls")
    @ResponseBody
    public void setCallsAsDeleted(@RequestBody List<Long> callsIds) {
        callService.setCallsAsDeleted(callsIds);

        String ids = Arrays.toString(callsIds.toArray());
        logger.info("Calls with IDs={} are set as deleted.", ids);
    }
}