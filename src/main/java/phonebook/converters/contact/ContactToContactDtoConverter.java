package phonebook.converters.contact;

import org.springframework.stereotype.Service;
import phonebook.converters.AbstractConverter;
import phonebook.model.Contact;
import phonebook.dto.ContactDto;

@Service
public class ContactToContactDtoConverter extends AbstractConverter<Contact, ContactDto> {
	@Override
	public ContactDto convert(Contact source) {
		ContactDto c = new ContactDto();

		c.setId(source.getId());
		c.setFirstName(source.getFirstName());
		c.setLastName(source.getLastName());
		c.setPhone(source.getPhone());
		c.setImportant(source.getImportant());

		return c;
	}
}
