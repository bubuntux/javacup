package org.dsaw.javacup.model.engine;


import org.dsaw.javacup.model.Team;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.swing.*;

/**
 * Clase usada para cargar y guardar partidos, uso interno
 */
public final class StoredMatch implements MatchInterface, Serializable { //TODO rename?

  private static Logger logger = LoggerFactory.getLogger(StoredMatch.class);
  private static final long serialVersionUID = 1L;
  transient private int tiempo = -1;
  transient private Iteration iteration;
  /**
   * Lista de iteraciones, uso interno
   */
  public ArrayList<Iteration> partido = new ArrayList<>(Constants.ITERACIONES);
  private Team detalleLocal;
  private Team detalleVisita;

  /**
   * Instancia un partido guardado indicando los detalles de el local y la visita
   */
  public StoredMatch(Team detalleLocal, Team detalleVisita) {
    this.detalleLocal = detalleLocal;
    this.detalleVisita = detalleVisita;
  }

  /**
   * Elimina desde la iteracion inicio hasta la iteracion fin, usado para resumir partidos
   */
  public void delete(int inicio, int fin) {
    for (int i = inicio; i < fin; i++) {
      partido.remove(inicio);
    }
    setTiempo(inicio);
  }

  /**
   * Inidica la cantidad de iteraciones del partido
   */
  public int getIterciones() {
    return partido.size();
  }

  /**
   * Estable la iteracion actual
   */
  public void setTiempo(int tiempo) {
    if (tiempo < 0) {
      tiempo = 0;
    }
    if (tiempo >= partido.size()) {
      tiempo = partido.size() - 1;
    }
    this.tiempo = tiempo;
    iteration = partido.get(tiempo);
  }

  /**
   * Retorna la iteracion actual
   */
  public int getTiempo() {
    return tiempo;
  }

  @Override
  public boolean esGol() {
    return iteration.gol;
  }

  @Override
  public boolean esPoste() {
    return iteration.poste;
  }

  @Override
  public boolean estaRebotando() {
    return iteration.rebotando;
  }

  @Override
  public boolean estanOvacionando() {
    return iteration.ovacionando;
  }

  @Override
  public boolean estanRematando() {
    return iteration.rematando;
  }

  @Override
  public boolean estanSacando() {
    return iteration.sacando;
  }

  @Override
  public boolean estanSilbando() {
    return iteration.silbando;
  }

  @Override
  public boolean cambioDeSaque() {
    return iteration.cambioSaque;
  }

  @Override
  public boolean isOffSide() {

    return iteration.isOffSide;
  }

  @Override
  public boolean isLibreIndirecto() {

    return iteration.isLibreIndirecto;
  }

  @Override
  public double getAlturaBalon() {
    return (double) iteration.alturaBalon / 256d;
  }

  @Override
  public Team getDetalleLocal() {
    return detalleLocal;
  }

  @Override
  public Team getDetalleVisita() {
    return detalleVisita;
  }

  @Override
  public Position getPosVisibleBalon() {
    return new Position((double) iteration.posVisibleBalonX / 256d,
                        (double) iteration.posVisibleBalonY / 256d);
  }

  @Override
  public Position[][] getPosiciones() {
    Position[][] pos = new Position[3][11];
    for (int i = 0; i < 11; i++) {
      pos[0][i] =
          new Position((double) iteration.posiciones[0][i][0] / 256d,
                       (double) iteration.posiciones[0][i][1] / 256d);
      pos[1][i] =
          new Position((double) iteration.posiciones[1][i][0] / 256d,
                       (double) iteration.posiciones[1][i][1] / 256d);
    }
    pos[2][0] =
        new Position((double) iteration.posiciones[2][0][0] / 256d,
                     (double) iteration.posiciones[2][0][1] / 256d);
    return pos;
  }

  @Override
  public void iterar() throws Exception {
    if (tiempo < partido.size() - 1) {
      tiempo++;
    }
    setTiempo(tiempo);
  }

  @Override
  public int getGolesLocal() {
    return iteration.golesLocal;
  }

  @Override
  public int getGolesVisita() {
    return iteration.golesVisita;
  }

  @Override
  public int getIteration() {
    return iteration.iteracion;
  }

  @Override
  public double getPosesionBalonLocal() {
    return (double) iteration.posecionBalonLocal / 256d;
  }

  @Override
  public StoredMatch getPartidoGuardado() {
    return this;
  }

  private URL url = null;

  /**
   * Retorna la url donde se almacena el partido guardado
   */
  public URL getURL() {
    return url;
  }

