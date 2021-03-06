package net.sf.jcablib.examples;

//

import java.awt.*;
import java.io.*;

import net.sf.jcablib.*;


/**
 * Extracts the contents of a Cabinet file.  Uses the slower conventional interface.
 */
public class CabExtractApp1 {
   public static void main(String[] args) {
      FileDialog fd = new FileDialog(new Frame(), "Find a .cab file", FileDialog.LOAD);
      CabFile ca;
      CabEntry[] entries;
      OutputStream out = null;
      InputStream in;
      File out_file,
            out_dir;
      byte[] buffer = new byte[2048];
      int bytes_read;
      String input = "";
      boolean proceed = true;

      fd.show();
      if (fd.getFile() != null) {
         try {
            ca = new CabFile(new File(fd.getDirectory(), fd.getFile()));
            entries = ca.getEntries();
            out_dir = new File(fd.getDirectory(), fd.getFile() + "-folder");
            out_dir.mkdir();
            
            for (int i = 0; i < entries.length; i++) {
//						if(entries[i].canExtract()) {

               System.out.println("Extracting " + entries[i].getName());
               out_file = new File(out_dir, entries[i].getName());
               File outdir = new File(out_file.getParent());
               if (!outdir.exists()) {     //shouldn't be in the loop, but...
                 System.out.println("Creating dir " + out_file.getParent());
                 outdir.mkdirs();
               }
                 
               out = new FileOutputStream(out_file);
               in = ca.getInputStream(entries[i]);
               while ((bytes_read = in.read(buffer)) != -1)
                  out.write(buffer, 0, bytes_read);
               in.close();
               out.close();
//						}
            }

            System.out.println("done");
         } catch (IOException e) {
            e.printStackTrace();
            try {
               out.close();
            } catch (IOException e2) {
            }
         }
      } else {
         System.exit(0);
      }

   }
}
