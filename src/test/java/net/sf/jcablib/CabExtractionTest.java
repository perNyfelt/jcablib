package net.sf.jcablib;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

/**
 * Created by Per on 22/10/2016.
 */
public class CabExtractionTest {

   @Test
   public void testUncompressedCab() throws IOException {
      URL url = Thread.currentThread().getContextClassLoader().getResource("uncompressed.cab");
      File cabFile = new File(url.getFile());
      File targetDir = Files.createTempDirectory("uncompressed").toFile();
      CabExtractor.extract(cabFile, targetDir);
      assertEquals("Expecting 9 extracted files", 9, targetDir.listFiles().length);
   }


}
