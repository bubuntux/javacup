package org.dsaw.javacup.render;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import org.dsaw.javacup.JavaCup;
import org.dsaw.javacup.model.util.Constants;

/**
 * Created by jgutierrez on 6/9/14.
 */
public class FieldRenderV2 { //TODO i don't like the 1px per meter scale

  private final JavaCup game;

  public FieldRenderV2(JavaCup game) {
    this.game = game;
  }

  public void draw() {
    ShapeRenderer shapeRenderer = game.shapeRenderer;

    //Render all the grass
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(0, 1, 0, 1);
    shapeRenderer
        .rect(0, 0, (float) Constants.ANCHO_CAMPO, (float) Constants.LARGO_CAMPO);
    shapeRenderer.end();

    //Render game field
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(1, 1, 1, 1);
    float gameFieldX = (float) (Constants.ANCHO_CAMPO - Constants.ANCHO_CAMPO_JUEGO) / 2;
    float gameFieldY = (float) (Constants.LARGO_CAMPO - Constants.LARGO_CAMPO_JUEGO) / 2;
    shapeRenderer
        .rect(gameFieldX, gameFieldY, (float) Constants.ANCHO_CAMPO_JUEGO,
              (float) Constants.LARGO_CAMPO_JUEGO);

    float halfFieldX = (float) (gameFieldX + (Constants.ANCHO_CAMPO_JUEGO / 2));

    //Penalty circle
    float penaltyY1 = (float) (gameFieldY + Constants.DISTANCIA_PENAL);
    shapeRenderer.circle(halfFieldX, penaltyY1, (float) Constants.RADIO_PENAL);

    float
        penaltyY2 =
        (float) (gameFieldY + Constants.LARGO_CAMPO_JUEGO - Constants.DISTANCIA_PENAL);
    shapeRenderer.circle(halfFieldX, penaltyY2, (float) Constants.RADIO_PENAL);
    shapeRenderer.end();

    //Filled big area (so remove inside penalty circle)
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(0, 1, 0, 1);
    float
        bigAreaX =
        (float) (halfFieldX - (Constants.LARGO_AREA_GRANDE / 2));
    shapeRenderer
        .rect(bigAreaX,
              gameFieldY,
              (float) Constants.LARGO_AREA_GRANDE, (float) Constants.ANCHO_AREA_GRANDE);
    shapeRenderer
        .rect(bigAreaX,
              (float) (gameFieldY + Constants.LARGO_CAMPO_JUEGO - Constants.ANCHO_AREA_GRANDE),
              (float) Constants.LARGO_AREA_GRANDE, (float) Constants.ANCHO_AREA_GRANDE);

    shapeRenderer.end();

    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(1, 1, 1, 1);
    //Render small area
    float
        smallAreaX =
        (float) (halfFieldX - (Constants.LARGO_AREA_CHICA / 2));
    shapeRenderer
        .rect(smallAreaX, gameFieldY,
              (float) Constants.LARGO_AREA_CHICA, (float) Constants.ANCHO_AREA_CHICA);
    shapeRenderer
        .rect(smallAreaX,
              (float) (gameFieldY + Constants.LARGO_CAMPO_JUEGO - Constants.ANCHO_AREA_CHICA),
              (float) Constants.LARGO_AREA_CHICA, (float) Constants.ANCHO_AREA_CHICA);

    //Render big area
    shapeRenderer
        .rect(bigAreaX,
              gameFieldY,
              (float) Constants.LARGO_AREA_GRANDE, (float) Constants.ANCHO_AREA_GRANDE);
    shapeRenderer
        .rect(bigAreaX,
              (float) (gameFieldY + Constants.LARGO_CAMPO_JUEGO - Constants.ANCHO_AREA_GRANDE),
              (float) Constants.LARGO_AREA_GRANDE, (float) Constants.ANCHO_AREA_GRANDE);

    float penaltyMarkRadio = 0.25f;
    shapeRenderer.circle(halfFieldX, penaltyY1, penaltyMarkRadio);
    shapeRenderer.circle(halfFieldX, penaltyY2, penaltyMarkRadio);

    //Half field
    float halfFieldY = (float) (gameFieldY + (Constants.LARGO_CAMPO_JUEGO / 2));
    shapeRenderer.line(gameFieldX, halfFieldY,
                       (float) (gameFieldX + Constants.ANCHO_CAMPO_JUEGO), halfFieldY);

    shapeRenderer.circle(halfFieldX, halfFieldY,
                         (float) Constants.RADIO_CIRCULO_CENTRAL);

    //render Goals TODO constants?
    float goalWidth = 7.32f;
    float goalHeight = 2.44f;
    float goalAreaX = halfFieldX - (goalWidth / 2);
    shapeRenderer.rect(goalAreaX, gameFieldY - goalHeight, goalWidth, goalHeight);
    shapeRenderer
        .rect(goalAreaX, (float) (gameFieldY + Constants.LARGO_CAMPO_JUEGO), goalWidth, goalHeight);

    shapeRenderer.end();
  }
}
