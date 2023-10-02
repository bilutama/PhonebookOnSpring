package phonebook.repository;

import org.springframework.data.repository.CrudRepository;
import phonebook.model.Call;

public interface CallRepository extends CrudRepository<Call, Long> {
}
