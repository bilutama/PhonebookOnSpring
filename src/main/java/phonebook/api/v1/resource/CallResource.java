package phonebook.api.v1.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phonebook.converters.call.CallDtoToCallConverter;
import phonebook.converters.call.CallToCallDtoConverter;
import phonebook.dto.CallDto;
import phonebook.service.CallService;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/phonebook/rpc/api/v1")
public class CallResource {

	private static final Logger logger = LoggerFactory.getLogger(CallResource.class);
	private final CallService callService;

	private final CallDtoToCallConverter callDtoToCallConverter;

	private final CallToCallDtoConverter callToCallDtoConverter;

	public CallResource(
		CallService callService,
		CallDtoToCallConverter callDtoToCallConverter,
		CallToCallDtoConverter callToCallDtoConverter
	) {
		this.callService = callService;
		this.callDtoToCallConverter = callDtoToCallConverter;
		this.callToCallDtoConverter = callToCallDtoConverter;
	}

	@PostMapping(value = "saveCall")
	public void saveCall(@RequestBody Long callRecipientId) {
		logger.info("Received POST request to call the contact with ID={}", callRecipientId);

		CallDto callDto = new CallDto(callRecipientId, new Timestamp(System.currentTimeMillis()));
		callService.save(callDtoToCallConverter.convert(callDto));
	}

	@PostMapping(value = {"findCallsByContactId/{callContactId}"})
	public List<CallDto> findCallsByContactId(@PathVariable Long callContactId) {
		logger.info("Received POST request to find calls for contact with id={}", callContactId);

		return callToCallDtoConverter.convert(callService.findAllByContactId(callContactId));
	}

	@PostMapping(value = "deleteCalls")
	public void setCallsAsDeleted(@RequestBody List<Long> callsIds) {
		String ids = Arrays.toString(callsIds.toArray());
		logger.info("Received POST request to set calls with IDs={} as deleted", ids);

		callService.setAsDeleted(callsIds);
	}
}
