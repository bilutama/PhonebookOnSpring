package phonebook.dao.call;

import org.springframework.stereotype.Repository;
import phonebook.model.Call;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CallDaoImpl extends GenericCallDaoImpl<Call, Long> implements CallDao {
	public CallDaoImpl() {
		super(Call.class);
	}

	@Override
	public List<Call> getCalls(Long contactId) {
		return find(contactId);
	}

	@Override
	public void setDeletedByIds(List<Long> callsIds) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaUpdate<Call> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(clazz);

		Root<Call> root = criteriaUpdate.from(clazz);

		// Update for all calls with ids from contactsIds
		for (Long thisId : callsIds) {
			criteriaUpdate.set("isDeleted", true);
			criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), thisId));
			entityManager.createQuery(criteriaUpdate).executeUpdate();
		}
	}
}