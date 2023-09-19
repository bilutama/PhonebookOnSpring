package phonebook.converters.call;

import org.springframework.stereotype.Service;
import phonebook.converters.AbstractConverter;
import phonebook.dto.CallDto;
import phonebook.model.Call;

@Service
public class CallToCallDtoConverter extends AbstractConverter<Call, CallDto> {
	@Override
	public CallDto convert(Call source) {
		CallDto c = new CallDto();

		c.setId(source.getId());
		c.setCallContactId(source.getCallContactId());
		c.setCallTime(source.getCallTime());

		return c;
	}
}
