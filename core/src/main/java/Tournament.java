import org.dsaw.javacup.model.Tactic;
import org.dsaw.javacup.model.TacticDetail;
import org.dsaw.javacup.model.engine.Match;
import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.TacticValidate;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

public class Tournament {

  String instancia;
  Tactic local;
  TacticDetail localDetalle;
  Tactic visita;
  TacticDetail visitaDetalle;
  Match partido;

  /**
   * Ejecuta un partido de torneo, se indica la instancia (cuartos, semi, clasificatorias, etc), La
   * fecha de ejecucion y las tacticas
   */
  public Tournament(String instancia, int fecha, Tactic local, Tactic visita) throws Exception {
    JFrame frame = new JFrame();
    frame.setAlwaysOnTop(true);
    Image img = new ImageIcon(getClass().getResource("/imagenes/javacup.png")).getImage();
    frame.setIconImage(img);
    JProgressBar bar = new JProgressBar(0, Constants.ITERACIONES);
    frame.setLayout(new CardLayout());
    frame.add(bar, "bar");
    frame.setSize(480, 48);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    this.instancia = instancia;
    localDetalle = local.getDetail();
    visitaDetalle = visita.getDetail();
    frame.setTitle(localDetalle.getTacticName() + " v/s " + visitaDetalle.getTacticName());
    TacticValidate.validateDetail(localDetalle.getTacticName(), localDetalle);
    TacticValidate.validateDetail(visitaDetalle.getTacticName(), visitaDetalle);
    this.local = local;
    this.visita = visita;
    partido = new Match(local, visita, true);
    //partido.inicioRapido();
    int iter = 0;
    for (int i = 0; partido.getEstado() != 7; i++) {
      bar.setValue(partido.getIteration());
      partido.iterar();
      iter = partido.getIteration();
      if (i > 100000) {
        throw new Exception("partido bloqueado");
      }
    }
    frame.setVisible(false);
    frame.dispose();
    //System.out.println("##### " + partido.getGolesLocal() + "-" + partido.getGolesVisita() + " " + partido.getPosesionBalonLocal() + "-" + (1 - partido.getPosesionBalonLocal()));
  }

  /**
   * Indica 0 si gano el local y 1 si gano la visita. Se considera la posecion del balon
   */
  public int getGanador() {
    if (partido.getGolesLocal() > partido.getGolesVisita()) {
      return 0;
    } else if (partido.getGolesLocal() == partido.getGolesVisita()) {
      if (partido.getPosesionBalonLocal() >= .5) {
        return 0;
      }
    }
    return 1;
  }

  /**
   * Retorna el partido ejecutado
   */
  public Match getPartido() {
    return partido;
  }

  /**
   * Lista de tacticas aceptadas
   */
  private static LinkedList<Class> tacticas = new LinkedList<>();

  /**
   * Metodo practico para crear liguillas todos contra todos, se debe indicar 'n' como la cantidad
   * de equipos. Retorna un array donde: el primer indice representa la fecha el segundo representa
   * el partido de la fecha y el tercer indice 0: equipo local y 1: la visita
   *
   * Nota: No son partidos de ida y vuelta
   */
  public static int[][][] crearLiguilla(int n) {
    boolean impar = n % 2 == 1;
    if (impar) {
      n++;
    }
    int ppf = n / 2;//partidos por fecha
    int p = n * (n - 1) / 2;//partidos total
    int f = p / ppf;//fechas
    int tmp[][][] = new int[f][impar ? ppf - 1 : ppf][2];
    int subTmp[][] = new int[ppf][2];
    int[][] camino = new int[2 * ppf - 1][2];
    int x = 1;
    int y = 0;
    boolean avanza = true;
    for (int i = 0; i < camino.length; i++) {
      camino[i][0] = x;
      camino[i][1] = y;
      if (avanza) {
        x++;
        if (x >= ppf) {
          x--;
          y++;
          avanza = false;
        }
      } else {
        x--;
      }
    }
    for (int i = 0; i < f; i++) {
      for (int j = 0; j < ppf; j++) {
        subTmp[j][0] = -1;
        subTmp[j][1] = -1;
      }
      subTmp[0][0] = 0;
      for (int j = 0; j < camino.length; j++) {
        x = camino[j][0];
        y = camino[j][1];
        subTmp[x][y] = (j + i) % (n - 1) + 1;
      }
      int k = 0;
      for (int j = 0; j < ppf; j++) {
        if (!impar || (impar && subTmp[j][0] != n - 1 && subTmp[j][1] != n - 1)) {
          if (((subTmp[j][0] + subTmp[j][1]) % 2) == 0) {
            int temp = subTmp[j][0];
            subTmp[j][0] = subTmp[j][1];
            subTmp[j][1] = temp;
          }
          int l = subTmp[j][0];
          int v = subTmp[j][1];
          boolean invertir = false;
          if (l >= v) {
            invertir = (l + v) % 2 == 0;
          } else {
            invertir = (l + v + 1) % 2 == 0;
          }
          if (invertir) {
            tmp[i][k][0] = v;
            tmp[i][k][1] = l;
          } else {
            tmp[i][k][0] = l;
            tmp[i][k][1] = v;
          }
          k++;
        }
      }
    }
    return tmp;
  }

