package phonebook.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import phonebook.model.Call;

import java.util.List;

public interface CallRepository extends CrudRepository<Call, Long> {
	@Query("SELECT c FROM Call AS c WHERE " +
		"c.callContactId = :callContactId AND " +
		"c.isDeleted IS FALSE")
	List<Call> findCallByCallContactId(@Param("callContactId") Long callContactId);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Call c SET c.isDeleted = TRUE " +
		"WHERE c.id IN (:callIds)")
	void setDeletedByIds(@Param("callIds") List<Long> callIds);
}
