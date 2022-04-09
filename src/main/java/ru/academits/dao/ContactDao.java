package ru.academits.dao;

import ru.academits.model.Contact;

import java.util.ArrayList;
import java.util.List;

public interface ContactDao extends GenericDao<Contact, Long> {
    List<Contact> getContacts(String term, boolean includeDeleted);

    List<Contact> findByPhone(String phone);

    boolean setDeletedByIds(ArrayList<Long> contactsIds);
}