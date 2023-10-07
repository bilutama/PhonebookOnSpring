package phonebook;

public final class Constants {

	private Constants() {
	}

	public static final class ContactService {

		private ContactService() {
		}

		public static final String FIRST_NAME_VALIDATION_ERROR_MESSAGE = "First name is required";

		public static final String LAST_NAME_VALIDATION_ERROR_MESSAGE = "Last name is required";

		public static final String PHONE_VALIDATION_ERROR_MESSAGE = "Phone is required";

		public static final String PHONE_ALREADY_EXISTS_MESSAGE = "Phone provided is already exists in the phonebook";
	}
}
