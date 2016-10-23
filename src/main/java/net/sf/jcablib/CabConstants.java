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

/**
 * Constants that are useful throughout the package DVH - changed from constant interface,
 * considered an antipattern
 *
 * @author David Himelright <a href="mailto:dhimelright@gmail.com">dhimelright@gmail.com</a>
 */
public class CabConstants {
   // Constants -------------------
   public static final int MSCF = 0x4D534346; // hex value 'MSCF'
   public static final short kNoCompression = 0x0000, kMszipCompression = 0x0001,
         kQuantumCompression = 0x0002, kLzxCompression = 0x0003, kInvalidFolder = 0x000F,
         kFlagHasPrev = 0x0001, kFlagHasNext = 0x0002, kFlagReserve = 0x0004;
}