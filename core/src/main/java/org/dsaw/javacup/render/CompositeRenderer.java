package org.dsaw.javacup.render;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Julio Gutierrez on 8/18/14.
 */
public class CompositeRenderer implements Disposable { //TODO rename?

  public final SpriteBatch batch;
  public final ShapeRenderer shape;
  public final BitmapFont font;

  public CompositeRenderer(SpriteBatch batch, ShapeRenderer shape, BitmapFont font) {
    this.batch = batch;
    this.shape = shape;
    this.font = font;
  }

  @Override
  public void dispose() {
    batch.dispose();
    shape.dispose();
    font.dispose();
  }
}
