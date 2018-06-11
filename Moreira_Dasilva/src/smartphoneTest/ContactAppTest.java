/**
* TP Week2
*Author: Joao Silva
*Date creation : 11 juin 2018
*/
package smartphoneTest;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import smartphone.ContactApp;

class ContactAppTest {

	ContactApp c = new ContactApp();
	
	@Test
	void onlyContainsNumberstest() {
		assertTrue("test de numeros non valides", c.onlyContainsNumbers("1234")==true );
		assertTrue("test de numeros non valides", c.onlyContainsNumbers("0798165409")==true );
		assertTrue("test de numeros non valides", c.onlyContainsNumbers("079 816 54 09")==false );
		assertTrue("test de numeros non valides", c.onlyContainsNumbers("aa")==false );
		assertTrue("test de numeros non valides", c.onlyContainsNumbers("a123a")==false );
	}
	

}
