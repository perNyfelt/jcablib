package net.sf.jcablib.examples;

//

import java.awt.*;
import java.io.*;

/**
 * Print all the cab information to System.out
 */
public class CabInfoApp {
   public static void main(String[] args) {
      FileDialog fd = new FileDialog(new Frame(), "Find a .cab file", FileDialog.LOAD);
      CabDump ca;
      String input = "";
      fd.show();
      if (fd.getFile() != null) {
         try {
            ca = new CabDump(new File(fd.getDirectory(), fd.getFile()));
            ca.close();
            ca.reportHeader(System.out);
            ca.reportEntries(System.out);
         } catch (IOException e) {
            System.out.println(e);
         }
      }
   }
}
