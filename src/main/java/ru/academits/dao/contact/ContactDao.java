package ru.academits.dao.contact;

import ru.academits.model.Contact;

import java.util.ArrayList;
import java.util.List;

public interface ContactDao extends GenericContactDao<Contact, Long> {
    List<Contact> getContacts(String term);

    List<Contact> findByPhone(String phone);

    void setDeletedByIds(ArrayList<Long> contactsIds);

    void toggleImportant(Long contactId);
}