package net.sf.jcablib.examples;

/*
*/

import java.io.File;

/**
 * Static utility routines for manipulating files.
 *
 * @author David Himelright <a href="dhimelright@gmail.com">dhimelright@gmail.com</a>
 */
public class FileUtils {
   /**
    * There are faster and more straightforward ways to do this, but this
    * way makes no assumptions about the underlying filesystem; it uses
    * only the File interface.
    *
    * @param f this File's path is walked, creating necessary directories
    */
   public static synchronized void createParents(File f) {
      int depth = 0;
      File current = new File(f.getParent());

      while (!current.exists()) {
         current = new File(current.getParent());
         depth++;
      }
      while (depth > 0) {
         subDir(f, depth).mkdir();
         depth--;
      }
   }

   /**
    * Returns the directory a specified number of directories deep in
    * the file's path.
    *
    * @param f     the base File to walk
    * @param depth the depth of the target directory
    */
   public static File subDir(File f, int depth) {
      if (depth < 1)
         return f;

      File result = new File(f.getParent());

      for (int i = 1; i < depth; i++)
         result = new File(result.getParent());

      return result;
   }
}