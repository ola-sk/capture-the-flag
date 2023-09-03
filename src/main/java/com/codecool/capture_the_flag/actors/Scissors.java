package com.codecool.capture_the_flag.actors;

import com.codecool.capture_the_flag.GameMap;
import com.codecool.capture_the_flag.util.Direction;
import com.codecool.capture_the_flag.util.GameUtils;
import com.codecool.capture_the_flag.util.Vector;

/**
 * Scissors player class
 */
public class Scissors extends Player {

  public Scissors(String name, GameMap mapReference) {
    super(name, mapReference);
  }

  @Override
  public void onGameCycle() {
    throw new RuntimeException("Method not implemented!");
  }

  @Override
  public short Fight(Player otherPlayer) {
    throwIfFightNotPossible(otherPlayer);
    if (otherPlayer.getTeam() == this.getTeam())
      return -1;
    if (otherPlayer.getTeam() == PlayerTeam.PAPER)
      return winFight(otherPlayer);
    if (otherPlayer.getTeam() == PlayerTeam.ROCK)
      return looseFight(otherPlayer);
  }

  @Override
  public PlayerTeam getTeam() {
    return PlayerTeam.SCISSORS;
  }

  @Override
  public String toString() {
    return "S";
  }
}
