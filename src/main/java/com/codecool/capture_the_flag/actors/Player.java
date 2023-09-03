package com.codecool.capture_the_flag.actors;

import com.codecool.capture_the_flag.GameMap;
import com.codecool.capture_the_flag.util.Direction;
import com.codecool.capture_the_flag.util.Vector;

/**
 * Base class for all Player
 */
public abstract class Player extends Actor {

  private final String name;
  private boolean alive;
  private int capturedFlags;
  private int killedPlayers;

  protected Player(String name, GameMap mapReference) {
    super(mapReference);
    this.name = name;
    this.alive = true;
    this.capturedFlags = 0;
    this.killedPlayers = 0;
  }

  /**
   * Returns direction for the player's next move, depending on the nearest flag's position
   *
   * @param playerPosition
   * @param flagPosition
   * @return
   */
  public static Direction getMoveDirection(Vector playerPosition, Vector flagPosition) {
    if (flagPosition == null ) { throw new IllegalArgumentException();}
    else {
      int deltaX = flagPosition.getX() - playerPosition.getX();
      int deltaY = flagPosition.getY() - playerPosition.getY();
      if (deltaX == 0 && deltaY == 0) {
        throw new IllegalArgumentException("Player is already at the flag position.");
      }
      if (Math.abs(deltaX) >= Math.abs(deltaY)) {
        // Move horizontally if horizontal distance is bigger
        return (deltaX > 0) ? Direction.RIGHT : Direction.LEFT;
      } else {
        // Move vertically
        return (deltaY > 0) ? Direction.DOWN : Direction.UP;
      }
    }
  }

  /**
   * Returns this player's current score
   *
   * @return
   */
  public int getCurrentScore() {
    int sum = 0;
    sum += (this.getKilledPlayers() * 5);
    sum += (this.getCapturedFlags() * 10);
    return sum;
  }

  /**
   * Cycles this player's behavior
   */
  public abstract void onGameCycle();

  /**
   * Fight simulation between this and given player
   *
   * @param otherPlayer
   * @return 1 if this player won, 0 if the other player won, -1 if fight didn't happen (otherPlayer is a teammate)
   */
  public abstract short Fight(Player otherPlayer);
  void throwIfFightNotPossible(Player otherPlayer) throws RuntimeException {
    if (!this.isAlive()) {
      throw new IllegalStateException("Player cannot fight since it is dead!");
    } else if (otherPlayer == null) {
      throw new IllegalArgumentException("The `otherPlayer` cannot be null.");
    } else if (!otherPlayer.isAlive()) {
      throw new IllegalArgumentException("The other player was dead already!");
    }
  }
  boolean verifyPlayersCanFight(Player otherPlayer) {
    if (this.isAlive() && otherPlayer == null && otherPlayer.isAlive()) {
      return true;
    } else
      return false;
  }
  short winFight(Player enemy) {
    enemy.setAlive(false);
    this.addKilledPlayer();
    return 1;
  }
  short looseFight(Player enemy) {
    this.setAlive(false);
    enemy.addKilledPlayer();
    return 0;
  }
  /**
   * Returns this player's team
   *
   * @return
   */
  public abstract PlayerTeam getTeam();

  /**
   * Returns true after player was killed
   *
   * @return
   */
  public boolean isAlive() {
    return alive;
  }

  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  /**
   * Returns current amount of flags captured by this player
   *
   * @return
   */
  public int getCapturedFlags() {
    return capturedFlags;
  }

  public void setCapturedFlags(int capturedFlags) {
    this.capturedFlags = capturedFlags;
  }

  /**
   * Returns current amount of players killed by this player
   *
   * @return
   */
  public int getKilledPlayers() {
    return killedPlayers;
  }

  public void setKilledPlayers(int killedPlayers) {
    this.killedPlayers = killedPlayers;
  }
  public void addKilledPlayer() {
    this.killedPlayers++;
  }
  /**
   * Returns this player's name
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * PlayerTeam enum
   */
  public enum PlayerTeam {
    ROCK,
    PAPER,
    SCISSORS
  }
}
