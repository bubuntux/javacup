package org.dsaw.javacup.model;

import com.neovisionaries.i18n.CountryCode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Julio Gutierrez on 8/10/14.
 */
public abstract class Team {

  private final String name;
  private final CountryCode countryCode;
  private final List<Player> players; //TODO collection?
  private final TeamStyle style;


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

  public final TeamStyle getStyle() {
    return style;
  }

  protected List<Player> players() { //TODO list or collection?
    List<Player> players = new LinkedList<>();
    Random random = new Random();
    String randomName = "Random"; //TODO some kind of name dict?
    players.add(new Player(randomName, random.nextInt(), true));
    players.add(new Player(randomName, random.nextInt()));
    players.add(new Player(randomName, random.nextInt()));
    players.add(new Player(randomName, random.nextInt()));
    players.add(new Player(randomName, random.nextInt()));
    players.add(new Player(randomName, random.nextInt()));
    players.add(new Player(randomName, random.nextInt()));
    players.add(new Player(randomName, random.nextInt()));
    players.add(new Player(randomName, random.nextInt()));
    players.add(new Player(randomName, random.nextInt()));
    players.add(new Player(randomName, random.nextInt()));
    return players;
  }

  protected TeamStyle style() {
    return null;
  }

  //TODO check access
  public abstract Tactic tactic(String teamName, CountryCode countryCode, List<Player> players);

  protected abstract String name();

  protected abstract CountryCode countryCode();
}
