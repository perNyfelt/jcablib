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
 * Thrown when format errors and other strangeness happens.
 *
 * @author David Himelright <a href="mailto:dhimelright@gmail.com">dhimelright@gmail.com</a>
 */
public class CabException extends IOException {

   private static final long serialVersionUID = 1L;

   public CabException() {
      super();
   }

   public CabException(String s) {
      super(s);
   }
}