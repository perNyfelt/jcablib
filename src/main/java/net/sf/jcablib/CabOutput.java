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
import java.util.*;

/**
 * @author dvh
 */
public class CabOutput {

   private Vector<CabFolder> mCabFolders = new Vector<>();
   private CabEntry currentEntry = null;
   private CabFolder currentFolder = null;
   private boolean writeComplete = false;
   private DataOutputStream mOut = null;

   private short mTotalFolders = -1;
   private short mTotalFiles = -1;

   /**
    * @param out
    */
   public CabOutput(OutputStream out) {
      if (out instanceof DataOutputStream)
         mOut = (DataOutputStream) out;
      else
         mOut = new DataOutputStream(out);
   }

   /**
    *
    */
   public void closeEntry() {
      currentFolder.addEntry(currentEntry);
      currentEntry = null;
   }

   /**
    *
    */
   public void closeFolder() {
      mCabFolders.add(currentFolder);
      currentFolder = null;
   }

   /**
    * @throws java.io.IOException
    */
   private void writeCabinet() throws IOException {
      writeComplete = true; // flag first, if exception is thrown, doesn't matter
      writeCabinetHeader();

   }

   /**
    * XXX unimplemented
    *
    * @return
    */
   private int getHeaderChecksum() {
      return 0;
   }

   /**
    * @return
    */
   private short getTotalFolders() {
      if (mTotalFolders == -1) {
         getTotalFiles();
      }
      return mTotalFolders;
   }

   /**
    * @return
    */
   private short getTotalFiles() {
      if (mTotalFiles == -1) {
         short totalFiles = 0;
         short totalFolders = 0;
         Iterator<CabFolder> i = mCabFolders.iterator();
         Iterator<?> iFiles;
         while (i.hasNext()) {
            totalFolders++;
            iFiles = i.next().iterator();
            while (iFiles.hasNext())
               totalFiles++;
         }
         mTotalFolders = totalFolders;
         mTotalFiles = totalFiles;
      }
      return mTotalFiles;
   }

   private int getHeaderSize() {
      getTotalFolders();

      int result = 0;
      // add archive header totals
      result += (4 * 5); // 5 ints
      result += (2 * 6); // 2 shorts

      // add folder header totals
      result += (4); // 1 ints
      result += (2 * 6); // 2 shorts

      // add file header totals
      result += (4 * 3); // 5 ints
      result += (2 * 2); // 2 shorts

      for (int i = 0; i < mTotalFolders; i++) {
         currentFolder = mCabFolders.get(i);
         for (int j = 0; j < currentFolder.getEntries().length; j++) {
            currentEntry = currentFolder.getEntries()[j];
            result += Math.min((currentEntry.getName().length() + 1), 256);
         }
      }

      return result;
   }

   /**
    * @throws java.io.IOException
    */
   private void writeCabinetHeader() throws IOException {

      int cab_header_checksum = 0, cab_file_size = 0, cab_folder_checksum = 0, cab_entry_offset = 0,
            cab_file_checksum = 0;
      short cab_version = 0, cab_folders = getTotalFolders(), cab_files = getTotalFiles(),
            cab_flags = 0, // not creating multipart
            cab_setid = 0, cab_icab = 0;

      int inflatedOffset = getHeaderSize(); // XXX start with default header size

      mOut.writeInt(CabConstants.MSCF);
      mOut.writeInt(cab_header_checksum);
      mOut.writeInt(CabUtils.rotateInt(cab_file_size));
      mOut.writeInt(CabUtils.rotateInt(cab_entry_offset));
      mOut.writeInt(CabUtils.rotateInt(cab_file_checksum));

      mOut.writeShort(CabUtils.rotateShort(cab_version));
      mOut.writeShort(CabUtils.rotateShort(cab_folders));
      mOut.writeShort(CabUtils.rotateShort(cab_files));
      mOut.writeShort(CabUtils.rotateShort(cab_flags));
      mOut.writeShort(CabUtils.rotateShort(cab_setid));
      mOut.writeShort(CabUtils.rotateShort(cab_icab));

      // XXX add entries here

      // write folder information
      for (int i = 0; i < mTotalFolders; i++) {
         currentFolder = mCabFolders.get(i);
         mOut.writeInt(CabUtils.rotateInt(0)); // folder offset
         mOut.writeShort(CabUtils.rotateShort((short) 0)); // folder data
         mOut.writeShort(CabUtils.rotateShort(CabConstants.kNoCompression)); // compression method
      }

      // write entry information
      for (int i = 0; i < mTotalFolders; i++) {
         currentFolder = mCabFolders.get(i);
         for (int j = 0; j < currentFolder.getEntries().length; j++) {
            currentEntry = currentFolder.getEntries()[j];
            // XXX
            mOut.writeInt(CabUtils.rotateInt(currentEntry.getInflatedSize())); // inflated size
            inflatedOffset += currentEntry.getInflatedSize();
            mOut.writeInt(CabUtils.rotateInt(inflatedOffset)); // inflated offset
            mOut.writeShort(CabUtils.rotateShort(currentEntry.getFolderIndex()));
            mOut.writeInt((int) CabUtils.javaToDosTime(currentEntry.getTimestamp().getTime()));
            mOut.writeShort(currentEntry.getAttributes());
            CabUtils.writeCString(currentEntry.getName(), mOut);
         }
      }

   }

   /**
    *
    */
   public void finish() throws IOException {
      writeCabinet();
   }

   /**
    * @param e
    */
   public void putNextEntry(CabEntry e) throws CabException {
      if (currentFolder == null)
         throw new CabException("Must have a current CabFolder specified to add entries");
      currentEntry = e;
   }

   public void write() {

      // XXX to be fleshed out later

   }

   /**
    * @param f
    */
   public void putNextFolder(CabFolder f) throws CabException {
      currentFolder = f;
   }

}
