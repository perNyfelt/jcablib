# jcablib
This is a fork of the cablib project on SF (https://sourceforge.net/projects/cablib/).
Forked at 2016-Oct-22 (SF svn commit version r9).

### Changes since since the SF version
- I've upgraded it to Java 8 and fixed a lot of use of deprected methods.
- Added dependency management (maven) but kept the original ant based build.
- Applied some patches that were submitted but not accepted into the SF project.
- Added a class with high level API usage for easier consumption
- Added some Junit tests

# Known issues
Right now only uncompressed CAB files works - Mzip compressed cabs have some issue
 as seen in the following test result:
 ```
 Testcase: testMzipCompressedCab took 0.176 sec
 	Caused an ERROR
 invalid distance too far back
 net.sf.jcablib.CabException: invalid distance too far back
 	at net.sf.jcablib.MSZipInputStream.read(MSZipInputStream.java:91)
 	at net.sf.jcablib.CabFileInputStream.read(CabFileInputStream.java:147)
 	at java.io.InputStream.read(InputStream.java:101)
 	at java.nio.file.Files.copy(Files.java:2908)
 	at java.nio.file.Files.copy(Files.java:3027)
 	at net.sf.jcablib.CabExtractor.extract(CabExtractor.java:59)
 	at net.sf.jcablib.CabExtractor.extract(CabExtractor.java:42)
 	at net.sf.jcablib.CabExtractionTest.testMzipCompressedCab(CabExtractionTest.java:31)
```
## The following is the original README.txt:

********************************* jcablib 0.1 **********************************

jcablib is a Java(tm) library for extracting microsoft cabinet files. Its main 
purpose in life is to provide a means of access to libraries that may have been
distributed as cabinet files for use with Microsoft(tm) Java(tm), or perhaps a
pre-CLR version of Microsoft J#(tm). Essentially, this is code that's intended 
as a shim to cut through the "embrace and extend" tactics and help get some work 
done.

****************************** Programmer's notes ******************************

jcablib is provided under the GPL or the LGPL and I'm open to relicensing the 
thing in the event it becomes the issue.

jcablib doesn't yet support LZX or Quantum compression methods, most often used 
in windows-only binary software distribution. Java software packages with 
microsoft's java software development kit is generally compressed using the 
mszip method.

I've provided a programmers interface similar to java.util.zip.ZipFile. Files 
in cabinets are grouped together into structures referred to as folders. From 
the folder header, we can discover the offset of the beginning of any file in 
the folder, but only once the entire folder has been decompressed. Because of 
this, when extracting the full contents of a cab it's most efficient to extract 
a folder at a time rather than a file at a time. In the example programs, there 
are some assumptions about the order in which files are returned. This is for 
performance reasons, and it's an example worth following.


********************************* GPL notice ***********************************

jcablib, a library for extracting MS cabinets
Copyright (C) 1999  David V. Himelright

This program is free software; you can redistribute it and/or modify it under 
the terms of the GNU General Public License as published by the Free Software 
Foundation; either version 2 of the License, or (at your option) any later 
version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY 
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with 
this program; if not, write to the Free Software Foundation, Inc., 59 Temple 
Place - Suite 330, Boston, MA  02111-1307, USA.


******************* How to get the source code and licenses ********************

A copy of the GPL and LGPL, as well as the source code and this document, are 
merged into the distribution library by the build script. I suggest that any 
derived works use a similar approach. If this software is used for commercial 
purposes, if you need to merge the jarfile into your own, make sure you merge 
the source code and licences into the root package directory as well. Users may 
not know it's there, but you will, your project manager will, the guy who has to 
maintain the code when you're gone will, and it's nice to spread a little 
goodwill into the business world about open source.

Thanks!

DVH - 06/2006
