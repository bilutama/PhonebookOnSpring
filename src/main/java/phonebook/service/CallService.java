package phonebook.service;

import org.springframework.stereotype.Service;
import phonebook.dao.call.CallDao;
import phonebook.model.Call;

import java.util.List;

@Service
public class CallService {
	private final CallDao callDao;

	public CallService(CallDao callDao) {
		this.callDao = callDao;
	}

	public void addCall(Call call) {
		callDao.create(call);
	}

	public List<Call> getCalls(Long contactId) {
		return callDao.getCalls(contactId);
	}

	public void setCallsAsDeleted(List<Long> callsIds) {
		callDao.setDeletedByIds(callsIds);
	}
}