package phonebook;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Phonebook Application Start")
class PhonebookSpringApplicationTest {
	@Test
	void shouldStartApplicationWhenMainMethodIsInvoked() {
		PhonebookSpringApplication.main(new String[]{});
	}
}