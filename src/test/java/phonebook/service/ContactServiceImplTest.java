package phonebook.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import phonebook.dao.contact.ContactDao;
import phonebook.model.Contact;
import phonebook.model.ContactValidation;
import phonebook.service.impl.ContactServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ContactServiceImpl")
class ContactServiceTest {
	@InjectMocks
	ContactServiceImpl contactService;

	@Mock
	private ContactDao contactDao;

	@Test
	void testValidateContact_ValidContact() {
		Contact contact = new Contact();
		contact.setFirstName("John");
		contact.setLastName("Doe");
		contact.setPhone("123-456-7890");

		ContactValidation result = contactService.validateContact(contact);

		assertTrue(result.isValid());
		assertNull(result.getError());
	}

	@Test
	void testValidateContact_InvalidFirstName() {
		Contact contact = new Contact();
		contact.setFirstName("");
		contact.setLastName("Doe");
		contact.setPhone("123-456-7890");

		ContactValidation result = contactService.validateContact(contact);

		assertFalse(result.isValid());
		assertEquals("First name is required", result.getError());
	}

	// Add similar test methods for other validation scenarios

	@Test
	void testAddContact_ValidContact() {
		Contact contact = new Contact();
		contact.setFirstName("John");
		contact.setLastName("Doe");
		contact.setPhone("123-456-7890");

		//when(contactDao.create(contact)).thenReturn();

		ContactValidation result = contactService.saveContact(contact);

		assertTrue(result.isValid());
		assertNull(result.getError());
		verify(contactDao, times(1)).create(contact);
	}

	@Test
	void testAddContact_InvalidContact() {
		Contact contact = new Contact();
		contact.setFirstName("");
		contact.setLastName("Doe");
		contact.setPhone("123-456-7890");

		ContactValidation result = contactService.saveContact(contact);

		assertFalse(result.isValid());
		assertEquals("First name is required", result.getError());
		verify(contactDao, never()).create(contact);
	}

	@Test
	void testGetContacts() {
		String searchTerm = "John";
		List<Contact> expectedContacts = new ArrayList<>();

		when(contactDao.getContacts(searchTerm)).thenReturn(expectedContacts);

		List<Contact> result = contactService.getContacts(searchTerm);

		assertEquals(expectedContacts, result);
		verify(contactDao, times(1)).getContacts(searchTerm);
	}
}
