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

    @RequestMapping(value = {"getContacts/", "getContacts/{term}"}, method = RequestMethod.POST)
    @ResponseBody
    public List<ContactDto> getContacts(@PathVariable(required = false) String term) {
        // === LOGGING START ===
        String logMessage = String.format("getContacts is called with term = \"%s\"", term == null ? "" : term);
        logger.info(logMessage);
        // === LOGGING END ===

        return contactToContactDtoConverter.convert(contactService.getAllContacts());
    }

    @RequestMapping(value = "addContact", method = RequestMethod.POST)
    @ResponseBody
    public ContactValidation addContact(@RequestBody ContactDto contact) {
        return contactService.addContact(contactDtoToContactConverter.convert(contact));
    }
}


