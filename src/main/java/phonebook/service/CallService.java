package phonebook.service;

import phonebook.model.Call;

import java.util.List;

public interface CallService {

	void save(Call call);

	List<Call> findAllByContactId(Long callContactId);

	void setAsDeleted(List<Long> callIds);
}
