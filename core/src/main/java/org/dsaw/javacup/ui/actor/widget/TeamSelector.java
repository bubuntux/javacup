package org.dsaw.javacup.ui.actor.widget;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Julio Gutierrez on 8/22/14.
 */
public class TeamSelector extends VerticalGroup {

  private final Map<CountryCode, List<Team>> teamsMappedByCountryCode;
  private final List<CountryCode> countryCodes;

  //TODO replace selectBox with new Custom Widget
  private final SelectBox<CountryCode> countries;
  private final SelectBox<Team> teamsSC;


  public TeamSelector(Collection<Team> teams, Skin skin) {
    teamsMappedByCountryCode = new EnumMap<>(CountryCode.class);
    countryCodes = new LinkedList<>();

    for (Team team : teams) {
      CountryCode countryCode = team.getCountryCode();

      List<Team> teamSet = teamsMappedByCountryCode.get(countryCode);
      if (teamSet == null) {
        teamSet = new LinkedList<>();
        teamsMappedByCountryCode.put(countryCode, teamSet);
        countryCodes.add(countryCode);
      }
      teamSet.add(team);
    }

    space(20);

    countries = new SelectBox<>(skin);
    CountryCode[] c = new CountryCode[countryCodes.size()];
    countries.setItems(countryCodes.toArray(c));
    addActor(countries);

    teamsSC = new SelectBox<>(skin);
    Team[] c2 = new Team[teams.size()];
    ArrayList<Team> list = new ArrayList<>(teams);
    teamsSC.setItems(list.toArray(c2));
    addActor(teamsSC);
  }


}
