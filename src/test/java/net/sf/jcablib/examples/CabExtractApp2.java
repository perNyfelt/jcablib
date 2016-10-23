package net.sf.jcablib.examples;

//

import java.awt.*;
import java.io.*;

import net.sf.jcablib.*;

/**
 * this version uses the faster, folder reference decompression method.
 */
public class CabExtractApp2 {
   public static void main(String[] args) {
      FileDialog fd = new FileDialog(new Frame(), "Find a .cab file", FileDialog.LOAD);
      CabDump ca;
      CabFolder[] folders;
      File out_dir;
      byte[] buffer = new byte[2048];
      int bytes_read;
      String input = "";
      boolean proceed = true;

      while (proceed) {
         fd.show();
         if (fd.getFile() != null) {
            try {
               ca = new CabDump(new File(fd.getDirectory(), fd.getFile()));
               ca.reportHeader(System.out);
               folders = ca.getFolders();
               out_dir = new File(fd.getDirectory(), fd.getFile() + " folder");

               for (int i = 0; i < folders.length; i++) {
//						if(folders[i].canExtract()) {
                  out_dir.mkdir();            //only create folder if you can decompress
                  ca.extract(folders[i], out_dir);
//						}
               }

               System.out.println("done");
            } catch (IOException e) {
               e.printStackTrace();
            }
         } else {
            System.exit(0);
         }

         System.out.println("Do another (y/n)?");
         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         try {
            input = br.readLine();
         } catch (Exception blah) {
         }
         if (input.toLowerCase().startsWith("n"))
            proceed = false;
      }
   }
}
