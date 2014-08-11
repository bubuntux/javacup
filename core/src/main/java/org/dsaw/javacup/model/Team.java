package org.dsaw.javacup.model;

import com.neovisionaries.i18n.CountryCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Julio Gutierrez on 8/10/14.
 */
public abstract class Team {

  private final String name;
  private final CountryCode countryCode;
  private final List<Player> players; //TODO collection?
  private final TeamStyle style;

  public static final List<Player>
      PLAYERS_DEFAULT = Arrays.asList(Player.DEFAULT_GK, Player.DEFAULT,
                                      Player.DEFAULT, Player.DEFAULT, Player.DEFAULT,
                                      Player.DEFAULT, Player.DEFAULT, Player.DEFAULT,
                                      Player.DEFAULT, Player.DEFAULT, Player.DEFAULT);

  public Team() {
    name = name();
    countryCode = countryCode();
    List<Player> playerList = players();
    players = Collections.unmodifiableList(playerList);
    style = style();
    //TODO validate and defaults!
  }

  public final String getName() {
    return name;
  }

  public final CountryCode getCountryCode() {
    return countryCode;
  }

  public final List<Player> getPlayers() {
    return players;
  }

  protected TeamStyle style() {
    return null;
  }

  protected List<Player> players() {
    return PLAYERS_DEFAULT; //TODO remove ALL defaults and create a random generator
  }

  //TODO check access
  public abstract Tactic tactic(String teamName, CountryCode countryCode, List<Player> players);

  protected abstract String name();

  protected abstract CountryCode countryCode();

  public TeamStyle getStyle() {
    return style;
  }
}
