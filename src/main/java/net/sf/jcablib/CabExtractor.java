package net.sf.jcablib;

/*
* CabLib, a library for extracting MS cabinets
* Copyright (C) 2016 Per Nyfelt
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
* USA
*
* Per Nyfelt can be reached at:
* <per.nyfelt@alipsa.se>
*/

import java.io.*;
import java.nio.file.Files;

/**
 * Simple high level API usage.
 *
 * @author Per Nyfelt <a href="mailto:per.nyfelt@alipsa.se">per.nyfelt@alipsa.se</a>
 */
public class CabExtractor {

   /**
    * Extract a cab file to the directory specified
    * @param cabFile the cab file to extract
    * @param dir the dir to extract the cab file to
    * @throws IOException if something goes wrong
    */
   public static void extract(File cabFile, File dir) throws IOException {
      CabFile cf = new CabFile(cabFile);
      extract(cf, dir);
   }

   /**
    * Extract a cab file to the directory specified
    * @param cabFile the cab file to extract
    * @param dir the dir to extract the cab file to
    * @throws IOException if something goes wrong
    */
   public static void extract(CabFile cabFile, File dir) throws IOException {
      CabEntry[] entries;
      entries = cabFile.getEntries();
      File outFile;
      if (!dir.exists()) dir.mkdir();
      for (CabEntry entry : entries) {
         outFile = new File(dir, entry.getName());
         InputStream in = cabFile.getInputStream(entry);
         Files.copy(in, outFile.toPath());
      }
   }
}
