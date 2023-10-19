package phonebook.api.v1.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import phonebook.converters.call.CallDtoToCallConverter;
import phonebook.converters.call.CallToCallDtoConverter;
import phonebook.dto.CallDto;
import phonebook.service.CallService;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Controller
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
	@ResponseBody
	public void saveCall(@RequestBody Long callId) {
		CallDto call = new CallDto();
		call.setCallContactId(callId);
		call.setCallTime(new Timestamp(System.currentTimeMillis()));

		callService.saveCall(callDtoToCallConverter.convert(call));

		logger.info("Received POST request to call the contact with ID={}", callId);
	}

	@PostMapping(value = {"findCalls", "findCalls/{callContactId}"})
	@ResponseBody
	public List<CallDto> findCalls(@PathVariable(required = false) Long callContactId) {
		logger.info(
			"Received POST request to find{} calls{}",
			callContactId == null ? " all" : "",
			callContactId == null ? "" : " for contact with id=" + callContactId
		);

		return callToCallDtoConverter.convert(callService.getCalls(callContactId));
	}

	@PostMapping(value = "deleteCalls")
	@ResponseBody
	public void setCallsAsDeleted(@RequestBody List<Long> callsIds) {
		callService.setCallsAsDeleted(callsIds);

		String ids = Arrays.toString(callsIds.toArray());
		logger.info("Received POST request to set calls with IDs={} as deleted.", ids);
	}
}
