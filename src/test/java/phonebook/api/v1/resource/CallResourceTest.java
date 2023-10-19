package phonebook.api.v1.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;
import phonebook.converters.call.CallDtoToCallConverter;
import phonebook.converters.call.CallToCallDtoConverter;
import phonebook.converters.contact.ContactDtoToContactConverter;
import phonebook.dto.CallDto;
import phonebook.model.Call;
import phonebook.service.CallService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CallResource.class)
@DisplayName("Call Controller Tests")
class CallResourceTest {

	@Captor
	ArgumentCaptor<List<Long>> listArgumentCaptor;

	@MockBean
	private CallService callService;

	@MockBean
	private CallToCallDtoConverter callToCallDtoConverter;

	@MockBean
	private CallDtoToCallConverter callDtoToCallConverter;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@Disabled
	void saveCall() {
	}

	@Test
	@DisplayName("Should return all calls for the contact")
	void shouldReturnAllCalls() throws Exception {
		// Given
		Long contactId = 1L;

		Call call1 = new Call();
		Call call2 = new Call();

		List<Call> calls = List.of(call1, call2);

		CallDto callDto1 = new CallDto();
		CallDto callDto2 = new CallDto();
		List<CallDto> callDtos = List.of(callDto1, callDto2);

		String expectedJson = mapper.writeValueAsString(callDtos);

		// Mocking behaviour
		when(callService.getAllByContactId(contactId)).thenReturn(calls);
		when(callToCallDtoConverter.convert(calls)).thenReturn(callDtos);

		// Assert
		mockMvc.perform(post("/phonebook/rpc/api/v1/findCallsByContactId/{id}", contactId))
			.andExpect(status().isOk())
			.andExpect(content().json(expectedJson));
	}

	@Test
	void shouldSetCallsAsDeleted() throws Exception {
		List<Long> callIdsToDelete = List.of(1L, 2L, 3L);
		String json = mapper.writeValueAsString(callIdsToDelete);

		mockMvc.perform(post("/phonebook/rpc/api/v1/deleteCalls")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk());

		verify(callService, times(1)).setAsDeleted(listArgumentCaptor.capture());

		List<Long> capturedContactIds = listArgumentCaptor.getValue();
		assertEquals(callIdsToDelete, capturedContactIds);
	}
}