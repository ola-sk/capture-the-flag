package com.codecool.capture_the_flag.actors;

import com.codecool.capture_the_flag.GameMap;
import com.codecool.capture_the_flag.util.Direction;
import com.codecool.capture_the_flag.util.Vector;


/**
 * Rock player class
 */
public class Rock extends Player {

  public Rock(String name, GameMap mapReference) {
    super(name, mapReference);
  }

  @Override
  public void onGameCycle() {
    if (!isAlive())
      return;

    // Make next move
    Vector myPosition = mapReference.getPosition(this);
    Vector nearestFlagPosition = mapReference.getNearestFlagPosition(this);
    Direction targetDirection = getMoveDirection(myPosition, nearestFlagPosition);

    mapReference.tryMovePlayer(this, myPosition, targetDirection);
  }

  @Override
  public int Fight(Player otherPlayer) {
    throw new RuntimeException("Method not implemented!");
  }

  @Override
  public PlayerTeam getTeam() {
    throw new RuntimeException("Method not implemented!");
  }

  @Override
  public String toString() {
    return "R";
  }
}
