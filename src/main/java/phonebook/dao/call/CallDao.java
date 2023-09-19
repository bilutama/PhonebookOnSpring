package phonebook.dao.call;

import phonebook.model.Call;

import java.util.List;

public interface CallDao extends GenericCallDao<Call, Long> {

	List<Call> getCalls(Long contactId);

	void setDeletedByIds(List<Long> contactsIds);
}