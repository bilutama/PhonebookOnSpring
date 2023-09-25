package phonebook.converters.contact;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import phonebook.dto.ContactDto;
import phonebook.model.Contact;

import java.util.List;

class ContactDtoToContactConverterTest {

	private ContactDtoToContactConverter converter;

	@BeforeEach
	void setUp() {
		converter = new ContactDtoToContactConverter();
	}

	@Test
	@DisplayName("Test converter - single contact")
	void testConverterForSingleContact() {
		// Given
		ContactDto source = new ContactDto();
		source.setId(1L);
		source.setFirstName("Ivan");
		source.setLastName("Ivanov");
		source.setPhone("+7-123-456-7890");
		source.setImportant(false);

		// Call the method
		Contact result = converter.convert(source);

		// Assert
		Assertions.assertEquals(source.getId(), result.getId());
		Assertions.assertEquals(source.getFirstName(), result.getFirstName());
		Assertions.assertEquals(source.getLastName(), result.getLastName());
		Assertions.assertEquals(source.getPhone(), result.getPhone());
		Assertions.assertEquals(source.getImportant(), result.getImportant());
		Assertions.assertFalse(result.getIsDeleted());
	}

	@Test
	@DisplayName("Test converter - contacts' list")
	void testConverterForContactsList() {
		// Given
		ContactDto source1 = new ContactDto();
		source1.setId(1L);
		source1.setFirstName("Ivan");
		source1.setLastName("Ivanov");
		source1.setPhone("+7-123-456-7890");
		source1.setImportant(false);

		ContactDto source2 = new ContactDto();
		source1.setId(2L);
		source1.setFirstName("Peter");
		source1.setLastName("Petrov");
		source1.setPhone("+7-123-456-0987");
		source1.setImportant(true);

		List<ContactDto> contactDtoList = List.of(source1, source2);

		// Call the method
		List<Contact> resultList = converter.convert(contactDtoList);

		// Asserts
		for (int i = 0; i < resultList.size(); ++i) {
			Assertions.assertEquals(contactDtoList.get(i).getId(), resultList.get(i).getId());
			Assertions.assertEquals(contactDtoList.get(i).getFirstName(), resultList.get(i).getFirstName());
			Assertions.assertEquals(contactDtoList.get(i).getLastName(), resultList.get(i).getLastName());
			Assertions.assertEquals(contactDtoList.get(i).getPhone(), resultList.get(i).getPhone());
			Assertions.assertEquals(contactDtoList.get(i).getImportant(), resultList.get(i).getImportant());
			Assertions.assertFalse(resultList.get(i).getIsDeleted());
		}
	}
}
