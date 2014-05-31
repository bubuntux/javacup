package org.dsaw.javacup.tactics.jvc2013.CTeam.util;

/*
 * Copyright (c) 2005, 2007 by L. Paul Chew.
 *
 * Permission is hereby granted, without written agreement and without
 * license or royalty fees, to use, copy, modify, and distribute this
 * software and its documentation for any purpose, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

import org.dsaw.javacup.model.engine.GameSituations;
import org.dsaw.javacup.tactics.jvc2013.CTeam.jugador.IJugadorCT;
import org.dsaw.javacup.tactics.jvc2013.CTeam.tactica.ICTeam2011;
import org.dsaw.javacup.tactics.jvc2013.CTeam.tactica.PosicionCT;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.*;

@SuppressWarnings("serial")
public class DelaunayPanel extends JPanel {

  public static Color voronoiColor = Color.magenta;
  public static Color delaunayColor = Color.green;
  public static int pointRadius = 3;

  private Triangulation dt; // Delaunay triangulation
  private Map<Object, Color> colorTable; // Remembers colors for display
  private Triangle initialTriangle; // Initial triangle
  private static int initialSize = 10000; // Size of initial triangle
  private Graphics g; // Stored graphics context
  private Random random = new Random(); // Source of random numbers
  private GameSituations sp;
  private ICTeam2011 tactica;

  /**
   * Create and initialize the DT.
   */
  public DelaunayPanel(GameSituations sp, ICTeam2011 tactica) {
    initialTriangle = new Triangle(new Pnt(-initialSize, -initialSize),
                                   new Pnt(initialSize, -initialSize), new Pnt(0, initialSize));
    dt = new Triangulation(initialTriangle);
    this.sp = sp;
    this.tactica = tactica;
    colorTable = new HashMap<>();
  }

  public void setTriangulation(Triangulation dt, GameSituations sp) {
    this.sp = sp;
    this.dt = dt;
  }

  public Pnt convertirPunto(double x, double y) {
    return convertirPunto(new PosicionCT(x, y));
  }

  public Pnt convertirPunto(PosicionCT pt) {
    final double mult = 3.0;
    final int sumX = 130;
    final int sumY = 170;

    PosicionCT inv = pt.getPosicionInvertida();
    Pnt pnt = new Pnt(pt.getX() * mult + sumX, inv.getY() * mult + sumY);
    return pnt;
  }

  public Pnt[] convertirPuntos(PosicionCT pt[]) {
    Pnt[] p = new Pnt[pt.length];
    for (int i = 0; i < pt.length; i++) {
      p[i] = convertirPunto(pt[i]);
    }
    return p;
  }

  public Pnt convertirPunto(Pnt pt) {
    return convertirPunto(pt.coord(0), pt.coord(1));
  }

  public Pnt[] convertirPuntos(Pnt pt[]) {
    Pnt[] p = new Pnt[pt.length];
    for (int i = 0; i < pt.length; i++) {
      p[i] = convertirPunto(pt[i].coord(0), pt[i].coord(1));
    }
    return p;
  }

  /**
   * Get the color for the spcified item; generate a new color if necessary.
   *
   * @param item we want the color for this item
   * @return item's color
   */
  private Color getColor(Object item) {
    if (colorTable.containsKey(item)) {
      return colorTable.get(item);
    }
    Color color = new Color(Color.HSBtoRGB(random.nextFloat(), 1.0f, 1.0f));
    colorTable.put(item, color);
    return color;
  }

	/* Basic Drawing Methods */

  /**
   * Draw a point.
   *
   * @param point the Pnt to draw
   */
  public void draw(Pnt point) {
    int r = pointRadius;
    int x = (int) point.coord(0);
    int y = (int) point.coord(1);
    g.fillOval(x - r, y - r, r + r, r + r);
  }

  /**
   * Draw a circle.
   *
   * @param center    the center of the circle
   * @param radius    the circle's radius
   * @param fillColor null implies no fill
   */
  public void draw(Pnt center, double radius, Color fillColor) {
    int x = (int) center.coord(0);
    int y = (int) center.coord(1);
    int r = (int) radius;
    if (fillColor != null) {
      Color temp = g.getColor();
      g.setColor(fillColor);
      g.fillOval(x - r, y - r, r + r, r + r);
      g.setColor(temp);
    }
    g.drawOval(x - r, y - r, r + r, r + r);
  }

  /**
   * Draw a polygon.
   *
   * @param polygon   an array of polygon vertices
   * @param fillColor null implies no fill
   */
  public void draw(Pnt[] polygon, Color fillColor) {
    int[] x = new int[polygon.length];
    int[] y = new int[polygon.length];
    for (int i = 0; i < polygon.length; i++) {
      x[i] = (int) polygon[i].coord(0);
      y[i] = (int) polygon[i].coord(1);
    }
    if (fillColor != null) {
      Color temp = g.getColor();
      g.setColor(fillColor);
      g.fillPolygon(x, y, polygon.length);
      g.setColor(temp);
    }
    g.drawPolygon(x, y, polygon.length);
  }

	/* Higher Level Drawing Methods */

  /**
   * Handles painting entire contents of DelaunayPanel. Called automatically; requested via call to
   * repaint().
   *
   * @param g the Graphics context
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.g = g;

    // Flood the drawing area with a "background" color
    Color temp = g.getColor();
    g.setColor(this.getBackground());
    // g.setColor(voronoiColor);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    g.setColor(temp);

    // If no colors then we can clear the color table
    colorTable.clear();

    // Draw the appropriate picture
    drawAllVoronoi(false, true);

    // Draw any extra info due to the mouse-entry switches
    temp = g.getColor();
    g.setColor(Color.white);
    // drawAllCircles();
    g.setColor(temp);
  }

  /**
   * Draw all the Voronoi cells.
   *
   * @param withFill  true iff drawing Voronoi cells with fill colors
   * @param withSites true iff drawing the site for each Voronoi cell
   */
  public void drawAllVoronoi(boolean withFill, boolean withSites) {
    // Keep track of sites done; no drawing for initial triangles sites
    HashSet<Pnt> done = new HashSet<>(initialTriangle);
    for (Triangle triangle : dt) {
      for (Pnt site : triangle) {
        if (done.contains(site)) {
          continue;
        }
        done.add(site);
        List<Triangle> list = dt.surroundingTriangles(site, triangle);
        Pnt[] vertices = new Pnt[list.size()];
        int i = 0;
        for (Triangle tri : list) {
          vertices[i++] = convertirPunto(tri.getCircumcenter());
          // draw(vertices[i-1]);
        }
        draw(vertices, withFill ? getColor(site) : null);
        if (withSites) {
          draw(convertirPunto(site));
        }
      }
    }

    if (sp != null && tactica != null) {
      Color c = g.getColor();

      for (IJugadorCT jug : tactica.getJugadores(IJugadorCT.Equipo.PROPIO)) {
        Pnt pnt = convertirPunto(jug.getActual());
        String str = "" + (jug.getIndice() + 1);
        if (tactica.recuperando().contains(jug)) {
          str += "!";
        }
        g.drawString(str, (int) pnt.coord(0), (int) pnt.coord(1));
        if (jug.getIndice() != 0) {
          Pnt pnt2 = convertirPunto(jug.getSiguiente());
          g.drawLine((int) pnt.coord(0), (int) pnt.coord(1), (int) pnt2.coord(0),
                     (int) pnt2.coord(1));
        }
        g.setColor(Color.GREEN);
        draw(pnt);
      }

      g.setColor(c);
      g.drawString("" + tactica.posesionBalon(), 250, 300);

      g.setColor(Color.BLUE);
      if (tactica.getUltimoPase() != null) {
        draw(convertirPunto(tactica.getUltimoPase()));
      }

      g.setColor(Color.RED);
      draw(convertirPunto(new PosicionCT(sp.ballPosition())));

      g.setColor(c);
    }
  }

  /**
   * Draw all the empty circles (one for each triangle) of the DT.
   */
  public void drawAllCircles() {
    // Loop through all triangles of the DT
    for (Triangle triangle : dt) {
      // Skip circles involving the initial-triangle vertices
      if (triangle.containsAny(initialTriangle)) {
        continue;
      }
      Pnt c = triangle.getCircumcenter();
      double radius = c.subtract(triangle.get(0)).magnitude();
      draw(c, radius, null);
    }
  }

}
