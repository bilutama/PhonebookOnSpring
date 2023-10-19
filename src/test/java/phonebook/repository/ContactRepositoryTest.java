package phonebook.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import phonebook.model.Contact;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ContactRepositoryTest {

	@Autowired
	private ContactRepository contactRepository;

	@BeforeEach
	void setContactRepository() {
		// Create sample contacts that match the search term
		Contact contact1 = new Contact("John", "Doe", "+1234567890");
		Contact contact2 = new Contact("Jane", "Smith", "+1098765432");
		Contact contact3 = new Contact("Mark", "Smith", "+1312312558");

		contactRepository.saveAll(List.of(contact1, contact2, contact3));
	}

	@Test
	@DisplayName("Searching all contacts in the repository")
	void shouldFindAllContacts() {
		// Given
		Contact contact1 = new Contact("John", "Doe", "+1234567890");
		Contact contact2 = new Contact("Jane", "Smith", "+1098765432");
		Contact contact3 = new Contact("Mark", "Smith", "+1312312558");
		List<Contact> expected = List.of(contact1, contact2, contact3);

		// Find in the repository
		List<Contact> result = contactRepository.findContacts("");

		// Assert
		assertEquals(expected, result);
	}

	@Test
	@DisplayName("Searching contacts in the repository by search term")
	void shouldFindContactBySearchTerm() {
		// Given
		Contact contact1 = new Contact("Jane", "Smith", "+1098765432");
		Contact contact2 = new Contact("Mark", "Smith", "+1312312558");

		Contact contact = new Contact("John", "Doe", "+1234567890");
		List<Contact> expected = List.of(contact1, contact2);
		String searchTerm = "smit";

		// Find in the repository
		List<Contact> result = contactRepository.findContacts(searchTerm);

		// Assert
		assertEquals(expected, result);
	}

	@Test
	@DisplayName("Searching not existing contacts in the repository by search term")
	void shouldNotFindAnyContactsByTerm() {
		// Given
		String searchTerm = "Maria";

		// Find in the repository
		List<Contact> result = contactRepository.findContacts(searchTerm);

		// Assert
		assertEquals(List.of(), result);
	}
}