package ru.academits.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.academits.converters.call.CallDtoToCallConverter;
import ru.academits.converters.call.CallToCallDtoConverter;
import ru.academits.converters.contact.ContactDtoToContactConverter;
import ru.academits.converters.contact.ContactToContactDtoConverter;
import ru.academits.dto.CallDto;
import ru.academits.dto.ContactDto;
import ru.academits.model.ContactValidation;
import ru.academits.service.CallService;
import ru.academits.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
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

    public PhonebookController(ContactService contactService, ContactDtoToContactConverter contactDtoToContactConverter, ContactToContactDtoConverter contactToContactDtoConverter, CallService callService, CallDtoToCallConverter callDtoToCallConverter, CallToCallDtoConverter callToCallDtoConverter) {
        this.contactService = contactService;
        this.contactDtoToContactConverter = contactDtoToContactConverter;
        this.contactToContactDtoConverter = contactToContactDtoConverter;
        this.callService = callService;
        this.callDtoToCallConverter = callDtoToCallConverter;
        this.callToCallDtoConverter = callToCallDtoConverter;
    }

    @RequestMapping(value = {"getContacts", "getContacts/{term}"}, method = RequestMethod.POST)
    @ResponseBody
    public List<ContactDto> getContacts(@PathVariable(required = false) String term) {
        // === LOGGING START ===
        String finalTerm = term == null ? "" : term;
        String logMessage = String.format("getContacts is called with term = \"%s\"", finalTerm);
        logger.info(logMessage);
        // === LOGGING END ===

        return contactToContactDtoConverter.convert(contactService.getContacts(term));
    }

    @RequestMapping(value = "addContact", method = RequestMethod.POST)
    @ResponseBody
    public ContactValidation addContact(@RequestBody ContactDto contact) {
        // === LOGGING START ===
        String logMessage = String.format("Adding contact %s %s, phone %s", contact.getFirstName(), contact.getLastName(), contact.getPhone());
        logger.info(logMessage);
        // === LOGGING END ===

        return contactService.addContact(contactDtoToContactConverter.convert(contact));
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public void setContactsAsDeleted(@RequestBody ArrayList<Long> contactIds) {
        contactService.setContactsAsDeleted(contactIds);

        // === LOGGING START ===
        String contactIdsString = contactIds.stream()
                .map(Object::toString)
                .reduce((t, u) -> t + ", " + u)
                .orElse("");

        String logMessage = String.format("Contacts with IDs = %s are set as deleted.", contactIdsString);
        logger.info(logMessage);
        // === LOGGING END ===
    }

    @RequestMapping(value = "toggleImportant/{contactId}", method = RequestMethod.POST)
    @ResponseBody
    public void toggleImportant(@PathVariable Long contactId) {
        contactService.toggleImportant(contactId);

        // === LOGGING START ===
        String logMessage = String.format("Toggling importance for contact ID=%d changed.", contactId);
        logger.info(logMessage);
        // === LOGGING END ===
    }

    @RequestMapping(value = "addCall", method = RequestMethod.POST)
    @ResponseBody
    public void addCall(@RequestBody Long callId) {
        // Add a call with info
        CallDto call = new CallDto();
        call.setCallContactId(callId);
        call.setCallTime(new Timestamp(System.currentTimeMillis()));

        callService.addCall(callDtoToCallConverter.convert(call));

        // === LOGGING START ===
        String logMessage = String.format("A contact with ID=%d was called", callId);
        logger.info(logMessage);
        // === LOGGING END ===
    }

    @RequestMapping(value = {"getCalls"}, method = RequestMethod.POST)
    @ResponseBody
    public List<CallDto> getCalls() {
        // === LOGGING START ===
        logger.info("getCalls method is called");
        // === LOGGING END ===

        return callToCallDtoConverter.convert(callService.getCalls());
    }

    @RequestMapping(value = "deleteCalls", method = RequestMethod.POST)
    @ResponseBody
    public void setCallsAsDeleted(@RequestBody ArrayList<Long> callsIds) {
        callService.setCallsAsDeleted(callsIds);

        // === LOGGING START ===
        String callsIdsString = callsIds.stream()
                .map(Object::toString)
                .reduce((t, u) -> t + ", " + u)
                .orElse("");

        String logMessage = String.format("Calls with IDs = %s are set as deleted.", callsIdsString);
        logger.info(logMessage);
        // === LOGGING END ===
    }
}