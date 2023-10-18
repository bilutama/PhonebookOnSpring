package phonebook.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Contact Entity equals() and hashCode()")
class ContactTest {
	@Test
	@DisplayName("equals()")
	void shouldTestEquals() {
		// Given set of contacts
		Contact contact1 = new Contact("John", "Doe", "+1234567890");
		Contact contact2 = new Contact("John", "Doe", "+1234567890");
		Object object = new Object();

		Contact contact3 = new Contact("Jane", "Doe", "+1234567890");
		Contact contact4 = new Contact("John", "Dow", "+1234567890");
		Contact contact5 = new Contact("John", "Doe", "+1234567809");

		// Asserts
		assertEquals(contact1, contact1);
		assertEquals(contact1, contact2);

		assertNotEquals(contact1, null);
		assertNotEquals(contact1, object);

		assertNotEquals(contact1, contact3);
		assertNotEquals(contact1, contact4);
		assertNotEquals(contact1, contact5);
	}

	@Test
	@DisplayName("hashCode()")
	void shouldTestHashCode() {
		Contact contact1 = new Contact("John", "Doe", "+1234567890");
		Contact contact2 = new Contact("John", "Doe", "+1234567890");

		assertEquals(contact1.hashCode(), contact2.hashCode());
	}
}
