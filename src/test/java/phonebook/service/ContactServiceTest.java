package phonebook.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.lang.Nullable;
import phonebook.model.Contact;
import phonebook.model.ContactValidation;
import phonebook.repository.ContactRepository;
import phonebook.service.impl.ContactServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Contact Service")
class ContactServiceTest {
	@InjectMocks
	ContactServiceImpl contactService;

	@Mock
	private ContactRepository contactRepository;

	@Captor
	ArgumentCaptor<List<Long>> listArgumentCaptor;

	@Captor
	ArgumentCaptor<Long> argumentCaptor;

	@Test
	@DisplayName("Contact is not validated - first name is empty")
	void shouldNotValidateContact_InvalidFirstName() {
		Contact contact = new Contact("", "Doe", "+1234567890");
		ContactValidation result = contactService.validate(contact);

		assertFalse(result.isValid());
		assertEquals("First name is required", result.getError());
	}

	@Test
	@DisplayName("Contact is not validated - last name is empty")
	void shouldNotValidateContact_InvalidLastName() {
		Contact contact = new Contact("John", "", "+1234567890");

		ContactValidation result = contactService.validate(contact);

		assertFalse(result.isValid());
		assertEquals("Last name is required", result.getError());
	}

	@Test
	@DisplayName("Contact is not validated - phone is empty")
	void shouldNotValidateContact_InvalidPhone() {
		Contact contact = new Contact("John", "Doe", "");

		ContactValidation result = contactService.validate(contact);

		assertFalse(result.isValid());
		assertEquals("Phone is required", result.getError());
	}

	@Test
	@DisplayName("Contact is validated and saved")
	void shouldValidateAndSaveContact() {
		Contact contact = new Contact("John", "Doe", "+1234567890");

		ContactValidation result = contactService.save(contact);

		assertTrue(result.isValid());
		verify(contactRepository, times(1)).save(contact);
		assertNull(result.getError());
	}

	@Test
	@DisplayName("Contact is not saved because of existing contact with the same phone")
	void shoutNotAddContactWithExistingPhone() {
		// Given - both contacts with same phone
		String phone = "+1234567890";
		Contact contact1 = new Contact("John", "Doe", phone);
		Contact contact2 = new Contact("Mike", "Norton", phone);

		// Save contact1
		ContactValidation result1 = contactService.save(contact1);

		// Asserts
		assertTrue(result1.isValid());
		verify(contactRepository, times(1)).save(contact1);

		// Mock repository behaviour
		when(contactRepository.findByPhoneAndNotDeleted(phone)).thenReturn(Optional.of(contact1));

		// Save contact2
		ContactValidation result2 = contactService.save(contact2);

		// Asserts
		assertFalse(result2.isValid());
		verify(contactRepository, never()).save(contact2);
		assertEquals("Phone provided is already exists in the phonebook", result2.getError());
	}

	@Test
	@DisplayName("Contact is found by the search term")
	void shouldFindContactsByTerm() {
		String searchTerm = "John";
		Contact contact = new Contact("John", "Doe", "+1234567890");
		List<Contact> expectedContacts = List.of(contact);

		when(contactRepository.findContacts(searchTerm)).thenReturn(expectedContacts);

		List<Contact> result = contactService.findByTerm(searchTerm);

		assertEquals(expectedContacts, result);
		verify(contactRepository, times(1)).findContacts(searchTerm);
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {" ", "  ", "   "})
	@DisplayName("All contacts are found by empty or null term")
	void shouldFindContactsByEmptyTerm(@Nullable String term) {
		Contact contact1 = new Contact("John", "Doe", "+1234567890");
		Contact contact2 = new Contact("Jane", "Smith", "+1098765432");
		List<Contact> expectedContacts = List.of(contact1, contact2);

		final String finalSearchTerm = term == null ? null : term.trim();
		when(contactRepository.findContacts(finalSearchTerm)).thenReturn(expectedContacts);

		List<Contact> result = contactService.findByTerm(term);

		assertEquals(expectedContacts, result);
		verify(contactRepository, times(1)).findContacts(finalSearchTerm);
	}

	@Test
	@DisplayName("Setting contacts as deleted")
	void shouldSetContactsAsDeleted() {
		// Given
		List<Long> contactIds = List.of(1L, 2L, 3L);

		// Call method
		contactService.setAsDeleted(contactIds);

		// Verify
		verify(contactRepository, times(1)).setDeletedByIds(listArgumentCaptor.capture());
		List<Long> capturedContactIds = listArgumentCaptor.getValue();
		assertEquals(contactIds, capturedContactIds);
	}

	@Test
	@DisplayName("Toggle contacts as important")
	void shouldToggleContactAsImportant() {
		// Given
		Long contactId = 1L;

		// Call method
		contactService.toggleImportant(contactId);

		// Verify
		verify(contactRepository, times(1)).toggleImportant(argumentCaptor.capture());
		Long capturedContactId = argumentCaptor.getValue();
		assertEquals(contactId, capturedContactId);
	}
}