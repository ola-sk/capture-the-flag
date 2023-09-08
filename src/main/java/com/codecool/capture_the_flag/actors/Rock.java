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

    // Make the next move
    Vector myPosition = mapReference.getPosition(this);
    Vector nearestFlagPosition = mapReference.getNearestFlagPosition(this);
    Direction targetDirection = getMoveDirection(myPosition, nearestFlagPosition);

    mapReference.tryMovePlayer(this, myPosition, targetDirection);
  }

  @Override
  public short fight(Player otherPlayer) {
    throwIfFightNotPossible(otherPlayer);
    if (otherPlayer.getTeam() == this.getTeam())
      return -1;
    if (otherPlayer.getTeam() == PlayerTeam.SCISSORS)
      return winFight(otherPlayer);
    if (otherPlayer.getTeam() == PlayerTeam.PAPER)
      return looseFight(otherPlayer);
    throw new IllegalStateException();
  }

  @Override
  public PlayerTeam getTeam() {
    return PlayerTeam.ROCK;
  }

  @Override
  public String toString() {
    return "R";
  }
}
