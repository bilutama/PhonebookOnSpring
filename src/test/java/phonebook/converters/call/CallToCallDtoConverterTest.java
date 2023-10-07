package phonebook.converters.call;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import phonebook.dto.CallDto;
import phonebook.model.Call;

import java.sql.Timestamp;
import java.util.List;

@DisplayName("Call To CallDto Converter")
public class CallToCallDtoConverterTest {
	private CallToCallDtoConverter converter;

	@BeforeEach
	void setUp() {
		converter = new CallToCallDtoConverter();
	}

	@Test
	@DisplayName("Convert a single call")
	void testConverterForSingleCall() {
		// Given
		Call source = new Call();
		source.setId(1L);
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		source.setCallTime(stamp);
		source.setCallContactId(2L);
		source.setDeleted(false);

		// Call the method
		CallDto result = converter.convert(source);

		// Assert
		Assertions.assertEquals(source.getId(), result.getId());
		Assertions.assertEquals(source.getCallContactId(), result.getCallContactId());
		Assertions.assertEquals(source.getCallTime(), result.getCallTime());
	}

	@Test
	@DisplayName("Converte a list of calls")
	void testConverterForContactsList() {
		// Given
		Call source1 = new Call();
		source1.setId(1L);
		Timestamp stamp1 = new Timestamp(System.currentTimeMillis());
		source1.setCallTime(stamp1);
		source1.setCallContactId(2L);
		source1.setDeleted(true);

		Call source2 = new Call();
		source2.setId(1L);
		Timestamp stamp2 = new Timestamp(System.currentTimeMillis());
		source2.setCallTime(stamp2);
		source2.setCallContactId(2L);
		source2.setDeleted(true);

		List<Call> callList = List.of(source1, source2);

		// Call the method
		List<CallDto> resultList = converter.convert(callList);

		// Asserts
		for (int i = 0; i < resultList.size(); ++i) {
			Assertions.assertEquals(callList.get(i).getId(), resultList.get(i).getId());
			Assertions.assertEquals(callList.get(i).getCallContactId(), resultList.get(i).getCallContactId());
			Assertions.assertEquals(callList.get(i).getCallTime(), resultList.get(i).getCallTime());
		}
	}
}
