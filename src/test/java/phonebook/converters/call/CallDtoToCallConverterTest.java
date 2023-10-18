package phonebook.converters.call;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import phonebook.dto.CallDto;
import phonebook.model.Call;

import java.sql.Timestamp;
import java.util.List;

@DisplayName("CallDto To Call Converter")
class CallDtoToCallConverterTest {

	private CallDtoToCallConverter converter;

	@BeforeEach
	void setUp() {
		converter = new CallDtoToCallConverter();
	}

	@Test
	@DisplayName("Convert a single callDto")
	void testConverterForSingleCall() {
		// Given
		CallDto source = new CallDto();
		source.setId(1L);
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		source.setCallTime(stamp);
		source.setCallContactId(2L);

		// Call the method
		Call result = converter.convert(source);

		// Assert
		Assertions.assertEquals(source.getId(), result.getId());
		Assertions.assertEquals(source.getCallContactId(), result.getCallContactId());
		Assertions.assertEquals(source.getCallTime(), result.getCallTime());
		Assertions.assertFalse(result.isDeleted());
	}

	@Test
	@DisplayName("Convert a list of callDtos")
	void testConverterForContactsList() {
		// Given
		CallDto source1 = new CallDto();
		source1.setId(1L);
		Timestamp stamp1 = new Timestamp(System.currentTimeMillis());
		source1.setCallTime(stamp1);
		source1.setCallContactId(2L);

		CallDto source2 = new CallDto();
		source2.setId(1L);
		Timestamp stamp2 = new Timestamp(System.currentTimeMillis());
		source2.setCallTime(stamp2);
		source2.setCallContactId(2L);

		List<CallDto> callDtoList = List.of(source1, source2);

		// Call the method
		List<Call> resultList = converter.convert(callDtoList);

		// Asserts
		for (int i = 0; i < resultList.size(); ++i) {
			Assertions.assertEquals(callDtoList.get(i).getId(), resultList.get(i).getId());
			Assertions.assertEquals(callDtoList.get(i).getCallContactId(), resultList.get(i).getCallContactId());
			Assertions.assertEquals(callDtoList.get(i).getCallTime(), resultList.get(i).getCallTime());
			Assertions.assertFalse(resultList.get(i).isDeleted());
		}
	}
}
