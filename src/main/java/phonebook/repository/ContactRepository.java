package phonebook.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import phonebook.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends CrudRepository<Contact, Long> {
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Contact AS c SET " +
		"c.important = CASE WHEN c.important = TRUE THEN FALSE ELSE TRUE END " +
		"WHERE c.id = :contactId")
	void toggleImportant(@Param("contactId") Long contactId);

	@Query("SELECT c FROM Contact AS c WHERE " +
		"(c.isDeleted IS FALSE) AND " +
		"(:term IS NULL OR :term = '' OR " +
		"LOWER(c.firstName) LIKE LOWER(CONCAT('%', :term, '%') ) OR " +
		"LOWER(c.lastName) LIKE LOWER(CONCAT('%', :term, '%') ) OR " +
		"LOWER(c.phone) LIKE LOWER(CONCAT('%', :term, '%')))")
	List<Contact> findContacts(@Param("term") String term);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Contact c SET c.isDeleted = TRUE "+
		"WHERE c.id IN :contactIds")
	void setDeletedByIds(@Param("contactIds") List<Long> contactIds);

	Optional<Contact> findByPhone(String phone);
}
