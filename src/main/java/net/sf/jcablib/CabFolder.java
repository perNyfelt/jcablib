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

import java.util.*;
import java.io.File;

/**
 * Represents the folder structure found in cabinets which is a grouping of files compressed as one
 * contiguous block of data.
 *
 * @author David Himelright <a href="mailto:dhimelright@gmail.com">dhimelright@gmail.com</a>
 */
public class CabFolder {
   private short mMethod, // compression method
         mData;
   private int mOffset, // offset
         mInflatedSize, mFolderNum;
   private Vector<CabEntry> mEntries;
   private File mFile;

   /**
    * Constructor for decompression
    *
    * @param inFile
    * @param inOffset
    * @param inData
    * @param inMethod
    * @param inFolderNum
    */
   public CabFolder(File inFile, int inOffset, short inData, short inMethod, int inFolderNum) {
      mFile = inFile;
      mOffset = inOffset;
      mData = inData;
      mMethod = inMethod;
      mFolderNum = inFolderNum;
      mInflatedSize = 0;
      mEntries = new Vector<CabEntry>();
   }

   /**
    * Constructor for compression
    *
    * @param inName
    * @param inMethod
    */
   public CabFolder(int inFolderNum, short inMethod) {
      mFolderNum = inFolderNum;
      mMethod = inMethod;
   }

   public void addEntry(CabEntry inEntry) {
      // inEntry.mCabFolder = this;
      mEntries.addElement(inEntry);
      mInflatedSize += inEntry.getInflatedSize();
   }

   /**
    * @return Iterator of the CabEntries in this CabFolder.
    */
   public Iterator<CabEntry> iterator() {
      return mEntries.iterator();
   }

   public CabEntry[] getEntries() {
      CabEntry[] result = new CabEntry[mEntries.size()];
      mEntries.toArray(result);
      return result;
   }

   /**
    * @return The compression method for this folder as defined in CabConstants.
    */
   public short getMethod() {
      return (short) (mMethod & 0x000F);
   }

   /**
    * @return The offset of this CabFolder in the File it is stored in.
    */
   public int getOffset() {
      return mOffset;
   }

   /**
    * @return Total size of all entries in this folder when inflated.
    */
   public int getInflatedSize() {
      return mInflatedSize;
   }

   /**
    * @return The File this CabFolder is stored in.
    */
   public File getArchiveFile() {
      return mFile;
   }

   public String getName() {
      return "Folder #" + mFolderNum;
   }
}