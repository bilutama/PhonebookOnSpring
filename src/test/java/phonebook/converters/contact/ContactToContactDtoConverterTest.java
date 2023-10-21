package phonebook.converters.contact;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import phonebook.dto.ContactDto;
import phonebook.model.Contact;

import java.util.List;

@DisplayName("Contact To ContactDto Converter")
class ContactToContactDtoConverterTest {

	private ContactToContactDtoConverter converter;

	@BeforeEach
	void setUp() {
		converter = new ContactToContactDtoConverter();
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	@DisplayName("Convert a single contact")
	void testConverterForSingleContact(boolean isImportant) {
		// Given
		Contact source = new Contact();
		source.setId(1L);
		source.setFirstName("Ivan");
		source.setLastName("Ivanov");
		source.setPhone("+7-123-456-7890");
		source.setImportant(isImportant);
		source.setDeleted(false);

		// Call the method
		ContactDto result = converter.convert(source);

		// Assert
		Assertions.assertEquals(source.getId(), result.getId());
		Assertions.assertEquals(source.getFirstName(), result.getFirstName());
		Assertions.assertEquals(source.getLastName(), result.getLastName());
		Assertions.assertEquals(source.getPhone(), result.getPhone());
		Assertions.assertEquals(source.getImportant(), result.getImportant());
	}

	@ParameterizedTest
	@CsvSource({"true,false", "false,true", "true,true", "false,false"})
	@DisplayName("Convert a list of contacts")
	void testConverterForContactsDtoList(boolean isImportant1, boolean isImportant2) {
		// Given
		Contact source1 = new Contact();
		source1.setId(1L);
		source1.setFirstName("Ivan");
		source1.setLastName("Ivanov");
		source1.setPhone("+7-123-456-7890");
		source1.setImportant(isImportant1);
		source1.setDeleted(false);

		Contact source2 = new Contact();
		source2.setId(2L);
		source2.setFirstName("Peter");
		source2.setLastName("Petrov");
		source2.setPhone("+7-123-456-0987");
		source2.setImportant(isImportant2);
		source2.setDeleted(false);

		List<Contact> contactList = List.of(source1, source2);

		// Call the method
		List<ContactDto> resultList = converter.convert(contactList);

		// Asserts
		for (int i = 0; i < resultList.size(); ++i) {
			Assertions.assertEquals(contactList.get(i).getId(), resultList.get(i).getId());
			Assertions.assertEquals(contactList.get(i).getFirstName(), resultList.get(i).getFirstName());
			Assertions.assertEquals(contactList.get(i).getLastName(), resultList.get(i).getLastName());
			Assertions.assertEquals(contactList.get(i).getPhone(), resultList.get(i).getPhone());
			Assertions.assertEquals(contactList.get(i).getImportant(), resultList.get(i).getImportant());
		}
	}
}
