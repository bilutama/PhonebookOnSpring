package phonebook.service.impl;

import org.springframework.stereotype.Service;
import phonebook.model.Call;
import phonebook.repository.CallRepository;
import phonebook.service.CallService;

import java.util.List;

@Service
public class CallServiceImpl implements CallService {
	private final CallRepository callRepository;

	public CallServiceImpl(
		final CallRepository callRepository
	) {
		this.callRepository = callRepository;
	}

	@Override
	public void save(Call call) {
		callRepository.save(call);
	}

	@Override
	public List<Call> getAllByContactId(Long contactId) {
		return callRepository.findCallByCallContactId(contactId);
	}

	@Override
	public void setAsDeleted(List<Long> callIds) {
		callRepository.setDeletedByIds(callIds);
	}
}