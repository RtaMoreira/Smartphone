
package smartphoneTest;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.Test;
import smartphone.GalerieApp;

class GalerieAppTest 
{
	GalerieApp galerie = new GalerieApp();
	
	@Test
	void checkExtensionTest()  
	{

		File image = new File("/image/image/japon.jpg");
		assertTrue(galerie.checkExtension(image));

		File fichierTxt = new File("/image/image/CheckExtensionTest.txt");
		assertFalse(galerie.checkExtension(fichierTxt));
		
		File fichierDocx = new File("/image/image/documentWorld.docx");
		assertFalse(galerie.checkExtension(fichierDocx));
		
		File fichierSansExt = new File("/image/image/japon");
		assertFalse(galerie.checkExtension(fichierSansExt));

	}
	
	@Test
	void getFileExtensionTest() 
	{
		File image = new File("/image/image/japon.jpg");
		assertTrue(galerie.getFileExtension(image).equals("jpg"));
		
		image = new File("/image/image/japon");
		assertTrue(galerie.getFileExtension(image).equals(""));
	}
	
	

}
