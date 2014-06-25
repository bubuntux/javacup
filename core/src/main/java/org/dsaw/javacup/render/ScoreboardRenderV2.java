package org.dsaw.javacup.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

/**
 * Created by Julio Gutierrez on 6/14/14.
 */
public class ScoreboardRenderV2 {

  private final Matrix4 view = new Matrix4();

  public ScoreboardRenderV2() {
    view.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  public void draw(ShapeRenderer shapeRenderer, Batch batch, BitmapFont font, String localName,
                   int localScore, String visitorName, int visitorScore, int time) {

    shapeRenderer.setProjectionMatrix(view); //todo perform on screen
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(Color.BLACK);

    int width = 200;
    int height = 80;
    float x = 10;
    float y = Gdx.graphics.getHeight() - height - 10;
    shapeRenderer.rect(x, y, width, height);
    shapeRenderer.end();
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(Color.WHITE);
    shapeRenderer.rect(x, y, width, height);
    shapeRenderer.end();

    batch.setProjectionMatrix(view); //todo perform on screen
    batch.begin();
    batch.setColor(Color.WHITE);
    font.draw(batch, localName, x + 40, y + 70, 0, 3);
    font.draw(batch, "-", x + 90, y + 70);
    font.draw(batch, visitorName, x + 120, y + 70, 0, 3);

    font.draw(batch, Integer.toString(localScore), x + 50, y + 40);
    font.draw(batch, Integer.toString(visitorScore), x + 130, y + 40);

    int seg = time / Gdx.graphics.getFramesPerSecond(); //TODO!
    int min = seg / 60;
    seg = seg % 60;

    font.draw(batch, String.format("%s:%s", min, seg), x + 100, y + 20); //

    batch.end();

  }
}