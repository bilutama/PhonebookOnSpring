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
		Contact contact1 = new Contact();
		contact1.setFirstName("John");
		contact1.setLastName("Doe");
		contact1.setPhone("123456");

		Contact contact2 = new Contact();
		contact2.setFirstName("Jane");
		contact2.setLastName("Smith");
		contact2.setPhone("789012");

		contactRepository.saveAll(List.of(contact1, contact2));
	}

	@Test
	@DisplayName("Searching all contacts in the repository")
	void shouldFindAllContacts() {
		// Given
		Contact contact1 = new Contact();
		contact1.setFirstName("John");
		contact1.setLastName("Doe");
		contact1.setPhone("123456");

		Contact contact2 = new Contact();
		contact2.setFirstName("Jane");
		contact2.setLastName("Smith");
		contact2.setPhone("789012");

		// Find in the repository
		List<Contact> result = contactRepository.findContacts("");

		// Assert
		assertEquals(List.of(contact1, contact2), result);
	}

	@Test
	@DisplayName("Searching contacts in the repository by search term")
	void shouldFindContactBySearchTerm() {
		// Given
		Contact contact1 = new Contact();
		contact1.setFirstName("John");
		contact1.setLastName("Doe");
		contact1.setPhone("123456");

		String searchTerm = "John";

		// Find in the repository
		List<Contact> result = contactRepository.findContacts(searchTerm);

		// Assert
		assertEquals(List.of(contact1), result);
	}
}