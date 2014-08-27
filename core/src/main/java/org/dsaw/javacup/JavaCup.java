package org.dsaw.javacup;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import org.dsaw.javacup.model.Team;
import org.dsaw.javacup.ui.screen.VersusScreen;
import org.reflections.Reflections;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Julio Gutierrez (29/05/2014)
 */
public class JavaCup extends Game {

  //TODO public?
  //TODO configs or something...
  public Skin skin;
  public Set<Team> teams;

  @Override
  public void create() {
    teams = loadTeams();
    skin = new Skin(Gdx.files.internal("skins/dummy/uiskin.json"));

    setScreen(new VersusScreen(this));
  }

  private Set<Team> loadTeams() {
    Set<Team> teams = new HashSet<>();
    Reflections reflections = new Reflections("org.dsaw.javacup.tactics.jvc2013"); //TODO
    for (Class<? extends Team> teamClass : reflections.getSubTypesOf(Team.class)) {
      try {
        Team team = teamClass.newInstance();
        teams.add(team);
      } catch (InstantiationException | IllegalAccessException e) {
        Gdx.app.error("create", "Error loading team " + teamClass.getName(), e);
      }
    }

    return Collections.unmodifiableSet(teams);
  }


  @Override
  public void dispose() {
    super.dispose();
    skin.dispose();
  }
}