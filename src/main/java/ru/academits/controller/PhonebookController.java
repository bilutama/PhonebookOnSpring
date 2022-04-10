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
    private static final Logger logger = LoggerFactory.getLogger(PhonebookController.class);

    private final ContactService contactService;
    private final ContactDtoToContactConverter contactDtoToContactConverter;
    private final ContactToContactDtoConverter contactToContactDtoConverter;

    public PhonebookController(ContactService contactService, ContactDtoToContactConverter contactDtoToContactConverter, ContactToContactDtoConverter contactToContactDtoConverter) {
        this.contactService = contactService;
        this.contactDtoToContactConverter = contactDtoToContactConverter;
        this.contactToContactDtoConverter = contactToContactDtoConverter;
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
}