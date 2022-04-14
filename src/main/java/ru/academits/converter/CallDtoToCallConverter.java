package ru.academits.converter;

import org.springframework.stereotype.Service;
import ru.academits.dto.CallDto;
import ru.academits.model.Call;

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