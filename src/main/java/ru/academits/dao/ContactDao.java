package ru.academits.dao;

import ru.academits.model.Contact;

import java.util.ArrayList;
import java.util.List;

public interface ContactDao extends GenericDao<Contact, Long> {
    List<Contact> getContacts(String term);

    List<Contact> findByPhone(String phone);

    void setDeletedByIds(ArrayList<Long> contactsIds);
}