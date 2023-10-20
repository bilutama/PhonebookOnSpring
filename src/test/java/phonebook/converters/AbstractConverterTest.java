package phonebook.converters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AbstractConverter test with concrete subclass")
class AbstractConverterTest {
	// Concrete subclass of AbstractConverter for testing
	private static class StringToIntConverter extends AbstractConverter<String, Integer> {
		@Override
		public Integer convert(String source) {
			return Integer.parseInt(source);
		}
	}

	@Test
	void testConvert() {
		// Subclass for testing
		StringToIntConverter converter = new StringToIntConverter();

		//Given
		List<String> sourceList = Arrays.asList("1", "2", "3");

		// Call convert()
		List<Integer> result = converter.convert(sourceList);

		// Assert
		List<Integer> expected = Arrays.asList(1, 2, 3);
		assertEquals(expected, result);
	}
}