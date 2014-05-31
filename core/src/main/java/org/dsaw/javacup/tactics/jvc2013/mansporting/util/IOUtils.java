/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author MaN
 */
public final class IOUtils {

  private static final Logger log = Logger.getLogger(IOUtils.class.getName());
  private static final int BUFFER_SIZE = 4 * 1024;

  private IOUtils() {
  }

  public static void copy(Object o, ObjectOutputStream os) throws IOException {
    try {
      os.writeObject(o);
    } finally {
      close(os);
    }
  }

  public static <E> E copy(Class<E> clazz, ObjectInputStream is) throws IOException {
    try {
      Object result = is.readObject();
      if (!clazz.isAssignableFrom(result.getClass())) {
        throw new ClassCastException("El objeto no es assignable a la clase " + clazz);
      }
      return (E) result;
    } catch (ClassCastException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } finally {
      close(is);
    }
  }

  public static int copy(InputStream is, ZipOutputStream os, String filename) throws IOException {
    os.putNextEntry(new ZipEntry(filename));
    return copy(is, os);
  }

  public static int copy(ZipInputStream is, OutputStream os, String filename) throws IOException {
    ZipEntry zipEntry;
    while ((zipEntry = is.getNextEntry()) != null) {
      if (zipEntry.getName().equals(filename)) {
        break;
      } else {
        zipEntry = null;
      }
    }
    if (zipEntry == null) {
      throw new IOException(String.format("entry [%s] no encontrada", filename));
    }

    return copy(is, os);
  }

  public static int copy(InputStream is, OutputStream os) throws IOException {
    try {
      int readed, totalReaded = 0;
      byte[] buffer = new byte[BUFFER_SIZE];
      while ((readed = is.read()) != -1) {
        os.write(buffer, 0, readed);
        totalReaded += readed;
      }
      os.flush();
      return totalReaded;
    } finally {
      close(is);
      close(os);
    }
  }

  public static int copy(Reader is, Writer os) throws IOException {
    try {
      int readed, totalReaded = 0;
      char[] buffer = new char[BUFFER_SIZE];
      while ((readed = is.read()) > 0) {
        os.write(buffer, 0, readed);
        totalReaded += readed;
      }
      os.flush();
      return totalReaded;
    } finally {
      close(is);
      close(os);
    }
  }

  public static void close(Closeable closeable) {
    try {
      closeable.close();
    } catch (Throwable e) {
      log.log(Level.SEVERE, "Error cerrando el recurso", e);
    }
  }
}
