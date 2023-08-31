package com.codecool.capture_the_flag.actors;

import com.codecool.capture_the_flag.GameMap;
import com.codecool.capture_the_flag.util.Direction;
import com.codecool.capture_the_flag.util.GameUtils;
import com.codecool.capture_the_flag.util.Vector;

import static com.codecool.capture_the_flag.actors.Player.PlayerTeam.*;

/**
 * Paper player class
 */
public class Paper extends Player {

  public Paper(String name, GameMap mapReference) {
    super(name, mapReference);
  }

  @Override
  public void onGameCycle() {
    throw new RuntimeException("Method not implemented!");
  }

  @Override
  public int Fight(Player otherPlayer) {
    throw new RuntimeException("Method not implemented!");
  }

  @Override
  public PlayerTeam getTeam() {
    throw new RuntimeException("Method not implemented!");
  }
}
