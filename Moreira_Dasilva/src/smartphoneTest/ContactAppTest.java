package smartphoneTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import smartphone.Contact;
import smartphone.ContactApp;

class ContactAppTest 
{
	
	ContactApp c = new ContactApp();

	@Test
	void testOnlyContainsNumbers() 
	{
		assertTrue("test de numeros non valides", c.onlyContainsNumbers("1234"));
		assertTrue("test de numeros non valides", c.onlyContainsNumbers("0798165409"));
		assertFalse("test de numeros non valides", c.onlyContainsNumbers("079 816 54 09"));
		assertFalse("test de numeros non valides", c.onlyContainsNumbers("aa"));
		assertFalse("test de numeros non valides", c.onlyContainsNumbers("a123a"));
		assertFalse("test de numeros non valides", c.onlyContainsNumbers("079/8456565"));
		assertFalse("test de numeros non valides", c.onlyContainsNumbers("abcd23434"));
		assertFalse("test de numeros non valides", c.onlyContainsNumbers("123abfd"));
		assertFalse("test de numeros non valides", c.onlyContainsNumbers("..."));
		assertTrue("test de numeros non valides", c.onlyContainsNumbers("00000000000"));
	}
	
	@Test
	void testDeserializeContacts() 
	{
		ArrayList<Contact> contacts=c.contacts;
		
		c.deserializeContacts();
		assertTrue("test si deserialization est bien faite", contacts!=c.contacts);
	}
}
