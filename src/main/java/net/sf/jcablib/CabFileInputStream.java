package net.sf.jcablib;

/*
* CabLib, a library for extracting MS cabinets
* Copyright (C) 1999, 2002  David V. Himelright
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
* David Himelright can be reached at:
* <dhimelright@gmail.com>
*/

import java.io.*;

/**
 * CabFileInputStream mimics the ZipFileInputStream interface.
 *
 * @author David Himelright <a href="mailto:dhimelright@gmail.com">dhimelright@gmail.com</a>
 */
public class CabFileInputStream extends InputStream {

   private long count; // bytes available in this stream
   private InputStream in;

   /*
    * @param ce the CabEntry to return data from
    *
    * @exception CabException thrown if the file's compression format isn't supported
    *
    * @exception IOException thrown by various file IO routines
    */
   CabFileInputStream(CabEntry ce) throws IOException {
      count = ce.getInflatedSize(); // compressed size unavailable in Cabinet
      FileInputStream fis = new FileInputStream(ce.getArchiveFile());
      String error = null;

      switch (ce.getMethod()) {
         case CabConstants.kNoCompression:
            fis.skip(ce.getCabFolder().getOffset());
            in = fis;
            in.skip(ce.getOffset());
            break;
         case CabConstants.kMszipCompression:
            fis.skip(ce.getCabFolder().getOffset());
            fis.skip(8); // skip unknown data
            in = new MSZipInputStream(fis);
            in.skip(ce.getOffset());
            break;
         case CabConstants.kInvalidFolder:
            error = "Invalid compression flag, unable to continue.";
            break;
         case CabConstants.kQuantumCompression:
            error = "Unsupported compression scheme: Quantum";
            break;
         case CabConstants.kLzxCompression:
            error = "Unsupported compression scheme: LZX";
            break;
         default:
            throw new CabException("Unknown");
      }
      if (error != null) {
         throw new CabException(error);
      }
   }

   /*
    * Creates an InputStream for reading the contents of the specified CabFolder
    *
    * @param cf A CabFolder to read
    *
    * @exception CabException thrown if the file's compression format isn't supported
    *
    * @exception IOException thrown by various file IO routines
    */
   public CabFileInputStream(CabFolder cf) throws IOException {
      count = cf.getInflatedSize();
      FileInputStream fis = new FileInputStream(cf.getArchiveFile());
      String error = null;

      switch (cf.getMethod()) {
         case CabConstants.kNoCompression:
            fis.skip(cf.getOffset());
            in = fis;
            break;
         case CabConstants.kMszipCompression:
            fis.skip(cf.getOffset());
            fis.skip(8); // skip unknown data
            in = new MSZipInputStream(fis);
            break;
         case CabConstants.kInvalidFolder:
            error = "Invalid compression flag, unable to continue.";
            break;
         case CabConstants.kQuantumCompression:
            error = "Unsupported compression scheme: Quantum";
            break;
         case CabConstants.kLzxCompression:
            error = "Unsupported compression scheme: LZX";
            break;
         default:
            throw new CabException("Unknown");
      }
      if (error != null) {
         throw new CabException(error);
      }
   }

   /**
    * @return Number of bytes available in this stream.
    */
   @Override
   public int available() {
      return (int) Math.min(count, Integer.MAX_VALUE);
   }

   /**
    * Reads cab file entry into an array of bytes. This method will block until some input is
    * available.
    *
    * @param b   the buffer into which the data is read
    * @param off the start offset of the data
    * @param len the maximum number of bytes to read
    * @return the actual number of bytes read, or -1 if the end of the stream has been reached.
    * @throws CabException if a Cabinet format error has occurred
    * @throws IOException  if an I/O error has occurred
    */
   @Override
   public int read(byte b[], int off, int len) throws IOException {
      if (count == 0)
         return -1;

      if (len > count)
         len = available();

      len = in.read(b, off, len);

      if (len == -1)
         throw new CabException("premature EOF");

      count -= len;
      return len;
   }

   /**
    * Reads a byte of data. This method will block until some input is available.
    *
    * @return the byte read, or -1 if the end of the stream has been reached.
    * @throws CabException if a Cabinet format error has occurred
    * @throws IOException  if an I/O error has occurred
    */
   public int read() throws IOException {
      if (count == 0)
         return -1;

      int n = in.read();

      if (n == -1)
         throw new CabException("premature EOF");

      count -= 1;
      return n;
   }

   /**
    * Skips n bytes of input.
    *
    * @param n the number of bytes to skip
    * @return the actual number of bytes skipped
    */
   @Override
   public long skip(long n) throws IOException {
      if (n > count)
         n = count;

      n = in.skip(n);

      count -= n;
      return n;
   }
}