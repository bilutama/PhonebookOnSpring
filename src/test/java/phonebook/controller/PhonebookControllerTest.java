package phonebook.controller;

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
import org.springframework.test.web.servlet.ResultActions;
import phonebook.converters.call.CallDtoToCallConverter;
import phonebook.converters.call.CallToCallDtoConverter;
import phonebook.converters.contact.ContactDtoToContactConverter;
import phonebook.converters.contact.ContactToContactDtoConverter;
import phonebook.dto.ContactDto;
import phonebook.model.Contact;
import phonebook.service.CallService;
import phonebook.service.ContactService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhonebookController.class)
@DisplayName("Phonebook Controller")
class PhonebookControllerTest {

	@Captor
	ArgumentCaptor<List<Long>> captor;
	@Autowired
	private ObjectMapper mapper;
	@MockBean
	private ContactService contactService;
	@MockBean
	private ContactDtoToContactConverter contactDtoToContactConverter;
	@MockBean
	private ContactToContactDtoConverter contactToContactDtoConverter;
	@MockBean
	private CallService callService;
	@MockBean
	private CallDtoToCallConverter callDtoToCallConverter;
	@MockBean
	private CallToCallDtoConverter callToCallDtoConverter;
	@Autowired
	private MockMvc mockMvc;

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

		final String expectedJson = mapper.writeValueAsString(contactDtos);

		// Mocking behaviour
		when(contactService.find(null)).thenReturn(contacts);
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

		final String expectedJson = mapper.writeValueAsString(contactDtos);

		// Mocking behaviour
		final String finalSearchTerm = term.trim();
		when(contactService.find(finalSearchTerm)).thenReturn(contacts);
		when(contactToContactDtoConverter.convert(contacts)).thenReturn(contactDtos);

		// Assert
		ResultActions actions = mockMvc.perform(post("/phonebook/rpc/api/v1/findContacts/{term}", term));

		actions.andExpect(status().isOk());
		actions.andExpect(content().json(expectedJson));
	}

	@Test
	@DisplayName("Setting contacts as deleted")
	void shouldSetContactsAsDeleted() throws Exception {
		List<Long> contactIdsToDelete = List.of(1L, 2L, 3L);
		String json = mapper.writeValueAsString(contactIdsToDelete);

		mockMvc.perform(post("/phonebook/rpc/api/v1/deleteContacts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk());

		verify(contactService, times(1)).setAsDeleted(captor.capture());

		List<Long> capturedContactIds = captor.getValue();
		assertEquals(contactIdsToDelete, capturedContactIds);
	}
}
