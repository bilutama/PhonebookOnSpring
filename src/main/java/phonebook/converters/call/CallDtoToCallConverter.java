package phonebook.converters.call;

import org.springframework.stereotype.Service;
import phonebook.converters.AbstractConverter;
import phonebook.model.Call;
import phonebook.dto.CallDto;

@Service
public class CallDtoToCallConverter extends AbstractConverter<CallDto, Call> {
	@Override
	public Call convert(CallDto source) {
		Call c = new Call();

		c.setId(source.getId());
		c.setCallContactId(source.getCallContactId());
		c.setCallTime(source.getCallTime());
		c.setDeleted(false);

		return c;
	}
}