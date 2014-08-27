package org.dsaw.javacup.ui.actor.widget;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.neovisionaries.i18n.CountryCode;

import org.dsaw.javacup.model.Team;

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
    countries.setItems(countryCodes.toArray(c)); //TODO clean up
    countries.addListener(new ChangeListener() {
      @Override
      @SuppressWarnings({"unchecked"})
      public void changed(ChangeEvent event, Actor actor) {
        updateTeams((SelectBox<CountryCode>) actor);
      }
    });
    addActor(countries);

    teamsSC = new SelectBox<>(skin);
    updateTeams(countries);
    addActor(teamsSC);
  }

  private void updateTeams(SelectBox<CountryCode> actor) {
    CountryCode countryCode = actor.getSelected();
    List<Team> teams1 = teamsMappedByCountryCode.get(countryCode);
    Team[] t = new Team[teams1.size()];
    teamsSC.setItems(teams1.toArray(t)); //TODO clean up
  }

}