  /**
   * Ejecuta una liguilla usando las tacticas definidas en la lista 'tacticas'
   */
  private static void doLiguilla(int n) {
    int puntos[] = new int[n];
    int partidos = n * (n - 1) / 2;
    int fechas = partidos / (n / 2);
    int partidosPorFecha = partidos / fechas;
    System.out.println("##### " + partidos + " partidos, " + fechas + " fechas, " + partidosPorFecha
                       + " partidos por fecha");
    int[][][] tmp = crearLiguilla(n);
    for (int i = 0; i < fechas; i++) {
      for (int j = 0; j < partidosPorFecha; j++) {
        int found[] = new int[]{tmp[i][j][0], tmp[i][j][1]};
        System.out.print(
            "##### fecha " + (i + 1) + ", partido " + (j + 1) + ", juega " + tacticas.get(found[0])
                .getSimpleName() + " vs " + tacticas.get(found[1]).getSimpleName());
        Class l = tacticas.get(found[0]);
        Class v = tacticas.get(found[1]);
        Tournament pt = null;
        try {
          pt = null;
          Constructor cl = (l.getConstructor(new Class[]{}));
          Constructor cv = (v.getConstructor(new Class[]{}));
          Tactic tl = (Tactic) cl.newInstance(new Object[]{});
          Tactic tv = (Tactic) cv.newInstance(new Object[]{});
          try {
            long t0 = System.currentTimeMillis();
            pt = new Tournament("Liguilla", 20090206, tl, tv);
            System.out.print("\n(" + (System.currentTimeMillis() - t0) / 1000 + " sec)\t");
          } catch (Exception e) {
            e.printStackTrace();
          }
          System.out.print(
              pt.getPartido().getGolesLocal() + "-" + pt.getPartido().getGolesVisita() + "\t");
          int gano = pt.getGanador();
          if (pt.getPartido().getGolesLocal() == pt.getPartido().getGolesVisita()) {
            System.out.print(gano == 0 ? " gana local" : " gana visita");
          }
          System.out.println();
          puntos[found[gano]]++;
        } catch (Exception ex) {
          try {
            pt.getPartido().getPartidoGuardado()
                .save(new File("c:\\" + System.currentTimeMillis()));
          } catch (Exception e) {
            e.printStackTrace();
          }
          ex.printStackTrace();
        }
      }
      for (int k = 0; k < n; k++) {
        String s = tacticas.get(k).toString();
        s = s.substring(s.lastIndexOf(".") + 1);
        System.out.println(s + ":" + puntos[k]);
      }
    }
    System.out.println("\n\nORDEN FINAL\n\n");
    Class c;
    int pt;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        if (puntos[j] > puntos[i]) {
          c = tacticas.get(i);
          pt = puntos[i];
          tacticas.set(i, tacticas.get(j));
          tacticas.set(j, c);
          puntos[i] = puntos[j];
          puntos[j] = pt;
        }
      }
      System.out.println(tacticas.get(i).getName() + ".class,//" + puntos[i] + " puntos.");
    }
  }

  /**
   * Obtiene una lista de clases ubicadas dentro de un paquete que son asignables a una clase
   * especifica
   */
  @SuppressWarnings("unchecked")
  private static Class[] getClases(File src, String paquete, Class claseAsignable,
                                   boolean recursivo) throws Exception {
    paquete = paquete.replace('.', '/');
    LinkedList<Class> classes = new LinkedList<>();
    if (!paquete.endsWith("//svn")) {
      File directory = null;
      try {
        URLClassLoader cld = URLClassLoader.newInstance(new URL[]{src.toURI().toURL()});
        if (cld == null) {
          throw new ClassNotFoundException(
              "Cargador de clases no encontrado.");
        }
        directory = new File(src, paquete);
        if (directory.exists()) {
          File[] files = directory.listFiles();
          String name, cls;
          for (File file : files) {
            name = file.getName();
            if (file.isFile() && name.endsWith(".class")) {
              cls = (paquete + '/' + name.substring(0, name.length() - 6)).replace('/', '.');
              if (cls.startsWith(".")) {
                cls = cls.substring(1);
              }
              Class c = cld.loadClass(cls);
              if (!c.equals(claseAsignable) && claseAsignable.isAssignableFrom(c)) {
                classes.add(c);
              }
            } else if (file.isDirectory()) {
              cls = paquete + "/" + name;
              for (Class c : getClases(src, cls, claseAsignable,
                                       recursivo)) {
                classes.add(c);
              }
            }
          }
        } else {
          throw new ClassNotFoundException(paquete + " no es un paquete valido.");
        }
      } catch (MalformedURLException ex) {
        throw new MalformedURLException(paquete + " (" + src + ") no es una url valido.");
      } catch (NullPointerException x) {
        throw new ClassNotFoundException(paquete + " (" + directory + ") no es un paquete valido.");
      }
    }
    Class[] classesA = new Class[classes.size()];
    classes.toArray(classesA);
    return classesA;
  }

  private static void scanDir(File dir) throws Exception {
    Class[] classes = new Class[]{};
    try {
      classes = getClases(dir, "", Tactic.class, true);
    } catch (Exception e) {
      classes = new Class[]{};
    }
    for (Class c : classes) {
      if (!c.getName().equals(
          "org.javahispano.javacup.tacticas_aceptadas.DaniP.Termineitor$JugadaEnsayadaAvanzayPasa")
          && c.getName().startsWith("org.javahispano.javacup.tacticas_aceptadas")) {
        synchronized (tacticas) {
          tacticas.add(c);
        }
      }
    }
  }

  /**
   * Busca tacticas en el classpath
   */
  private static void scanForTactics(List<File> dir) {
    for (File f : dir) {
      try {
        scanDir(f);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  //tacticas ordenadas segun su resultado en la liguilla previa
  private static Class[] tactics = new Class[]{
      org.dsaw.javacup.tactics.jvc2012.romedal.TacticaRomedalusTeam.class,//32 puntos.
      org.dsaw.javacup.tactics.jvc2012.masia2012.Masia.class,//32 puntos.
      org.dsaw.javacup.tactics.jvc2012.hortalezablamers.HortalezaBlamers.class,//31 puntos.
      org.dsaw.javacup.tactics.jvc2012.bo.ChimuelosTactic.class,//28 puntos.
      org.dsaw.javacup.tactics.jvc2012.losdesistemas.Sistemitas.class,//28 puntos.
      org.dsaw.javacup.tactics.jvc2012.iriveros.Chelsea.class,
//28 puntos. (descalificado por ser copia identica del campeon 2011)
      org.dsaw.javacup.tactics.jvc2012.dbonilla.Millos.class,
//26 puntos. (descalificado por ser copia identica del campeon 2011)
      org.dsaw.javacup.tactics.jvc2012.iria.Iria.class,//25 puntos.
      org.dsaw.javacup.tactics.jvc2012.arturo8a.TacticaPulgarcitos.class,//24 puntos.
      org.dsaw.javacup.tactics.jvc2012.chr2012.tactica.TacticaTiquiTacaTeam.class,//23 puntos.
      org.dsaw.javacup.tactics.jvc2012.fortega.Frioleros.class,//23 puntos.
      org.dsaw.javacup.tactics.jvc2012.team2012.Team2312.class,//22 puntos.
      org.dsaw.javacup.tactics.jvc2012.tukutuku.Tactica.class,//21 puntos.
      org.dsaw.javacup.tactics.jvc2012.davidramirez.TElResultaoDaIgual.class,//20 puntos.
      org.dsaw.javacup.tactics.jvc2012.evolution.SanMarinoEvolution.class,//18 puntos.
      org.dsaw.javacup.tactics.jvc2012.agalan.NewTeam.class,//18 puntos.
      org.dsaw.javacup.tactics.jvc2012.losjavatos.LosJavatos.class,//17 puntos.
      org.dsaw.javacup.tactics.jvc2012.mijarojos.Mijarojos.class,//17 puntos.
      org.dsaw.javacup.tactics.jvc2012.moleteam.MoleTeam.class,//13 puntos.
      org.dsaw.javacup.tactics.jvc2012.lgarza.Tactica.class,//13 puntos.
      org.dsaw.javacup.tactics.jvc2012.srh.SRH.class,//12 puntos.
      org.dsaw.javacup.tactics.jvc2012.abonilla.Arsenal4.class,//11 puntos.
      org.dsaw.javacup.tactics.jvc2012.realvencedores.RealVencedoresJuego.class,//11 puntos.
      org.dsaw.javacup.tactics.jvc2012.pringaos.Pringaos2012.class,//11 puntos.
      org.dsaw.javacup.tactics.jvc2012.adeleon.Manada.class,//11 puntos.
      org.dsaw.javacup.tactics.jvc2012.emendoza.Junior.class,//8 puntos.
      org.dsaw.javacup.tactics.jvc2012.ateixeira.Artenara.class,//7 puntos.
      org.dsaw.javacup.tactics.jvc2012.lacras.lacras.class,//7 puntos.
      org.dsaw.javacup.tactics.jvc2012.rchavarria.RChavarriaTactic.class,//7 puntos.
      org.dsaw.javacup.tactics.jvc2012.petardos.Petardos.class,//5 puntos.
      org.dsaw.javacup.tactics.jvc2012.vnavarro.Pequeo.class,//4 puntos.
      org.dsaw.javacup.tactics.jvc2012.sotilezos.Sotilezos.class,//3 puntos.
      org.dsaw.javacup.tactics.jvc2012.celta.Celta.class,//3 puntos.
      org.dsaw.javacup.tactics.jvc2012.tomatesmecanicos.tomates.class,//2 puntos.
  };

  //array de grupos de la primera fase
  private static Class[][] gruposPublicados() {
    return new Class[][]{
        {org.dsaw.javacup.tactics.jvc2012.hortalezablamers.HortalezaBlamers.class,
         org.dsaw.javacup.tactics.jvc2012.sotilezos.Sotilezos.class,
         org.dsaw.javacup.tactics.jvc2012.evolution.SanMarinoEvolution.class,
         org.dsaw.javacup.tactics.jvc2012.realvencedores.RealVencedoresJuego.class,},
        {org.dsaw.javacup.tactics.jvc2012.davidramirez.TElResultaoDaIgual.class,
         org.dsaw.javacup.tactics.jvc2012.mijarojos.Mijarojos.class,
         org.dsaw.javacup.tactics.jvc2012.losdesistemas.Sistemitas.class,
         org.dsaw.javacup.tactics.jvc2012.emendoza.Junior.class,},
        {org.dsaw.javacup.tactics.jvc2012.iria.Iria.class,
         org.dsaw.javacup.tactics.jvc2012.chr2012.tactica.TacticaTiquiTacaTeam.class,
         org.dsaw.javacup.tactics.jvc2012.ateixeira.Artenara.class,
         org.dsaw.javacup.tactics.jvc2012.pringaos.Pringaos2012.class,},
        {org.dsaw.javacup.tactics.jvc2012.agalan.NewTeam.class,
         org.dsaw.javacup.tactics.jvc2012.lacras.lacras.class,
         org.dsaw.javacup.tactics.jvc2012.romedal.TacticaRomedalusTeam.class,
         org.dsaw.javacup.tactics.jvc2012.srh.SRH.class,},
        {org.dsaw.javacup.tactics.jvc2012.iriveros.Chelsea.class,
         org.dsaw.javacup.tactics.jvc2012.arturo8a.TacticaPulgarcitos.class,
         org.dsaw.javacup.tactics.jvc2012.losjavatos.LosJavatos.class,
         org.dsaw.javacup.tactics.jvc2012.petardos.Petardos.class,},
        {org.dsaw.javacup.tactics.jvc2012.rchavarria.RChavarriaTactic.class,
         org.dsaw.javacup.tactics.jvc2012.abonilla.Arsenal4.class,
         org.dsaw.javacup.tactics.jvc2012.tukutuku.Tactica.class,
         org.dsaw.javacup.tactics.jvc2012.bo.ChimuelosTactic.class,},
        {org.dsaw.javacup.tactics.jvc2012.adeleon.Manada.class,
         org.dsaw.javacup.tactics.jvc2012.masia2012.Masia.class,
         org.dsaw.javacup.tactics.jvc2012.fortega.Frioleros.class,
         org.dsaw.javacup.tactics.jvc2012.celta.Celta.class,
         org.dsaw.javacup.tactics.jvc2012.lgarza.Tactica.class,},
        {org.dsaw.javacup.tactics.jvc2012.team2012.Team2312.class,
         org.dsaw.javacup.tactics.jvc2012.dbonilla.Millos.class,
         org.dsaw.javacup.tactics.jvc2012.vnavarro.Pequeo.class,
         org.dsaw.javacup.tactics.jvc2012.moleteam.MoleTeam.class,
         org.dsaw.javacup.tactics.jvc2012.tomatesmecanicos.tomates.class,},};
  }

  static Random r = new Random();

  //genera grupos parejos segun la clasificacion en la liguilla
  private static Class[][] gruposFase1(int... equipos) {
    Class[] copy = tactics.clone();
    int max = 0;
    for (int i : equipos) {
      if (max < i) {
        max = i;
      }
    }
    int idx = 0, idx0 = 0;
    Class[][] grupos = new Class[equipos.length][max];
    for (int j = 0; j < max; j++) {
      int sum = 0;
      for (int equipo : equipos) {
        if (j < equipo) {
          sum++;
        }
      }
      for (int i = 0; i < equipos.length; i++) {
        if (j < equipos[i]) {
          do {
            idx = idx0 + r.nextInt(sum);
          } while (copy[idx] == null);
          grupos[i][j] = copy[idx];
          copy[idx] = null;
        }
      }
      idx0 = idx0 + sum;
    }
    Class c;
    for (int i = 0; i < equipos.length; i++) {
      for (int j = 0; j < 100; j++) {
        idx = r.nextInt(equipos[i]);
        idx0 = r.nextInt(equipos[i]);
        c = grupos[i][idx];
        grupos[i][idx] = grupos[i][idx0];
        grupos[i][idx0] = c;
      }
    }
    return grupos;
  }

  public static void printGrupos(Class[][] clases) {
    System.out.println("{");
    for (Class[] clase : clases) {
      System.out.print("\t{");
      for (Class clas : clase) {
        if (clas != null) {
          System.out.print(clas.getName() + ".class,\n\t");
        }
      }
      System.out.println("},");
    }
    System.out.print("}");
  }

  //formatea el archivo de resultados
  static String format(String s) {
    int idx0 = s.indexOf(" partidos:");
    String equipo = s.substring(0, idx0);
    int partidos = Integer.parseInt(s.substring(idx0 + 10, s.indexOf(" ", idx0 + 1)));
    idx0 = s.indexOf(" puntos:");
    int puntos = Integer.parseInt(s.substring(idx0 + 8, s.indexOf(" ", idx0 + 1)));
    int difgol = Integer.parseInt(s.substring(s.lastIndexOf(":") + 1).trim());
    return "PUNTOS:" + spaces(puntos) + " DIF.GOLES:" + spaces(difgol) + " PARTIDOS:" + spaces(
        partidos) + " " + equipo;
  }

  //genera un string con espacios
  static String spaces(int num) {
    String s = "" + num;
    while (s.length() < 3) {
      s = " " + s;
    }
    return s;
  }

  //muestra los puntos y goles parciales de la fase preliminar toma como entrada el fichero de resultados
  static void main2() throws Exception {
    String[] reporteDiario = new String[7];
    for (int i = 0; i < 7; i++) {
      reporteDiario[i] = "";
    }
    String s;
    BufferedReader
        r =
        new BufferedReader(new FileReader(new File("H:\\torneo\\torneo2009\\resultados.txt")));
    int grupo = -1;
    int fecha = -1;
    int dia = -1;

    while ((s = r.readLine()) != null) {
      if (s.startsWith("Grupo ")) {
        grupo = Integer.parseInt(s.substring(5).trim()) - 1;
      }
      if (s.contains("Fecha ")) {
        fecha = Integer.parseInt(s.trim().substring(6)) - 1;
        dia = -1;
        if (grupo < 7 && fecha != 0 && fecha != 7) {
          if (fecha < 7) {
            dia = fecha - 1;
          }
          if (fecha == 8) {
            dia = 6;
          }
        }
        if (grupo == 7) {
          dia = fecha;
        }
      }
      if (s.contains("partidos:") && dia >= 0) {
        reporteDiario[dia] =
            reporteDiario[dia] + "DIA:" + (dia + 1) + " GRUPO " + (grupo + 1) + " | " + format(s)
            + "\n";
      }
    }
    r.close();
    for (String ss : reporteDiario) {
      System.out.println(ss);
    }
  }

  //array de equipos clasificados en la fase uno
  static Class[][] clasificados = new Class[][]{
      {
          //GRUPO A
          org.dsaw.javacup.tactics.jvc2012.hortalezablamers.HortalezaBlamers.class,
          org.dsaw.javacup.tactics.jvc2012.evolution.SanMarinoEvolution.class,}, {
          //GRUPO B
          org.dsaw.javacup.tactics.jvc2012.losdesistemas.Sistemitas.class,
          org.dsaw.javacup.tactics.jvc2012.davidramirez.TElResultaoDaIgual.class,}, {
          //GRUPO C
          org.dsaw.javacup.tactics.jvc2012.iria.Iria.class,
          org.dsaw.javacup.tactics.jvc2012.chr2012.tactica.TacticaTiquiTacaTeam.class,}, {
          //GRUPO D
          org.dsaw.javacup.tactics.jvc2012.romedal.TacticaRomedalusTeam.class,
          org.dsaw.javacup.tactics.jvc2012.agalan.NewTeam.class,}, {
          //GRUPO E
          org.dsaw.javacup.tactics.jvc2012.arturo8a.TacticaPulgarcitos.class,
          org.dsaw.javacup.tactics.jvc2012.losjavatos.LosJavatos.class}, {
          //GRUPO F
          org.dsaw.javacup.tactics.jvc2012.bo.ChimuelosTactic.class,
          org.dsaw.javacup.tactics.jvc2012.tukutuku.Tactica.class,}, {
          //GRUPO G
          org.dsaw.javacup.tactics.jvc2012.masia2012.Masia.class,
          org.dsaw.javacup.tactics.jvc2012.fortega.Frioleros.class,}, {
          //GRUPO H
          org.dsaw.javacup.tactics.jvc2012.team2012.Team2312.class,
          org.dsaw.javacup.tactics.jvc2012.moleteam.MoleTeam.class,}
  };

  //ejecuta la segunda fase (eliminatorias)... guarda los partidos en path
  static void eliminatorias(String path) {
    File f = new File(path);
    if (!f.exists()) {
      f.mkdirs();
    }
    f = new File(path + File.separator + "08");
    if (!f.exists()) {
      f.mkdirs();
    }
    f = new File(path + File.separator + "04");
    if (!f.exists()) {
      f.mkdirs();
    }
    f = new File(path + File.separator + "SF");
    if (!f.exists()) {
      f.mkdirs();
    }
    f = new File(path + File.separator + "FF");
    if (!f.exists()) {
      f.mkdirs();
    }
    Tactic tl, tv;
    Tournament pt = null;
    boolean ok;
    LinkedList<Class> pasan = new LinkedList<>();
    LinkedList<Class> pasan2 = new LinkedList<>();
    Match p = null;
    for (Class[] clases : clasificados) {
      pasan.addAll(Arrays.asList(clases));
    }

    int[][]
        cruce =
        new int[][]{{0, 9}, {1, 8}, {2, 11}, {3, 10}, {4, 13}, {5, 12}, {6, 15}, {7, 14}};
    for (int[] aCruce2 : cruce) {
      tl = instance(pasan.get(aCruce2[0]));
      tv = instance(pasan.get(aCruce2[1]));
      String nl = tl.getDetail().getTacticName(), nv = tv.getDetail().getTacticName();
      nl = nl.trim().toLowerCase();
      nl =
          nl.replaceAll(" ", "_").replaceAll("ñ", "n").replaceAll("á", "a").replaceAll("é", "e")
              .replaceAll("í", "i").replaceAll("ó", "o").replaceAll("ú", "u");
      nv = nv.trim().toLowerCase();
      nv =
          nv.replaceAll(" ", "_").replaceAll("ñ", "n").replaceAll("á", "a").replaceAll("é", "e")
              .replaceAll("í", "i").replaceAll("ó", "o").replaceAll("ú", "u");
      String filename = path + File.separator + "08" + File.separator + nl + "-" + nv + ".jvc";
      do {
        ok = true;
        try {
          pt = new Tournament(" 8°", 20090614, tl, tv);
          p = pt.getPartido();
        } catch (Exception e) {
          ok = false;
        }
      } while (!ok);
      System.out.println(
          " 8° -> " + nl + " " + p.getGolesLocal() + " - " + nv + " " + p.getGolesVisita() + "     "
          + p.getPosesionBalonLocal() + "-" + (1 - p.getPosesionBalonLocal()));
      pasan2.add(pt.getGanador() == 0 ? tl.getClass() : tv.getClass());
      try {
        p.getPartidoGuardado().save(new File(filename));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    pasan.clear();
    cruce = new int[][]{{0, 4}, {1, 5}, {2, 6}, {3, 7}};
    for (int[] aCruce1 : cruce) {
      tl = instance(pasan2.get(aCruce1[0]));
      tv = instance(pasan2.get(aCruce1[1]));
      String nl = tl.getDetail().getTacticName(), nv = tv.getDetail().getTacticName();
      nl = nl.trim().toLowerCase();
      nl =
          nl.replaceAll(" ", "_").replaceAll("ñ", "n").replaceAll("á", "a").replaceAll("é", "e")
              .replaceAll("í", "i").replaceAll("ó", "o").replaceAll("ú", "u");
      nv = nv.trim().toLowerCase();
      nv =
          nv.replaceAll(" ", "_").replaceAll("ñ", "n").replaceAll("á", "a").replaceAll("é", "e")
              .replaceAll("í", "i").replaceAll("ó", "o").replaceAll("ú", "u");
      String filename = path + File.separator + "04" + File.separator + nl + "-" + nv + ".jvc";
      do {
        ok = true;
        try {
          pt = new Tournament(" 4°", 20090614, tl, tv);
          p = pt.getPartido();
        } catch (Exception e) {
          ok = false;
        }
      } while (!ok);
      System.out.println(
          " 4° -> " + nl + " " + p.getGolesLocal() + " - " + nv + " " + p.getGolesVisita() + "     "
          + p.getPosesionBalonLocal() + "-" + (1 - p.getPosesionBalonLocal()));
      pasan.add(pt.getGanador() == 0 ? tl.getClass() : tv.getClass());
      try {
        p.getPartidoGuardado().save(new File(filename));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    pasan2.clear();
    cruce = new int[][]{{0, 2}, {1, 3}};
    for (int[] aCruce : cruce) {
      tl = instance(pasan.get(aCruce[0]));
      tv = instance(pasan.get(aCruce[1]));
      String nl = tl.getDetail().getTacticName(), nv = tv.getDetail().getTacticName();
      nl = nl.trim().toLowerCase();
      nl =
          nl.replaceAll(" ", "_").replaceAll("ñ", "n").replaceAll("á", "a").replaceAll("é", "e")
              .replaceAll("í", "i").replaceAll("ó", "o").replaceAll("ú", "u");
      nv = nv.trim().toLowerCase();
      nv =
          nv.replaceAll(" ", "_").replaceAll("ñ", "n").replaceAll("á", "a").replaceAll("é", "e")
              .replaceAll("í", "i").replaceAll("ó", "o").replaceAll("ú", "u");
      String filename = path + File.separator + "SF" + File.separator + nl + "-" + nv + ".jvc";
      do {
        ok = true;
        try {
          pt = new Tournament("semi", 20090614, tl, tv);
          p = pt.getPartido();
        } catch (Exception e) {
          ok = false;
        }
      } while (!ok);
      System.out.println(
          "SF -> " + nl + " " + p.getGolesLocal() + " - " + nv + " " + p.getGolesVisita() + "     "
          + p.getPosesionBalonLocal() + "-" + (1 - p.getPosesionBalonLocal()));
      pasan2.add(pt.getGanador() == 0 ? tl.getClass() : tv.getClass());
      pasan2.add(pt.getGanador() == 0 ? tv.getClass() : tl.getClass());
      try {
        p.getPartidoGuardado().save(new File(filename));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    cruce = new int[][]{{1, 3}, {0, 2}};
    for (int i = 0; i < cruce.length; i++) {
      tl = instance(pasan2.get(cruce[i][0]));
      tv = instance(pasan2.get(cruce[i][1]));
      String nl = tl.getDetail().getTacticName(), nv = tv.getDetail().getTacticName();
      nl = nl.trim().toLowerCase();
      nl =
          nl.replaceAll(" ", "_").replaceAll("ñ", "n").replaceAll("á", "a").replaceAll("é", "e")
              .replaceAll("í", "i").replaceAll("ó", "o").replaceAll("ú", "u");
      nv = nv.trim().toLowerCase();
      nv =
          nv.replaceAll(" ", "_").replaceAll("ñ", "n").replaceAll("á", "a").replaceAll("é", "e")
              .replaceAll("í", "i").replaceAll("ó", "o").replaceAll("ú", "u");
      String filename = path + File.separator + "FF" + File.separator + nl + "-" + nv + ".jvc";
      do {
        ok = true;
        try {
          pt = new Tournament("", 20090614, tl, tv);
          p = pt.getPartido();
        } catch (Exception e) {
          ok = false;
        }
      } while (!ok);
      if (i == 0) {
        System.out.println(
            "34 -> " + nl + " " + p.getGolesLocal() + " - " + nv + " " + p.getGolesVisita()
            + "     " + p.getPosesionBalonLocal() + "-" + (1 - p.getPosesionBalonLocal()));
      }
      if (i == 1) {
        System.out.println(
            "12 -> " + nl + " " + p.getGolesLocal() + " - " + nv + " " + p.getGolesVisita()
            + "     " + p.getPosesionBalonLocal() + "-" + (1 - p.getPosesionBalonLocal()));
      }
      try {
        p.getPartidoGuardado().save(new File(filename));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  //ejecuta la fase 1, guarda los resultados en path
  static void primeraFase(String path) {
    HashMap<Class, Integer> puntos = new HashMap<>();
    HashMap<Class, Integer> goles = new HashMap<>();
    HashMap<Class, Integer> golesafavor = new HashMap<>();
    HashMap<Class, Integer> juegos = new HashMap<>();
    for (Class c : tactics) {
      puntos.put(c, 0);
      goles.put(c, 0);
      golesafavor.put(c, 0);
      juegos.put(c, 0);
    }

    int[] equipos = new int[]{4, 4, 4, 4, 4, 4, 5, 5};
    //int[] equipos=new int[]{4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,4};
    //Class[][] grupos = gruposFase1(equipos);
    Class[][] grupos = gruposPublicados();
    for (int i = 0; i < grupos.length; i++) {
      System.out.println("Grupo " + (i + 1));
      for (int j = 0; j < grupos[i].length && grupos[i][j] != null; j++) {
        System.out.println("\t" + instance(grupos[i][j]).getDetail().getTacticName());
      }
    }
    File dir = new File(path);
    dir.mkdirs();
    System.out.println(
        "-----------------------------------------------------------------------------------------------");
    Tactic tl, tv;
    boolean ok;
    for (int k = 0; k < grupos.length; k++) {
      System.out.println("Grupo " + (k + 1));
      int[][][] partidos = crearLiguilla(equipos[k]);
      for (int i = 0; i < partidos.length; i++) {
        System.out.println("\tFecha " + (i + 1));
        File f = new File(path + File.separator + "grupo" + (k + 1) + "fecha" + (i + 1));
        f.mkdirs();
        for (int j = 0; j < partidos[i].length; j++) {
          ok = true;
          Class cl = grupos[k][partidos[i][j][0]];
          Class cv = grupos[k][partidos[i][j][1]];
          tl = instance(cl);
          tv = instance(cv);
          String nl = tl.getDetail().getTacticName(), nv = tv.getDetail().getTacticName();
          nl = nl.trim().toLowerCase();
          nl =
              nl.replaceAll(" ", "_").replaceAll("ñ", "n").replaceAll("á", "a").replaceAll("é", "e")
                  .replaceAll("í", "i").replaceAll("ó", "o").replaceAll("ú", "u");
          nv = nv.trim().toLowerCase();
          nv =
              nv.replaceAll(" ", "_").replaceAll("ñ", "n").replaceAll("á", "a").replaceAll("é", "e")
                  .replaceAll("í", "i").replaceAll("ó", "o").replaceAll("ú", "u");
          System.out.println("\t\t" + nl + " vs " + nv);
          String
              filename =
              path + File.separator + "grupo" + (k + 1) + "fecha" + (i + 1) + File.separator + nl
              + "-" + nv + ".jvc";
          Tournament pt = null;
          do {
            ok = true;
            try {
              pt = new Tournament("Primera Ronda", i + 1, tl, tv);
            } catch (Exception e) {
              ok = false;
            }
          } while (!ok);
          Match p = pt.getPartido();
          try {
            p.getPartidoGuardado().save(new File(filename));
          } catch (Exception e) {
            e.printStackTrace();
          }

          System.out.println(
              "\t\t\tRESULTADO: " + p.getGolesLocal() + "-" + p.getGolesVisita() + "\t" + p
                  .getPosesionBalonLocal() + "-" + (1 - p.getPosesionBalonLocal()));
          goles.put(cl, goles.get(cl) + p.getGolesLocal() - p.getGolesVisita());
          goles.put(cv, goles.get(cv) + p.getGolesVisita() - p.getGolesLocal());
          golesafavor.put(cl, golesafavor.get(cl) + p.getGolesLocal());
          golesafavor.put(cv, golesafavor.get(cv) + p.getGolesVisita());
          juegos.put(cv, juegos.get(cv) + 1);
          juegos.put(cl, juegos.get(cl) + 1);
          int pl = 0;
          int pv = 0;
          if (p.getGolesLocal() == p.getGolesVisita()) {
            pl = 1;
            pv = 1;
          } else if (p.getGolesLocal() > p.getGolesVisita()) {
            pl = 3;
          } else {
            pv = 3;
          }
          puntos.put(cl, puntos.get(cl) + pl);
          puntos.put(cv, puntos.get(cv) + pv);
        }

        for (int j = 0; j < grupos[k].length; j++) {
          Class c = grupos[k][j];
          if (c != null) {
            System.out.println(
                instance(c).getDetail().getTacticName() + " partidos:" + juegos.get(c) + " puntos:"
                + puntos.get(c) + " diferencia de goles:" + goles.get(c) + " goles a favor:"
                + golesafavor.get(c));
          }
        }
      }
    }
  }

  //obtiene una instancia de una clase que implementa Tactic
  public static Tactic instance(Class cls) {
    try {
      Constructor cons = (cls.getConstructor(new Class[]{}));
      return (Tactic) cons.newInstance(new Object[]{});
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }

  //ejecuta la liguilla previa
  public static void liguilla() {
    tacticas.addAll(Arrays.asList(tactics));
    int idx;
    Class c;
    Random rand = new Random();
    for (int i = 0; i < tacticas.size(); i++) {
      idx = rand.nextInt(tacticas.size());
      c = tacticas.get(i);
      tacticas.set(i, tacticas.get(idx));
      tacticas.set(idx, c);
    }
    int j = -1;
    for (Class cc : tacticas) {
      j++;
      System.out.println(cc.getName() + ",");
    }
    long t0 = System.currentTimeMillis();
    doLiguilla(tacticas.size());
    long t1 = System.currentTimeMillis();
    t1 = (t1 - t0) / 60000;
    System.out.println("Tiempo total " + t1 + " min");
  }

  public static void main(String[] args) throws Exception {
    for (Class t : tactics) {
      Tactic tl = instance(t);
      Tactic tv = instance(t);
      long t0 = System.currentTimeMillis();
      new Tournament("", 0, tl, tv);
      System.out.println(t + " " + (System.currentTimeMillis() - t0));
    }
    //liguilla();
    //printGrupos(gruposFase1(4, 4, 4, 4, 4, 4, 5, 5));
    //primeraFase("c:\\jvc2012");
    //eliminatorias("c:\\jvc2012");
  }
}