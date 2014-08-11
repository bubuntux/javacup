package org.dsaw.javacup.model;

/**
 * Created by Julio Gutierrez on 8/10/14.
 */
public class Player {

  private final String name;
  private final byte number;
  private final boolean goalkeeper; //TODO check one word?
  private final PlayerStats stats;
  private final PlayerStyle style;

  public static final Player DEFAULT = new Player("Default", (byte) 42);
  public static final Player DEFAULT_GK = new Player("Default_GK", (byte) 24, true);

  public Player(String name, byte number) {
    this(name, number, false);
  }

  public Player(String name, byte number, boolean goalkeeper) {
    this(name, number, goalkeeper, PlayerStats.DEFAULT);
  }

  public Player(String name, byte number, PlayerStats stats) {
    this(name, number, false, stats);
  }

  public Player(String name, byte number, boolean goalkeeper, PlayerStats stats) {
    this(name, number, goalkeeper, stats, null);
  }

  public Player(String name, byte number, PlayerStyle style) {
    this(name, number, false, PlayerStats.DEFAULT, style);
  }

  public Player(String name, byte number, boolean goalkeeper, PlayerStyle style) {
    this(name, number, goalkeeper, PlayerStats.DEFAULT, style);
  }

  public Player(String name, byte number, boolean goalkeeper, PlayerStats stats,
                PlayerStyle style) {
    this.name = name;
    this.number = number;
    this.goalkeeper = goalkeeper;
    this.stats = stats;
    this.style = style;
    //TODO validate and defaults
  }

  public final String getName() {
    return name;
  }

  public final byte getNumber() {
    return number;
  }

  public final boolean isGoalkeeper() {
    return goalkeeper;
  }

  public final PlayerStats getStats() {
    return stats;
  }

  public final PlayerStyle getStyle() {
    return style;
  }
}
