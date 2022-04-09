package ru.academits.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.academits.converter.ContactDtoToContactConverter;
import ru.academits.converter.ContactToContactDtoConverter;
import ru.academits.dto.ContactDto;
import ru.academits.model.ContactValidation;
import ru.academits.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/phonebook/rpc/api/v1")
public class PhonebookController {
    private static final boolean INCLUDE_DELETED = false;

    private static final Logger logger = LoggerFactory.getLogger(PhonebookController.class);

    private final ContactService contactService;
    private final ContactDtoToContactConverter contactDtoToContactConverter;
    private final ContactToContactDtoConverter contactToContactDtoConverter;

    public PhonebookController(ContactService contactService, ContactDtoToContactConverter contactDtoToContactConverter, ContactToContactDtoConverter contactToContactDtoConverter) {
        this.contactService = contactService;
        this.contactDtoToContactConverter = contactDtoToContactConverter;
        this.contactToContactDtoConverter = contactToContactDtoConverter;
    }

    @RequestMapping(value = {"getContacts/", "getContacts/{term}"}, method = RequestMethod.POST)
    @ResponseBody
    public List<ContactDto> getContacts(@PathVariable(required = false) String term) {
        // === LOGGING START ===
        String finalTerm = term == null ? "" : term;
        String logMessage = String.format("getContacts is called with term = \"%s\"", finalTerm);
        logger.info(logMessage);
        // === LOGGING END ===

        return contactToContactDtoConverter.convert(contactService.getContacts(term, INCLUDE_DELETED));
    }

    @RequestMapping(value = "addContact", method = RequestMethod.POST)
    @ResponseBody
    public ContactValidation addContact(@RequestBody ContactDto contact) {
        return contactService.addContact(contactDtoToContactConverter.convert(contact));
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public boolean setContactsAsDeleted(@RequestBody ArrayList<Long> contactIds) {
        boolean contactAreSetAsDeleted = contactService.setContactsAsDeleted(contactIds);

        // === LOGGING START ===
        String contactIdsString = contactIds.stream()
                .map(Object::toString)
                .reduce((t, u) -> t + ", " + u)
                .orElse("");

        String logMessage;

        if (contactAreSetAsDeleted) {
            logMessage = String.format("Contacts with IDs = %s are set as deleted.", contactIdsString);
        } else {
            logMessage = String.format("Contacts with IDs = %s were not set as deleted. Possible reason: no such contacts in the database.", contactIdsString);
        }

        logger.info(logMessage);
        // === LOGGING END ===

        return contactAreSetAsDeleted;
    }
}


