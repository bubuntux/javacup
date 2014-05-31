package org.dsaw.javacup.model;

import com.badlogic.gdx.Gdx;
import com.neovisionaries.i18n.CountryCode;

import org.reflections.Reflections;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Julio Gutierrez (30/05/2014)
 */
public class TacticSelector {

  private static Map<CountryCode, List<Tactic>> tactics;

  public Map<CountryCode, List<Tactic>> getTactics() {
    if (tactics == null) {
      tactics = loadTactics();
    }
    return tactics;
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
          tacticList = new LinkedList<>();
          tactics.put(countryCode, tacticList);
        }
        tacticList.add(tactic);
      } catch (InstantiationException | IllegalAccessException e) {
        Gdx.app.error("init", "Error loading tactic " + tacticClass.getName(), e);
      }
    }

    return tactics;
  }

}