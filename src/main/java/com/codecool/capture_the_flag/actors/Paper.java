package com.codecool.capture_the_flag.actors;

import com.codecool.capture_the_flag.GameMap;

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
  public short fight(Player otherPlayer) {
    throwIfFightNotPossible(otherPlayer);
    if (otherPlayer.getTeam() == this.getTeam())
      return -1;
    if (otherPlayer.getTeam() == PlayerTeam.ROCK)
      return winFight(otherPlayer);
    if (otherPlayer.getTeam() == PlayerTeam.SCISSORS)
      return looseFight(otherPlayer);
    throw new IllegalStateException();
  }

  @Override
  public PlayerTeam getTeam() {
    return PlayerTeam.PAPER;
  }

  @Override
  public String toString() {
    return "P";
  }
}
