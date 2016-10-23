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

import java.io.File;
import java.util.Date;

/**
 * Represents a file inside a MS Cabinet archive.
 *
 * @author David Himelright <a href="mailto:dhimelright@gmail.com">dhimelright@gmail.com</a>
 */
public class CabEntry {

   private String mName;
   private int mInflatedSize, // inflated_size,
         mInflatedOffset, // inflated_offset,
         mTimestamp;
   private short mFolderIndex, // file control id (CAB_FILE_*)
   // mDate, // file date stamp, as used by DOS
   // mTime, // file time stamp, as used by DOS
   mAttributes; // file attributes (CAB_ATTRIB_*)
   private CabFolder mCabFolder;

   byte[] mOutputData; // XXX bad design, hacked up for now

   public CabEntry(String inName, int inSize, int inOffset, int inTimestamp, short inFolderIndex,
                   short inAttributes, CabFolder inCabFolder) {

      mName = inName;
      mInflatedSize = inSize;
      mInflatedOffset = inOffset;
      mTimestamp = inTimestamp;
      mFolderIndex = inFolderIndex; // iFolder
      mAttributes = inAttributes;
      mCabFolder = inCabFolder;

      mCabFolder.addEntry(this);
   }

   /**
    * @return The name of the file this entry refers to, a DOS path.
    */
   public String getName() {
      return mName;
   }

   /**
    * @return Size of this entry when inflated
    */
   public int getInflatedSize() {
      return mInflatedSize;
   }

   /**
    * @return The compression method for this entry's folder as defined in CabConstants.
    */
   public short getMethod() {
      return mCabFolder.getMethod();
   }

   public Date getTimestamp() {
      return new Date(CabUtils.dosToJavaTime((long) mTimestamp));
   }

   /**
    * @return The contents of the attributes field as a short.
    */
   public short getAttributes() {
      return mAttributes;
   }

   /**
    * @return The CabFolder which contains this entry.
    */
   public CabFolder getCabFolder() {
      return mCabFolder;
   }

   public short getFolderIndex() {
      return mFolderIndex;
   }

   /**
    * @return The offset of this entry's file in this entry's folder when inflated.
    */
   public int getOffset() {
      return mInflatedOffset;
   }

   /**
    * @return True if compresson scheme is supported by this library.
    *
    *         public boolean canExtract() { return folder.canExtract(); }
    */

   /**
    * @return The file this entry is stored in.
    */
   public File getArchiveFile() {
      return mCabFolder.getArchiveFile();
   }

}