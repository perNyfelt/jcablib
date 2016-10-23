/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.jcablib;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author DVH
 */
public class CabOutputStreamTest {

   public CabOutputStreamTest() {
   }

   @org.junit.BeforeClass
   public static void setUpClass() throws Exception {
   }

   @org.junit.AfterClass
   public static void tearDownClass() throws Exception {
   }

   @org.junit.Before
   public void setUp() throws Exception {
   }

   @org.junit.After
   public void tearDown() throws Exception {
   }
/*
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }*/

   /**
    * Test of close method, of class CabOutputStream.
    */
   @org.junit.Test
   public void close() {
      System.out.println("close");
      CabOutput instance = null;
      instance.closeFolder();
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of closeEntry method, of class CabOutputStream.
    */
   @org.junit.Test
   public void closeEntry() {
      System.out.println("closeEntry");
      CabOutput instance = null;
      instance.closeEntry();
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of finish method, of class CabOutputStream.
    */
   @org.junit.Test
   public void finish() {
      System.out.println("finish");
      CabOutput instance = null;
      try {
         instance.finish();
      } catch (IOException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of putNextEntry method, of class CabOutputStream.
    */
   @org.junit.Test
   public void putNextEntry() throws CabException {
      System.out.println("putNextEntry");
      CabEntry arg0 = null;
      CabOutput instance = null;
      instance.putNextEntry(arg0);
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of setComment method, of class CabOutputStream.
    */
   @org.junit.Test
   public void setComment() {
      System.out.println("setComment");
      String arg0 = "";
      CabOutput instance = null;
      //instance.setComment(arg0);
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of setLevel method, of class CabOutputStream.
    */
   @org.junit.Test
   public void setLevel() {
      System.out.println("setLevel");
      int arg0 = 0;
      CabOutput instance = null;
      // instance.setLevel(arg0);
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of setMethod method, of class CabOutputStream.
    */
   @org.junit.Test
   public void setMethod() {
      System.out.println("setMethod");
      int arg0 = 0;
      CabOutput instance = null;
      // instance.setMethod(arg0);
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of write method, of class CabOutputStream.
    */
   @org.junit.Test
   public void write() {
      System.out.println("write");
      byte[] arg0 = null;
      int arg1 = 0;
      int arg2 = 0;
      CabOutput instance = null;
      //instance.write(arg0, arg1, arg2);
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

}