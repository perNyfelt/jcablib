package net.sf.jcablib.examples;
//

import java.awt.*;
import java.io.*;

import net.sf.jcablib.*;


/**
 * format test
 */
public class MSZipInflaterApp {
   public static void main(String[] args) {
      FileDialog fd = new FileDialog(new Frame(), "Find a .def file", FileDialog.LOAD);
      boolean proceed = true;
      String input = "";

      DataInputStream dis;
      OutputStream out;
      InputStream in;
      File out_file,
            in_file;
      byte[] buffer = new byte[2048];
      int bytes_read;

      fd.show();
      if (fd.getFile() != null) {

         try {
            in_file = new File(fd.getDirectory(), fd.getFile());
            out_file = new File(fd.getDirectory(), fd.getFile() + ".out");

            out = new FileOutputStream(out_file);
            in = new MSZipInputStream(new FileInputStream(in_file));

            while ((bytes_read = in.read(buffer)) != -1)
               out.write(buffer, 0, bytes_read);

            in.close();
            out.close();

         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
}