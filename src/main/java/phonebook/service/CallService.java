package phonebook.service;

import phonebook.model.Call;

import java.util.List;

public interface CallService {

	void saveCall(Call call);

	List<Call> getCalls(Long callContactId);

	void setCallsAsDeleted(List<Long> callIds);
}
