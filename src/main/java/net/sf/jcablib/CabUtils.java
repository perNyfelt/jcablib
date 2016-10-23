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
import java.util.Calendar;
// import java.util.Date;
import java.util.GregorianCalendar;

/**
 * static utilities class for type conversion
 *
 * @author dvh
 */
public class CabUtils {

   public static final byte kNil = 0x00;

   /**
    * Converts DOS time to Java time (number of milliseconds since epoch) XXX deprecated API
    */
   public static long dosToJavaTime(long dtime) {

      int year = (int) (((dtime >> 25) & 0x7f) + 80);
      int month = (int) (((dtime >> 21) & 0x0f) - 1);
      int date = (int) ((dtime >> 16) & 0x1f);
      int hrs = (int) ((dtime >> 11) & 0x1f);
      int min = (int) ((dtime >> 5) & 0x3f);
      int sec = (int) ((dtime << 1) & 0x3e);
      // Date d = new Date(year, month, date, hrs, min, sec);
      Calendar cal = GregorianCalendar.getInstance();
      cal.set(year + 1900, month, date, hrs, min, sec);
      // return d.getTime();
      return cal.getTimeInMillis();
   }

   /**
    * Converts Java time to DOS time. XXX deprecated API
    */
   public static long javaToDosTime(long time) {

      Calendar cal = GregorianCalendar.getInstance();
      cal.setTimeInMillis(time);
      int year = cal.get(Calendar.YEAR);
      int month = cal.get(Calendar.MONTH);
      int date = cal.get(Calendar.DAY_OF_MONTH);
      int hours = cal.get(Calendar.HOUR_OF_DAY);
      int minutes = cal.get(Calendar.MINUTE);
      int sec = cal.get(Calendar.SECOND);
      // Date d = new Date(time);

      if (year < 1980) {
         return (1 << 21) | (1 << 16);
      }
      return (year - 1980) << 25 | (month + 1) << 21 | date << 16 | hours << 11 | minutes << 5
            | sec >> 1;
    /*
     * int year = d.getYear() + 1900; return (year - 1980) << 25 | (d.getMonth() + 1) << 21 |
     * d.getDate() << 16 | d.getHours() << 11 | d.getMinutes() << 5 | d.getSeconds() >> 1;
     */
   }

   /**
    * Rotate an int's byte order; switch big to little or little to big endian
    *
    * @param
    * @return
    */
   public static int rotateInt(int i) {
      int[] bytes = new int[4];
      bytes[0] = (i >> 24) & 0x000000FF;
      bytes[1] = (i >> 8) & 0x0000FF00;
      bytes[2] = (i << 8) & 0x00FF0000;
      bytes[3] = (i << 24) & 0xFF000000;
      return bytes[0] + bytes[1] + bytes[2] + bytes[3];
   }

   /**
    * Rotate an short's byte order; switch big to little or little to big endian
    *
    * @param s
    * @return
    */
   public static short rotateShort(short s) {
      short[] bytes = new short[2];
      bytes[0] = (short) (s >> 8);
      bytes[1] = (short) (s << 8);
      return (short) (bytes[0] + bytes[1]);
   }

   /**
    * Converts c string to java string XXX this is absolutely boneheaded code
    *
    * @param in
    * @return
    * @throws IOException Thrown by DataInput.
    */
   public static String readCString(DataInput in) throws IOException {
      int i = 0;
      byte[] temp = new byte[256];

      while (true) {
         temp[i] = in.readByte();
         if (temp[i] == kNil || i > 255)
            break;
         i++;
      }
      return new String(temp).substring(0, i);
   }

   /**
    * Writes java string to c string in dataoutput.
    *
    * @param inString
    * @param out
    * @throws java.io.IOException
    */
   public static void writeCString(String inString, DataOutput out) throws IOException {
      byte[] buf;
      if (inString.length() > 255)
         buf = inString.substring(0, 255).getBytes();
      else
         buf = inString.getBytes();
      out.write(buf);
      out.writeByte(kNil);
   }

}
