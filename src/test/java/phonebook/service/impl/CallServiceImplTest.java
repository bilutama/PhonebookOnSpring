package phonebook.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import phonebook.model.Call;
import phonebook.repository.CallRepository;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Call Service")
class CallServiceTest {

	@InjectMocks
	CallServiceImpl callService;

	@Mock
	private CallRepository callRepository;

	@Captor
	ArgumentCaptor<List<Long>> listArgumentCaptor;

	@Captor
	ArgumentCaptor<Call> argumentCaptor;

	@Test
	@DisplayName("Call is saved")
	void shouldSaveCall() {
		Long callContactId = 1L;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Call call = new Call(callContactId, timestamp);

		callService.save(call);

		verify(callRepository, times(1)).save(argumentCaptor.capture());
		assertEquals(argumentCaptor.getValue(), call);
	}

	@Test
	@DisplayName("Found calls for the contact")
	void shouldFindAllByContactId() {
		Long callContactId = 1L;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Call call = new Call(callContactId, timestamp);
		List<Call> expectedCalls = List.of(call);

		when(callRepository.findCallByCallContactId(callContactId)).thenReturn(expectedCalls);

		List<Call> result = callService.findAllByContactId(callContactId);

		assertEquals(expectedCalls, result);
		verify(callRepository, times(1)).findCallByCallContactId(callContactId);
	}

	@Test
	@DisplayName("Call is set as deleted")
	void shouldSetAsDeleted() {
		// Given
		List<Long> callsIds = List.of(1L, 2L, 3L);

		// Call method
		callService.setAsDeleted(callsIds);

		// Verify
		verify(callRepository, times(1)).setDeletedByIds(listArgumentCaptor.capture());
		List<Long> capturedContactIds = listArgumentCaptor.getValue();
		assertEquals(callsIds, capturedContactIds);
	}
}