package phonebook.api.v1.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import phonebook.converters.contact.ContactDtoToContactConverter;
import phonebook.converters.contact.ContactToContactDtoConverter;
import phonebook.dto.ContactDto;
import phonebook.model.Contact;
import phonebook.model.ContactValidation;
import phonebook.service.ContactService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactResource.class)
@DisplayName("Contact Controller Tests")
class ContactResourceTest {

	@Captor
	ArgumentCaptor<List<Long>> listArgumentCaptor;

	@Captor
	ArgumentCaptor<Contact> argumentCaptorForContact;

	@Captor
	ArgumentCaptor<Long> argumentCaptorForLong;

	@MockBean
	private ContactService contactService;

	@MockBean
	private ContactToContactDtoConverter contactToContactDtoConverter;

	@MockBean
	private ContactDtoToContactConverter contactDtoToContactConverter;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@DisplayName("Should return all contacts with null term")
	void shouldReturnAllContacts() throws Exception {
		// Given
		Contact contact1 = new Contact();
		Contact contact2 = new Contact();
		List<Contact> contacts = List.of(contact1, contact2);

		ContactDto contactDto1 = new ContactDto();
		ContactDto contactDto2 = new ContactDto();
		List<ContactDto> contactDtos = List.of(contactDto1, contactDto2);

		String expectedJson = mapper.writeValueAsString(contactDtos);

		// Mocking behaviour
		when(contactService.findByTerm(null)).thenReturn(contacts);
		when(contactToContactDtoConverter.convert(contacts)).thenReturn(contactDtos);

		// Assert
		mockMvc.perform(post("/phonebook/rpc/api/v1/findContacts"))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedJson));
	}

	@ParameterizedTest
	@ValueSource(strings = {" ", "  ", "   ", "     "})
	void shouldFindAllContactsWithEmptySearchTerm(String term) throws Exception {
		// Given
		Contact contact1 = new Contact();
		Contact contact2 = new Contact();
		List<Contact> contacts = List.of(contact1, contact2);

		ContactDto contactDto1 = new ContactDto();
		ContactDto contactDto2 = new ContactDto();
		List<ContactDto> contactDtos = List.of(contactDto1, contactDto2);

		String expectedJson = mapper.writeValueAsString(contactDtos);

		// Mocking behaviour
		String emptyTerm = "";
		when(contactService.findByTerm(emptyTerm)).thenReturn(contacts);
		when(contactToContactDtoConverter.convert(contacts)).thenReturn(contactDtos);

		// Assert
		mockMvc.perform(post("/phonebook/rpc/api/v1/findContacts/{term}", term))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedJson));
	}

	@Test
	@DisplayName("Invoked setContactsAsDeleted() method in ContactService")
	void shouldSetContactsAsDeleted() throws Exception {
		List<Long> contactIdsToDelete = List.of(1L, 2L, 3L);
		String json = mapper.writeValueAsString(contactIdsToDelete);

		mockMvc.perform(post("/phonebook/rpc/api/v1/deleteContacts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk());

		verify(contactService, times(1)).setAsDeleted(listArgumentCaptor.capture());

		List<Long> capturedContactIds = listArgumentCaptor.getValue();
		assertEquals(contactIdsToDelete, capturedContactIds);
	}

	@Test
	@DisplayName("Invoked toggleContactImportance() method in ContactService")
	void shouldToggleContactImportant() throws Exception {
		Long toggledContactId = 1L;

		mockMvc.perform(post("/phonebook/rpc/api/v1/toggleImportant/{id}", toggledContactId))
			.andExpect(status().isOk());

		verify(contactService, times(1)).toggleImportant(argumentCaptorForLong.capture());

		Long capturedContactId = argumentCaptorForLong.getValue();
		assertEquals(toggledContactId, capturedContactId);
	}

	@Test
	@DisplayName("Invoked saveContact() method in ContactService")
	void shouldSaveContact() throws Exception {
		// Given
		ContactDto contactDto = new ContactDto();
		String jsonDto = mapper.writeValueAsString(contactDto);

		Contact contact = new Contact();

		ContactValidation validation = new ContactValidation();
		validation.setValid(true);
		String expectedJson = mapper.writeValueAsString(validation);

		// Mocking behaviour
		when(contactDtoToContactConverter.convert(any(ContactDto.class))).thenReturn(contact);
		when(contactService.save(contact)).thenReturn(validation);

		// Perform and assert
		mockMvc.perform(post("/phonebook/rpc/api/v1/saveContact")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonDto))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(expectedJson))
			.andExpect(status().isOk());

		verify(contactService, times(1)).save(argumentCaptorForContact.capture());
		assertEquals(argumentCaptorForContact.getValue(), contact);
	}
}