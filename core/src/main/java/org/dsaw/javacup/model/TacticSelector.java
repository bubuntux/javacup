package org.dsaw.javacup.model;

import com.badlogic.gdx.Gdx;
import com.neovisionaries.i18n.CountryCode;

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
public class TacticSelector {

  private static final Map<CountryCode, List<Tactic>> tactics = loadTactics();
  private static final List<CountryCode> countryCodeList = loadCountryCodeList();

  private int countryIndex;
  private int tacticIndex;

  public TacticSelector() {
    countryIndex = 0;
    tacticIndex = 0;
  }

  public void prevCountry() {
    countryIndex--;
    if (countryIndex < 0) {
      countryIndex = countryCodeList.size() - 1;
    }
    tacticIndex = 0;
  }

  public void nextCountry() {
    countryIndex++;
    if (countryIndex >= countryCodeList.size()) {
      countryIndex = 0;
    }
    tacticIndex = 0;
  }

  public CountryCode currentCountry() {
    return countryCodeList.get(countryIndex);
  }

  public void prevTactic() {
    tacticIndex--;
    if (tacticIndex < 0) {
      tacticIndex = tactics.get(currentCountry()).size() - 1;
    }
  }

  public void nextTactic() {
    tacticIndex++;
    if (tacticIndex >= tactics.get(currentCountry()).size()) {
      tacticIndex = 0;
    }
  }

  public Tactic currentTactic() {
    return tactics.get(currentCountry()).get(tacticIndex);
  }

  private static Map<CountryCode, List<Tactic>> loadTactics() {
    Map<CountryCode, List<Tactic>> tactics = new EnumMap<>(CountryCode.class);

    Reflections reflections = new Reflections("org.dsaw.javacup.tactics.jvc2013");
    for (Class<? extends Tactic> tacticClass : reflections.getSubTypesOf(Tactic.class)) {
      try {
        Tactic tactic = tacticClass.newInstance(); //TODO detail as a main class
        CountryCode countryCode = tactic.getDetail().getCountry();
        List<Tactic> tacticList = tactics.get(countryCode);
        if (tacticList == null) {
          tacticList = new ArrayList<>();
          tactics.put(countryCode, tacticList);
        }
        tacticList.add(tactic);
      } catch (InstantiationException | IllegalAccessException e) {
        Gdx.app.error("init", "Error loading tactic " + tacticClass.getName(), e);
      }
    }

    for (List<Tactic> tacticList : tactics.values()) {
      Collections.sort(tacticList, new Comparator<Tactic>() {
        @Override
        public int compare(Tactic o1, Tactic o2) {
          return o1.getDetail().getTacticName().toUpperCase().compareTo(
              o2.getDetail().getTacticName().toUpperCase());
        }
      });
    }

    return tactics;
  }

  private static List<CountryCode> loadCountryCodeList() {
    List<CountryCode> countryCodesList = new ArrayList<>(tactics.keySet());
    Collections.sort(countryCodesList, new Comparator<CountryCode>() {
      @Override
      public int compare(CountryCode o1, CountryCode o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });
    return countryCodesList;
  }


}