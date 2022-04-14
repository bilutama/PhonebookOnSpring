package ru.academits.converters.call;

import org.springframework.stereotype.Service;
import ru.academits.converters.AbstractConverter;
import ru.academits.dto.CallDto;
import ru.academits.model.Call;

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
