package org.dsaw.javacup.render;

import com.badlogic.gdx.Gdx;
import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.Team;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author Julio Gutierrez (30/05/2014)
 */
public class TeamSelector {

  //TODO load and dispose?
  private static final Map<CountryCode, List<Team>> teams = loadTactics();
  private static final List<CountryCode> countryCodeList = loadCountryCodeList();

  private int countryIndex;
  private int teamIndex;

  public TeamSelector() {
    countryIndex = 0;
    teamIndex = 0;
  }

  public void prevCountry() {
    countryIndex--;
    if (countryIndex < 0) {
      countryIndex = countryCodeList.size() - 1;
    }
    teamIndex = 0;
  }

  public CountryCode getCountry() {
    return countryCodeList.get(countryIndex);
  }

  public void nextCountry() {
    countryIndex++;
    if (countryIndex >= countryCodeList.size()) {
      countryIndex = 0;
    }
    teamIndex = 0;
  }

  public void prevTeam() {
    teamIndex--;
    if (teamIndex < 0) {
      teamIndex = teams.get(getCountry()).size() - 1;
    }
  }

  public Team getTeam() {
    return teams.get(getCountry()).get(teamIndex);
  }

  public void nextTeam() {
    teamIndex++;
    if (teamIndex >= teams.get(getCountry()).size()) {
      teamIndex = 0;
    }
  }

  private static Map<CountryCode, List<Team>> loadTactics() {
    Map<CountryCode, List<Team>> teams = new EnumMap<>(CountryCode.class);

    Reflections reflections = new Reflections("org.dsaw.javacup.tactics.jvc2013"); //TODO
    for (Class<? extends Team> tacticClass : reflections.getSubTypesOf(Team.class)) {
      try {
        Team team = tacticClass.newInstance();
        CountryCode countryCode = team.getCountryCode();
        List<Team> tactteamListList = teams.get(countryCode);
        if (tactteamListList == null) {
          tactteamListList = new ArrayList<>();
          teams.put(countryCode, tactteamListList);
        }
        tactteamListList.add(team);
      } catch (InstantiationException | IllegalAccessException e) {
        Gdx.app.error("init", "Error loading tactic " + tacticClass.getName(), e);
      }
    }

    for (List<Team> tacticList : teams.values()) {
      Collections.sort(tacticList, new Comparator<Team>() {
        @Override
        public int compare(Team o1, Team o2) {
          return o1.getName().toUpperCase().compareTo(
              o2.getName().toUpperCase());
        }
      });
    }

    return teams;
  }

  private static List<CountryCode> loadCountryCodeList() {
    List<CountryCode> countryCodesList = new ArrayList<>(teams.keySet());
    Collections.sort(countryCodesList, new Comparator<CountryCode>() {
      @Override
      public int compare(CountryCode o1, CountryCode o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });
    return countryCodesList;
  }

}