  /**
   * Instancia un partido guardado a partir de una url
   */
  public StoredMatch(final URL url) throws Exception {
    StoredMatch pg = null;
    try {
      pg = StoredMatch.load(url);
      this.url = url;
    } catch (Exception ex) {
      logger.error("Error al cargar partido guardado", ex);
    }
    detalleLocal = pg.detalleLocal;
    detalleVisita = pg.detalleVisita;
    partido = pg.partido;
    iteration = partido.get(0);
  }

  private static File unzip(File file) throws Exception {
    ZipFile zf = new ZipFile(file.getAbsolutePath());
    Enumeration e = zf.entries();
    File f;
    ZipEntry ze = (ZipEntry) e.nextElement();
    f = new File(new File(ze.getName()).getName());
    FileOutputStream fout = new FileOutputStream(f);
    InputStream in = zf.getInputStream(ze);
    byte[] buffer = new byte[4096];
    int read;
    while ((read = in.read(buffer)) > -1) {
      fout.write(buffer, 0, read);
    }
    in.close();
    fout.close();
    zf.close();
    return f;
  }

  private static void zip(String entry, File file) throws Exception {
    FileOutputStream fout = new FileOutputStream(file);
    ZipOutputStream zout = new ZipOutputStream(fout);
    zout.setLevel(java.util.zip.Deflater.BEST_SPEED);
    try (FileInputStream fin = new FileInputStream(entry)) {
      zout.putNextEntry(new ZipEntry(entry));
      byte[] buffer = new byte[4096];
      int read;
      while ((read = fin.read(buffer)) > -1) {
        zout.write(buffer, 0, read);
      }
    }
    zout.close();
  }

  /**
   * Muesta un jframe que indica que el partido se esta cargando
   */
  public static boolean SHOWFRAME = true;

  private static StoredMatch load(URL url) throws Exception {
    JFrame frame = null;
    if (SHOWFRAME) {
      frame = new JFrame("Cargando Partido ");
      frame.add(new JLabel(url.toURI().getPath()));
      frame.pack();
      frame.setAlwaysOnTop(true);
      frame.setResizable(false);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
    }
    InputStream is = null;
    Exception e = null;
    byte bytes[] = new byte[4096];
    File tempFile = null;
    File unziped = null;
    try {
      tempFile = File.createTempFile("tmp", ".zip", new File("."));
      tempFile.deleteOnExit();
      FileOutputStream fos = new FileOutputStream(tempFile);
      is = url.openStream();
      int read;
      while ((read = is.read(bytes)) > -1) {
        fos.write(bytes, 0, read);
      }
      fos.close();
      is.close();
      unziped = unzip(tempFile);
      //System.out.println("creando " + unziped);
      unziped.deleteOnExit();
      FileInputStream fis = new FileInputStream(unziped);
      ObjectInputStream ois = new ObjectInputStream(fis);
      StoredMatch p = (StoredMatch) ois.readObject();
      ois.close();
      fis.close();
      tempFile.delete();
      unziped.delete();
      //System.out.println("borrando " + unziped);
      p.iteration = p.partido.get(0);
      if (SHOWFRAME) {
        frame.setVisible(false);
        frame.dispose();
      }
      return p;
    } catch (ClassNotFoundException ex) {
      e = ex;
    } catch (IOException ex) {
      e = ex;
    } finally {
      try {
        is.close();
      } catch (IOException ex) {
        e = ex;
      }
    }
    if (e != null) {
      logger.error("Error al cargar partido guardado", e);
      if (SHOWFRAME) {
        frame.setVisible(false);
        frame.dispose();
      }
      throw e;
    }
    frame.setVisible(false);
    frame.dispose();
    if (tempFile != null && tempFile.exists()) {
      tempFile.delete();
    }
    if (unziped != null && unziped.exists()) {
      unziped.delete();
    }
    return null;
  }

  /**
   * Guarda el partido en un fichero
   */
  public void save(File file) throws Exception {
    FileOutputStream fos = null;
    Exception e = null;
    ObjectOutputStream oos = null;
    File tempFile = File.createTempFile("tmp", ".jvc");
    try {
      fos = new FileOutputStream(tempFile);
      oos = new ObjectOutputStream(fos);
      oos.writeObject(this);
      oos.close();
      fos.close();
      zip(tempFile.getAbsolutePath(), file);
      tempFile.delete();
    } catch (IOException ex) {
      e = ex;
    } finally {
      try {
        fos.close();
      } catch (IOException ex) {
        e = ex;
      }
      try {
        oos.close();
      } catch (IOException ex) {
        e = ex;
      }
    }
    if (e != null) {
      logger.error("Error al guardar partido", e);
      throw e;
    }
  }

  @Override
  public boolean fueGrabado() {
    return false;
  }


}
